import { FolderInput, UserMinus, UserPlus, UsersRound } from "lucide-react";
import { useState } from "react";
import RegisterEmployeeModal from "@/component/department/modal/RegisterModal"; // AddUserModal 컴포넌트 import
import MoveModal from "@/component/department/modal/MoveModal"; // 부서 이동 모달 컴포넌트 import

export function Toolbar() {
    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);  // 선택된 사용자 ID 상태
    const [selectedDepartmentId, setSelectedDepartmentId] = useState<string | null>(null);  // 선택된 부서 ID 상태
    const [isAddUserModalOpen, setIsAddUserModalOpen] = useState(false);  // 사용자 추가 모달 상태
    const [isMoveUserModalOpen, setIsMoveUserModalOpen] = useState(false);  // 사용자 부서 이동 모달 상태

    // 부서 이동하기 기능
    const handleMoveDepartment = () => {
        if (selectedUserId && selectedDepartmentId) {
            setIsMoveUserModalOpen(true); // 부서 이동 모달 열기
        } else {
            alert("사용자와 부서를 선택해주세요.");  // 사용자 또는 부서 선택되지 않으면 알림
        }
    };

    // 사용자 삭제하기 기능
    const handleDeleteUser = () => {
        if (selectedUserId) {
            console.log(`Delete user ${selectedUserId}`);
        } else {
            alert("삭제할 사용자를 선택해주세요.");  // 사용자 선택되지 않으면 알림
        }
    };

    // 사용자 추가하기 기능
    const handleAddUser = () => {
        setIsAddUserModalOpen(true);  // 사용자 추가 모달 열기
    };

    // 모달 닫기
    const closeAddUserModal = () => {
        setIsAddUserModalOpen(false);  // 사용자 추가 모달 닫기
    };

    const closeMoveUserModal = () => {
        setIsMoveUserModalOpen(false);  // 사용자 부서 이동 모달 닫기
    };

    return (
        <div>
            <div>
                <button onClick={handleDeleteUser}>
                    <UserMinus size={16} />
                    사용자 삭제하기
                </button>
                <button onClick={handleMoveDepartment}>
                    <UsersRound size={16} />
                    사용자 이동하기
                </button>
                <button onClick={handleAddUser}>
                    <UserPlus size={16} />
                    사용자 추가하기
                </button>

                {/* 사용자 추가 모달 */}
                <RegisterEmployeeModal closeModal={closeAddUserModal} isOpen={isAddUserModalOpen} />

                {/* 사용자 부서 이동 모달 */}
                <MoveModal
                    closeModal={closeMoveUserModal}
                    isOpen={isMoveUserModalOpen}
                    userId={selectedUserId}
                    departmentId={selectedDepartmentId}
                />
            </div>
        </div>
    );
}
