import React, {useEffect, useRef, useState} from "react";
import ContextMenu from "./ContextMenu";

interface Message {
  chatId?: string;
  name: string;
  roomId: string;
  content: string;
  read: boolean,
  deleted?: boolean;
}

interface ChatAreaProps {
  currentUserName: string | null;
  currentRoomId: string | null;
  stompClient: any | null;
}

const ChatArea: React.FC<ChatAreaProps> = ({currentUserName, currentRoomId, stompClient}) => {
  const [messages, setMessages] = useState<Message[]>([]);
  const [contextMenu, setContextMenu] = useState<{
    x: number;
    y: number;
    visible: boolean;
    chatId: string | null
  }>({
    x: 0,
    y: 0,
    visible: false,
    chatId: null,
  });
  const messageRef = useRef<HTMLInputElement>(null);

  // ✅ 채팅방 변경 시 메시지 불러오기
  useEffect(() => {
    if (!currentRoomId) return;

    fetch(`http://127.0.0.1:1006/chat/messages/${currentRoomId}`)
    .then((res) => res.json())
    .then((data) => {
      console.log("📥 메시지 로드 성공:", data);
      setMessages(data);
    })
    .catch((err) => console.error("메시지 불러오기 실패:", err));
  }, [currentRoomId]);

  // ✅ WebSocket 메시지 구독
  useEffect(() => {
    if (!stompClient || !currentRoomId) return;

    const subscription = stompClient.subscribe(`/topic/messages/${currentRoomId}`, (message: any) => {
      const newMessage = JSON.parse(message.body);
      console.log("📨 실시간 메시지 도착:", newMessage);

      setMessages((prev) => {
        const filtered = prev.filter(
            (msg) =>
                !(
                    msg.content === newMessage.content &&
                    msg.name === newMessage.name &&
                    msg.roomId === newMessage.roomId &&
                    !msg.chatId // chatId 없는 건 임시로 간주
                )
        );
        return [...filtered, newMessage];
      });
    });

    return () => subscription.unsubscribe();
  }, [stompClient, currentRoomId]);

  // ✅ 메시지 보내기
  async function sendMessage() {
    if (!stompClient || !currentRoomId || !messageRef.current) return;
    const message = messageRef.current.value.trim();
    if (!message) return;

    const tempMessage = {
      chatId: undefined,
      name: currentUserName!,
      content: message,
      roomId: currentRoomId,
      createdAt: new Date().toISOString(),
      read: false,
      deleted: false
    };

    // ✅ 먼저 뷰에 보여줌
    setMessages((prev) => [...prev, tempMessage]);

    try {
      const formData = new FormData();
      formData.append("name", currentUserName!);
      formData.append("roomId", currentRoomId);
      formData.append("content", message);

      const res = await fetch("http://127.0.0.1:1006/chat/send", {
        method: "POST",
        body: formData,
      });

      if (!res.ok) {
        const errText = await res.text();
        throw new Error("전송 실패: " + errText);
      }

      messageRef.current.value = ""; // 입력창 비우기
    } catch (error) {
      console.error("메시지 전송 오류:", error);
    }
  }

  const bottomRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    bottomRef.current?.scrollIntoView({behavior: 'smooth'});
  }, [messages]);

  const handleContextMenu = (event: React.MouseEvent, chatId: string) => {
    event.preventDefault();
    setContextMenu({
      x: event.clientX + window.scrollX,
      y: event.clientY + window.scrollY,
      visible: true,
      chatId,
    });
  };

// 삭제 버튼 눌렀을 때
  const handleDelete = () => {
    if (contextMenu.chatId) {
      handleDeleteMessage(contextMenu.chatId);
    }
    setContextMenu({...contextMenu, visible: false});
  };

  // ✅ 메시지 삭제
  const handleDeleteMessage = async (chatId: string) => {
    try {
      const res = await fetch(`http://127.0.0.1:1006/chat/messages/${chatId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!res.ok) {
        throw new Error("메시지 삭제 실패");
      }

      setMessages((prev) => prev.map((msg) =>
          msg.chatId === chatId ? {...msg, deleted: true} : msg));
    } catch (err) {
      console.error("삭제 오류:", err);
    }
  };

  return (
      <div style={{
        flex: 1,
        display: "flex",
        flexDirection: "column",
        gap: "10px",
        marginLeft: "25%",
        marginTop: "60px",  // Topbar 영역 확보
        marginBottom: "70px", // 하단 입력창 고정 영역 확보
        padding: "10px"

      }}>
        {messages.map((msg) => (
            <div
                key={msg.chatId || `${msg.name}-${msg.content}-${msg.roomId}`}
                onContextMenu={(e) => handleContextMenu(e, msg.chatId ?? "")}
                style={{
                  display: "block",
                  padding: "10px",
                  backgroundColor: msg.name === currentUserName ? "#e1ffc7" : "#f1f1f1",
                  marginBottom: "10px",
                  borderRadius: "10px",
                  maxWidth: "70%",
                  minWidth: "120px",
                  alignSelf: msg.name === currentUserName ? "flex-end" : "flex-start",
                  wordBreak: "break-word",
                  color: "#000",
                }}
            >
              <strong>{msg.name}</strong>: {msg.deleted ? <i>삭제된 메시지입니다.</i> : msg.content}
            </div>
        ))}

        <div ref={bottomRef}/>

        {/* 메시지 입력창 */}
        <div
            style={{
              position: "fixed",
              bottom: 0,
              left: "25%",
              width: "75%",
              backgroundColor: "#fff",
              padding: "10px",
              display: "flex",
              borderTop: "1px solid #ccc",
              boxShadow: "0 -2px 5px rgba(0,0,0,0.1)",
            }}
        >
          <input
              type="text"
              ref={messageRef}
              placeholder="메시지를 입력하세요"
              onKeyDown={(e) => {
                if (e.key === "Enter") sendMessage();
              }}
              style={{flex: 1, padding: "5px"}}
          />
          <button onClick={sendMessage} style={{marginLeft: "5px"}}>
            전송
          </button>
        </div>

        {/* 우클릭 삭제 메뉴 */}
        {contextMenu.visible && (
            <ContextMenu
                x={contextMenu.x}
                y={contextMenu.y}
                visible={contextMenu.visible}
                onClose={() => setContextMenu({...contextMenu, visible: false})}
                onDelete={handleDelete}
            />
        )}
      </div>
  );
};

export default ChatArea;