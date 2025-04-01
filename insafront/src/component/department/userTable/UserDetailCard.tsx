import React from "react";
import MiniCalendar from "@/component/department/ui/miniCalendar";
import {CalendarDTO} from "@/api/mypage/calendaraction"; // Calendar 컴포넌트 분리

interface UserDetailCardProps {
    employeeId: string;
    companyCode: string | null;
    departmentName: string;
    userDetails: any;
    error: string | null;
    checkInTime?: string | null;
    leaveData?: CalendarDTO[]; // leaveData의 타입을 CalendarDTO[]로 지정
    onClose?: () => void;
}


const UserDetailCard: React.FC<UserDetailCardProps> = ({
                                                           employeeId,
                                                           companyCode,
                                                           departmentName,
                                                           userDetails,
                                                           error,
                                                           checkInTime,
                                                           leaveData = [], // leaveData가 없으면 기본값으로 빈 배열 설정
                                                           onClose
                                                       }) => {
    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (!userDetails) {
        return <p className="text-gray-500">로딩 중...</p>;
    }

    const formatDate = (date: string) => new Date(date).toLocaleDateString("ko-KR");

    return (
        <div className="bg-white p-4 rounded-lg shadow-md w-full max-w-md">
            <div>
                <h2>사용자 상세 정보</h2>
                <button
                    type="button"
                    onClick={() => console.log("닫기 버튼 클릭")} // 예시로 버튼 클릭 시 동작 추가
                >
                    닫기
                </button>
            </div>
            <p><strong>이름:</strong> {userDetails.name}</p>
            <p>
                <strong>직급:</strong> {Array.isArray(userDetails.positionName) ? userDetails.positionName.join(", ") : userDetails.positionName}
            </p>
            <p><strong>부서:</strong> {userDetails.departmentName}</p>
            <p><strong>사번:</strong> {userDetails.employeeId}</p>
            <p><strong>이메일:</strong> {userDetails.email}</p>
            <p><strong>전화번호:</strong> {userDetails.phoneNumber}</p>

            {/* 연차 캘린더 */}
            <div className="mt-4">
                <MiniCalendar leaveList={leaveData || []} /> {/* leaveData가 null일 경우 빈 배열로 처리 */}
            </div>
        </div>
    );
};

export default UserDetailCard;
