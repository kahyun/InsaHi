import { FolderInput, UserMinus, UserPlus, UsersRound } from "lucide-react";
import { useState } from "react";
import AddUserModal from "@/component/department/modal/AddUserModal"; // AddUserModal 컴포넌트 import

export function Toolbar() {
    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);
    const [selectedDepartmentId, setSelectedDepartmentId] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);  // 모달 상태 관리

    // 부서 이동하기 기능
    const handleMoveDepartment = () => {
        if (selectedUserId && selectedDepartmentId) {
            console.log(`Move user ${selectedUserId} to department ${selectedDepartmentId}`);
        } else {
            alert("사용자와 부서를 선택해주세요.");
        }
    };

    // 사용자 삭제하기 기능
    const handleDeleteUser = () => {
        if (selectedUserId) {
            console.log(`Delete user ${selectedUserId}`);
        } else {
            alert("삭제할 사용자를 선택해주세요.");
        }
    };

    // 사용자 이동하기 기능
    const handleMoveUser = () => {
        if (selectedUserId && selectedDepartmentId) {
            console.log(`Move user ${selectedUserId} to department ${selectedDepartmentId}`);
        } else {
            alert("사용자와 부서를 선택해주세요.");
        }
    };

    // 사용자 추가하기 기능 (모달 열기)
    const handleAddUser = () => {
        setIsModalOpen(true);  // 모달을 열기
    };

    // 모달 닫기
    const closeModal = () => {
        setIsModalOpen(false);  // 모달을 닫기
    };

    return (
        <div>
            <button onClick={handleMoveDepartment}>
                <FolderInput size={16} />
                부서 이동하기
            </button>
            <button onClick={handleDeleteUser}>
                <UserMinus size={16} />
                사용자 삭제하기
            </button>
            <button onClick={handleMoveUser}>
                <UsersRound size={16} />
                사용자 이동하기
            </button>
            <button onClick={handleAddUser}>
                <UserPlus size={16} />
                사용자 추가하기
            </button>

            {/* 사용자 추가 모달 */}
            <AddUserModal isOpen={isModalOpen} onClose={closeModal} />
        </div>
    );
}
