// import React from 'react';
// import Modal from './Modal';
//
// function RoomInfoModal({ visible, onClose, room }) {
//     if (!room) {
//         return null; // 방 정보가 없으면 표시할 것 없음
//     }
//
//     return (
//         <Modal visible={visible} onClose={onClose}>
//             <h3>방 정보</h3>
//             <img
//                 src="https://via.placeholder.com/100"
//                 alt="Room"
//                 style={{ display: 'block', margin: '0 auto' }}
//             />
//             <p><strong>방 이름:</strong> {room.roomName}</p>
//             <p><strong>방 개설자:</strong> {room.creatorName || '(정보 없음)'}</p>
//             <p><strong>방 개설일:</strong> {room.createdAt || '(알 수 없음)'}</p>
//
//             <p><strong>대화 참여자:</strong></p>
//             <ul>
//                 {room.members && room.members.map((member, idx) => (
//                     <li key={idx}>{member}</li>
//                 ))}
//             </ul>
//
//             <button onClick={onClose}>닫기</button>
//         </Modal>
//     );
// }
//
// export default RoomInfoModal;
