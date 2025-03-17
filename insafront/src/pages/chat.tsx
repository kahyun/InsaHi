import React, { useEffect, useState } from "react";
import ChatRoomList from "@/component/chat/ChatRoomList";
import ChatArea from "@/component/chat/ChatArea";
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import { useRouter } from "next/router";

const ChatPage: React.FC = () => {
    const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
    const [currentUser, setCurrentUser] = useState<string | null>(null);
    const [currentRoomId, setCurrentRoomId] = useState<string | null>(null);
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem("accessToken");

        if (!token) {
            console.error("토큰이 없습니다. 로그인 페이지로 이동합니다.");
            router.push("/");
            return;
        }

        fetch("http://127.0.0.1:1006/employee/find", {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        })
            .then((res) => res.json())
            .then((data) => {
                if (data?.employeeId) {
                    setCurrentUser(data.employeeId);
                    connectWebSocket(data.employeeId);
                } else {
                    console.error("사용자 정보를 가져올 수 없습니다.");
                }
            })
            .catch((err) => console.error("JWT 인증 실패:", err));
    }, []);

    function connectWebSocket(userName: string) {
        if (!userName) return;

        const socket = new SockJS("http://127.0.0.1:1006/chat/ws-stomp");
        const stomp = Stomp.over(socket);

        stomp.connect(
            {},
            (frame) => console.log("✅ WebSocket 연결 완료:", frame),
            (error) => console.error("❌ WebSocket 연결 실패:", error)
        );

        setStompClient(stomp);
    }

    return (
        <div style={{ display: "flex", height: "100vh" }}>
            <ChatRoomList
                currentUser={currentUser}
                stompClient={stompClient}
                onSelectRoom={(roomId: string) => setCurrentRoomId(roomId)}
            />
            <ChatArea currentUser={currentUser} currentRoomId={currentRoomId} stompClient={stompClient} />
        </div>
    );
};

export default ChatPage;
