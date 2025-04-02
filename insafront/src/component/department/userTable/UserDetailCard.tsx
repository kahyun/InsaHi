import React, { useState, useEffect } from "react";
import MiniCalendar from "@/component/department/ui/miniCalendar";
import {MemberView,MemberHeader} from "./styled";
import { CalendarDTO } from "@/api/mypage/calendaraction";

interface User {
    name: string;
    positionName: string;
    email: string;
    phoneNumber: string;
    address?: string;
    gender?: string;
    birthday?: string;
    departmentId?: string;
    state?: string;
    hireDate?: string;
    retireDate?: string;
    positionSalaryId?: number;
    salaryStepId?: number;
    authorityList?: string[];
    companyCode?: string | null;
    employeeId: string;
    departmentName: string;
}

interface UserDetailCardProps {
    employeeId: string;
    departmentName: string;
    userDetails: User;
    error: string | null;
    checkInTime?: string | null;
    leaveData?: CalendarDTO[];
    onClose?: () => void;
    companyCode?: string | null;
}

const UserDetailCard: React.FC<UserDetailCardProps> = ({
                                                           employeeId,
                                                           departmentName,
                                                           userDetails,
                                                           error,
                                                           checkInTime,
                                                           leaveData = [],
                                                           onClose,
                                                           companyCode,
                                                       }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editedUser, setEditedUser] = useState<User>(userDetails);
    const [saveError, setSaveError] = useState<string | null>(null);
    const [saveSuccess, setSaveSuccess] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setEditedUser(userDetails);
    }, [userDetails]);

    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (!userDetails) {
        return <p className="text-gray-500">로딩 중...</p>;
    }

    const formatDate = (date: string) => new Date(date).toLocaleDateString("ko-KR");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setEditedUser((prevState: User) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSaveClick = async () => {
        setIsLoading(true);
        setSaveError(null);
        setSaveSuccess(null);

        if (!companyCode) {
            setSaveError("회사 코드가 없습니다.");
            setIsLoading(false);
            return;
        }

        const userToSave = {
            ...editedUser,
            companyCode: companyCode,
        };

        try {
            const response = await fetch(`/api/${companyCode}/employee/edit/${employeeId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(userToSave),
            });

            if (!response.ok) {
                throw new Error("사용자 정보 업데이트 실패");
            }

            const updatedUser = await response.json();
            setEditedUser(updatedUser);
            setSaveSuccess("사용자 정보가 성공적으로 저장되었습니다.");
        } catch (err) {
            setSaveError("사용자 정보를 저장하는데 실패했습니다. 다시 시도해주세요.");
            console.error("오류 발생:", err);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <MemberView className="MemberView">
            <MemberHeader className="MemberHeader">
                <h2>사용자 상세 정보</h2>
                <div>
                {isEditing ? (
                    <button
                        type="button"
                        onClick={handleSaveClick}
                        disabled={isLoading}
                        className="bg-blue-500 text-white p-2 rounded"
                    >
                        {isLoading ? "저장 중..." : "저장"}
                    </button>
                ) : (
                    <button type="button" onClick={() => setIsEditing(true)} className="bg-green-500 text-white p-2 rounded">
                        수정
                    </button>
                )}
                <button
                    type="button"
                    onClick={() => {
                        onClose && onClose();
                        setIsEditing(false);
                    }}
                    className="bg-gray-500 text-white p-2 rounded"
                >
                    닫기
                </button>
                </div>
            </MemberHeader>

            {saveError && <p className="text-red-500">{saveError}</p>}
            {saveSuccess && <p className="text-green-500">{saveSuccess}</p>}

            <p>
                <strong>이름:</strong>{" "}
                {isEditing ? (
                    <input
                        type="text"
                        name="name"
                        value={editedUser.name}
                        onChange={handleChange}
                        className="border p-1"
                    />
                ) : (
                    userDetails.name
                )}
            </p>

            <p>
                <strong>직급:</strong>{" "}
                {isEditing ? (
                    <input
                        type="text"
                        name="positionName"
                        value={editedUser.positionName || ""}
                        onChange={handleChange}
                        className="border p-1"
                    />
                ) : (
                    userDetails.positionName
                )}
            </p>

            <p><strong>부서:</strong> {userDetails.departmentName}</p>
            <p><strong>사번:</strong> {userDetails.employeeId}</p>

            <p>
                <strong>이메일:</strong>{" "}
                {isEditing ? (
                    <input
                        type="email"
                        name="email"
                        value={editedUser.email}
                        onChange={handleChange}
                        className="border p-1"
                    />
                ) : (
                    userDetails.email
                )}
            </p>

            <p>
                <strong>전화번호:</strong>{" "}
                {isEditing ? (
                    <input
                        type="tel"
                        name="phoneNumber"
                        value={editedUser.phoneNumber}
                        onChange={handleChange}
                        className="border p-1"
                    />
                ) : (
                    userDetails.phoneNumber
                )}
            </p>

            <div className="mt-4">
                <MiniCalendar leaveList={leaveData || []} />
            </div>
        </MemberView>
    );
};

export default UserDetailCard;
