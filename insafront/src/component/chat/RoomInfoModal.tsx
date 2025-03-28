const RoomInfoModal = ({
                         visible,
                         onClose,
                         room,
                       }: {
  visible: boolean;
  onClose: () => void;
  room: {
    roomId: string;
    roomName: string;
    members: string[];
    createdAt: string;
    creatorName: string;
  } | null;
}) => {
  if (!visible || !room) return null; // ✅ room이 없으면 렌더링 안 함

  return (
      <div className="modal-backdrop">
        <div className="modal-content">
          <div style={{
            position: "fixed",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            backgroundColor: "white",
            padding: "20px",
            borderRadius: "12px",
            boxShadow: "0 2px 10px rgba(0,0,0,0.3)",
            zIndex: 10000
          }}>
            <h2>{room.roomName}</h2>
            <p>방 개설자: {room.creatorName}</p>
            <p>인원: {room.members.length ?? 0}명</p>
            <p>참여자:</p>
            <ul>
              {room.members.map((member) => (
                  <li key={member}>{member}</li>
              ))}
            </ul>
            <p>개설일: {new Date(room.createdAt).toLocaleString()}</p>
            <button onClick={onClose}>닫기</button>
          </div>
        </div>
      </div>
  );
};

export default RoomInfoModal;