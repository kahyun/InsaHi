// import React, { useEffect, useState } from "react";
// import RoomCreateModal from "./RoomCreateModal";
// import RoomInfoModal from "./RoomInfoModal";
// import ContextMenu from "./ContextMenu";
//
// let Stomp: any = null; // ✅ Stomp 변수를 미리 선언
//
// if (typeof window !== "undefined") {
//     Stomp = require("stompjs"); // ✅ 브라우저 환경에서만 동적으로 로드
// }
// interface ChatRoom {
//     roomId: string;
//     roomName: string;
// }
//
// interface ChatRoomListProps {
//     currentUser: string | null;
//     stompClient: any;
//     onSelectRoom: (roomId: string) => void;
// }
//
// const ChatRoomList: React.FC<ChatRoomListProps> = ({ currentUser, stompClient, onSelectRoom }) => {
//     const [rooms, setRooms] = useState<ChatRoom[]>([]);
//     const [showCreateModal, setShowCreateModal] = useState(false);
//     const [roomInfo, setRoomInfo] = useState<ChatRoom | null>(null);
//     const [contextMenu, setContextMenu] = useState<{ visible: boolean; x: number; y: number; room: ChatRoom | null }>({
//         visible: false,
//         x: 0,
//         y: 0,
//         room: null,
//     });
//
//     useEffect(() => {
//         if (!currentUser) return;
//         fetchRooms();
//     }, [currentUser]);
//
//     function fetchRooms() {
//         fetch(`http://127.0.0.1:1006/chat/rooms/member/${currentUser}`)
//             .then((res) => res.json())
//             .then((data) => setRooms(data))
//             .catch((err) => console.error("방 목록 오류", err));
//     }
//
//     function handleRoomClick(roomId: string) {
//         onSelectRoom(roomId);
//     }
//
//     function handleContextMenu(e: React.MouseEvent, room: ChatRoom) {
//         e.preventDefault();
//         setContextMenu({ visible: true, x: e.pageX, y: e.pageY, room });
//     }
//
//     function handleLeaveRoom() {
//         if (!contextMenu.room) return;
//         fetch(`http://127.0.0.1:1006/chat/rooms/${contextMenu.room.roomId}/members/${currentUser}`, {
//             method: "DELETE",
//         })
//             .then(() => {
//                 alert("방에서 나갔습니다.");
//                 fetchRooms();
//             })
//             .catch(() => alert("방 나가기 실패"));
//
//         setContextMenu({ visible: false, x: 0, y: 0, room: null });
//     }
//
//     const containerStyle = {
//         width: "25%",
//         backgroundColor: "#f2f2f2",
//         borderRight: "1px solid #ccc",
//     };
//
//     return (
//         <div style={containerStyle}>
//             <div style={{ backgroundColor: "#ddd", padding: "10px" }}>
//                 <h2 style={{ margin: 0 }}>채팅방 목록</h2>
//                 <button onClick={() => setShowCreateModal(true)}>+</button>
//             </div>
//
//             <div>
//                 {rooms.map((room) => (
//                     <div
//                         key={room.roomId}
//                         style={{ padding: 10, borderBottom: "1px solid #eee", cursor: "pointer" }}
//                         onDoubleClick={() => handleRoomClick(room.roomId)}
//                         onContextMenu={(e) => handleContextMenu(e, room)}
//                     >
//                         {room.roomName}
//                     </div>
//                 ))}
//             </div>
//
//             <RoomCreateModal
//                 visible={showCreateModal}
//                 onClose={() => setShowCreateModal(false)}
//                 onRoomCreated={fetchRooms}
//                 currentUser={currentUser}
//             />
//             <RoomInfoModal visible={!!roomInfo} onClose={() => setRoomInfo(null)} room={roomInfo} />
//
//             <ContextMenu
//                 x={contextMenu.x}
//                 y={contextMenu.y}
//                 visible={contextMenu.visible}
//                 onClose={() => setContextMenu({ visible: false, x: 0, y: 0, room: null })}
//                 menuItems={[{ label: "나가기", onClick: handleLeaveRoom }]}
//             />
//         </div>
//     );
// };
//
// export default ChatRoomList;
