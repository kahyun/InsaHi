import React, {useEffect, useState} from "react";
import {API_BASE_URL_Chat, API_BASE_URL_Employee} from "@/api/api_base_url";

interface RoomCreateModalProps {
  visible: boolean;
  onClose: () => void;
  onRoomCreated: () => void;
  currentUserName: string | null;
  currentUserId: string | null;
}

const RoomCreateModal: React.FC<RoomCreateModalProps> = ({
                                                           visible,
                                                           onClose,
                                                           onRoomCreated,
                                                           currentUserName,
                                                           currentUserId
                                                         }) => {
  const [roomName, setRoomName] = useState("");
  const [members, setMembers] = useState<string[]>([]);
  const [allUsers, setAllUsers] = useState<{ employeeId: string; name: string }[]>([]);


  function decodeToken(token: string) {
    try {
      // 실제 프로젝트에선 jwt-decode 라이브러리를 써도 됩니다.
      const base64Payload = token.split(".")[1];
      const payload = JSON.parse(window.atob(base64Payload));
      return payload; // { companyCode: "...", sub: "...", iat: ..., ... }
    } catch (err) {
      console.error("토큰 디코딩 실패", err);
      return null;
    }
  }

  useEffect(() => {
    if (!visible) return;

    let token = localStorage.getItem("accessToken");

    //  'Bearer '이 없는 경우 자동으로 추가
    if (token && !token.startsWith("Bearer ")) {
      token = `Bearer ${token}`;
    }

    if (!token) {
      console.error("❌ 토큰이 없습니다. 회원 목록 요청을 중단합니다.");
      return; // ❌ 토큰이 없으면 요청을 보내지 않음
    }

    // 디코딩해서 companyCode 추출
    const decoded = decodeToken(token);
    const myCompanyCode = decoded?.companyCode;  // 백엔드 JWT payload 안에 companyCode가 들어 있어야 합니다.
    if (!myCompanyCode) {
      console.error("❌ 토큰 payload에 companyCode가 없습니다.");
      return;
    }

    fetch(`${API_BASE_URL_Employee}/all`, {
      method: "GET",
      headers: {
        Authorization: token,  // ✅ 토큰이 있을 때만 요청
        "Content-Type": "application/json"
      },
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error("❌ 인증 실패 또는 서버 오류");
      }
      return res.json();
    })
    .then((data: { employeeId: string; name: string; companyCode?: string }[]) => {
      const filteredUsers = data.filter(user =>
          user.companyCode === myCompanyCode && user.employeeId !== currentUserId);
      setAllUsers(filteredUsers); //  전체 사용자 정보 보관
    })
    .catch((err) => console.error("회원 목록 불러오기 실패:", err));
  }, [visible]);

  useEffect(() => {
    if (visible) {
      setRoomName('');
      setMembers([]);
    }
  }, [visible]);

  function createRoom() {
    if (!roomName.trim()) {
      alert("방 이름을 입력하세요");
      return;
    }
    const selectedMemberIds = allUsers
    .filter((user) => members.includes(user.employeeId))
    .map((user) => user.name);
//          로그인한 유저도 멤버로 포함
    const finalMembers = [...selectedMemberIds];
    if (currentUserName && !finalMembers.includes(currentUserName)) {
      finalMembers.push(currentUserName);
    }

    const requestBody = {
      roomName,
      members: finalMembers,
      creatorName: currentUserName || "익명",
    };

    fetch(`${API_BASE_URL_Chat}/rooms`, {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(requestBody),
    })
    .then(() => {
      onRoomCreated();
      onClose();
    })
    .catch(() => alert("방 생성 실패"));
  }

  if (!visible) return null;

  return (
      <div style={{
        position: "fixed",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        padding: "20px",
        backgroundColor: "white",
        borderRadius: "8px",
        boxShadow: "0 4px 10px rgba(0, 0, 0, 0.2)",
        maxWidth: "600px",  // ✅ 폭 제한
        width: "90%",        // 반응형 대응
        margin: "0 auto",
      }}>
        <h2 style={{marginBottom: "15px", textAlign: "center"}}>방 생성</h2>
        <input
            type="text"
            placeholder="방 이름 입력"
            value={roomName}
            onChange={(e) => setRoomName(e.target.value)}
            style={{
              width: "100%",
              padding: "10px",
              marginBottom: "15px",
              border: "1px solid #ccc",
              borderRadius: "4px"
            }}
        />

        <div style={{
          display: "flex",
          gap: "20px",
          justifyContent: "center",
          alignItems: "flex-start"
        }}>
          {/* 초대할 멤버 목록 */}
          <div style={{
            flex: 1,
            padding: "15px",
            backgroundColor: "#ffffff",
            borderRadius: "8px",
            boxShadow: "0 2px 5px rgba(0, 0, 0, 0.1)",
            border: "1px solid #ddd",
            maxHeight: "250px",       // ✅ 최대 높이 제한
            overflowY: "auto",        // ✅ 세로 스크롤
          }}>
            <h4 style={{marginBottom: "10px"}}>초대할 멤버</h4>
            {allUsers.length > 0 ? (
                allUsers.map((user) => (
                    <label key={user.employeeId} style={{display: "block", marginBottom: "5px"}}>
                      <input
                          type="checkbox"
                          value={user.employeeId}
                          onChange={(e) => {
                            if (e.target.checked) setMembers([...members, user.employeeId]);
                            else setMembers(members.filter((m) => m !== user.employeeId));
                          }}
                      />
                      {user.name}
                    </label>
                ))
            ) : (
                <p>초대할 멤버가 없습니다.</p>
            )}
          </div>
        </div>

        {/* 생성 / 취소 버튼 */}
        <div style={{textAlign: "center", marginTop: "20px"}}>
          <button
              onClick={createRoom}
              style={{
                padding: "10px 20px",
                marginRight: "10px",
                backgroundColor: "#1E3A8A",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer"
              }}>
            생성
          </button>
          <button
              onClick={onClose}
              style={{
                padding: "10px 20px",
                backgroundColor: "#ccc",
                color: "black",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer"
              }}>
            취소
          </button>
        </div>
      </div>
  );
};

export default RoomCreateModal;