import { FolderInput, UserMinus, UserPlus, UsersRound } from "lucide-react";
import { useState, useCallback, useEffect } from "react";
import RegisterEmployeeModal from "@/component/department/modal/RegisterModal";
import MoveModal from "@/component/department/modal/MoveModal";

interface IProps {
    selectedContacts: { [key: string]: boolean };
    setSelectedContacts: React.Dispatch<React.SetStateAction<{ [key: string]: boolean }>>;
}

export function Toolbar({ selectedContacts, setSelectedContacts }: IProps) {
    const [companyCode, setCompanyCode] = useState<string | null>(null);

    useEffect(() => {
        const storedCompanyCode = localStorage.getItem("companyCode");
        if (storedCompanyCode) {
            setCompanyCode(storedCompanyCode);
        } else {
            alert("회사 코드가 없습니다. 다시 로그인해주세요.");
        }
    }, []);

    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);
    const [selectedDepartmentId, setSelectedDepartmentId] = useState<string | null>(null);
    const [isAddUserModalOpen, setIsAddUserModalOpen] = useState(false);
    const [isMoveUserModalOpen, setIsMoveUserModalOpen] = useState(false);

    /** 🔹 부서 이동 */
    const handleMoveDepartment = () => {
        if (!selectedUserId || !selectedDepartmentId) {
            alert("사용자와 부서를 선택해주세요.");
            return;
        }
        setIsMoveUserModalOpen(true);
    };

    /** 🔹 사용자 삭제 */
    const handleDeleteUser = useCallback(async () => {
        if (!companyCode) {
            alert("회사 코드가 없습니다. 다시 로그인해주세요.");
            return;
        }

        const selectedUserKeys = Object.keys(selectedContacts).filter((key) => selectedContacts[key]);
        if (selectedUserKeys.length === 0) {
            alert("삭제할 사용자를 선택해주세요.");
            return;
        }

        if (!window.confirm("정말 삭제하겠습니까?")) {
            return;
        }

        try {
            await Promise.all(
                selectedUserKeys.map(async (userId) => {
                    const response = await fetch(`/api/${companyCode}/employee/${userId}`, {
                        method: "DELETE",
                        headers: { "Content-Type": "application/json" },
                    });

                    if (!response.ok) {
                        throw new Error(`사용자 삭제 실패: ${userId}`);
                    }
                })
            );

            alert("선택한 직원이 삭제되었습니다.");

            // 상태 업데이트 (삭제된 직원 제거)
            setSelectedContacts((prev) => {
                const updatedContacts = { ...prev };
                selectedUserKeys.forEach((userId) => delete updatedContacts[userId]);
                return updatedContacts;
            });

        } catch (error) {
            console.error("삭제 중 오류 발생:", error);
            alert("삭제 중 오류가 발생했습니다.");
        }
    }, [selectedContacts, companyCode]);

    return (
        <section className="toolbar">
            <button onClick={handleDeleteUser}>
                <UserMinus size={16} />
                사용자 삭제하기
            </button>
            <button onClick={handleMoveDepartment}>
                <UsersRound size={16} />
                사용자 이동하기
            </button>
            <button onClick={() => setIsAddUserModalOpen(true)}>
                <UserPlus size={16} />
                사용자 추가하기
            </button>

            {/* 사용자 추가 모달 */}
            <RegisterEmployeeModal closeModal={() => setIsAddUserModalOpen(false)} isOpen={isAddUserModalOpen} />

            {/* 사용자 부서 이동 모달 */}
            <MoveModal
                closeModal={() => setIsMoveUserModalOpen(false)}
                isOpen={isMoveUserModalOpen}
                userId={selectedUserId}
                departmentId={selectedDepartmentId}
            />
        </section>
    );
}
