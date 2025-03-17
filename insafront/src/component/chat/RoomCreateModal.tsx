import React, { useEffect, useState } from 'react';
import Modal from './Modal';

function RoomCreateModal({ visible, onClose, currentUser, onRoomCreated }) {
    const [roomName, setRoomName] = useState("");
    const [availableMembers, setAvailableMembers] = useState([]);
    const [checkedMembers, setCheckedMembers] = useState([]);

    useEffect(() => {
        // 모달이 열릴 때마다(visible이 true일 때) 멤버 목록 새로 불러온다
        if (!visible) return;
        fetch("http://127.0.0.1:9500/erp/member/all", { credentials: 'include' })
            .then(res => res.json())
            .then(data => {
                let allNames = data.map(member => member.name);
                allNames = allNames.filter(name => name !== currentUser);
                setAvailableMembers(allNames);
            })
            .catch(() => console.error("회원 목록 실패"));
    }, [visible, currentUser]);

    function handleConfirm() {
        if (!roomName.trim()) {
            alert("방 이름을 입력하세요!");
            return;
        }
        if (checkedMembers.length === 0) {
            alert("초대할 멤버를 선택하세요!");
            return;
        }
        if (!checkedMembers.includes(currentUser)) {
            checkedMembers.push(currentUser);
        }

        const body = { roomName, name: checkedMembers };
        fetch("http://127.0.0.1:9500/chat/rooms", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        })
            .then(res => {
                if (!res.ok) throw new Error("방 생성 실패");
                return res.json();
            })
            .then(room => {
                alert("방 생성 완료!");
                onClose();
                onRoomCreated(room); // 부모에 알림
            })
            .catch(() => alert("방 생성 실패"));
    }

    return (
        <Modal visible={visible} onClose={onClose}>
            <h3>방 생성</h3>
            <label>방 이름:</label>
            <input
                type="text"
                value={roomName}
                onChange={e => setRoomName(e.target.value)}
            />
            <div style={{ marginTop: 10 }}>
                <label>초대할 멤버:</label>
                <div style={{ maxHeight: 150, overflowY: 'auto', border: '1px solid #ccc', padding: 5 }}>
                    {availableMembers.map(name => (
                        <div key={name}>
                            <input
                                type="checkbox"
                                checked={checkedMembers.includes(name)}
                                onChange={e => {
                                    if (e.target.checked) {
                                        setCheckedMembers([...checkedMembers, name]);
                                    } else {
                                        setCheckedMembers(checkedMembers.filter(m => m !== name));
                                    }
                                }}
                            />
                            <span> {name}</span>
                        </div>
                    ))}
                </div>
            </div>
            <button onClick={handleConfirm}>확인</button>
            <button onClick={onClose}>취소</button>
        </Modal>
    );
}

export default RoomCreateModal;
