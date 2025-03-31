import React, { useEffect, useState } from "react";

interface Position {
    positionName: string;
    department: string;
}

interface UserDetailCardProps {
    employeeId: string;
    companyCode: string | null;
}

const UserDetailCard: React.FC<UserDetailCardProps> = ({ employeeId, companyCode }) => {
    const [userDetails, setUserDetails] = useState<any>(null); // 사용자 세부 정보 상태
    const [error, setError] = useState<string | null>(null); // 에러 메시지 상태

    useEffect(() => {
        if (!employeeId || !companyCode) return; // employeeId와 companyCode가 있어야 API 호출

        // 사용자 상세 정보를 가져오는 API 호출
        fetch(`/api/${companyCode}/employee/${employeeId}`)
            .then((res) => res.json())
            .then((data) => {
                if (data) {
                    setUserDetails(data); // 유효한 데이터면 사용자 정보 설정
                    setError(null); // 에러 초기화
                } else {
                    setError("사용자 정보를 불러오지 못했습니다.");
                }
            })
            .catch((error) => {
                setError("사용자 정보를 불러오는데 실패했습니다.");
            });
    }, [employeeId, companyCode]); // employeeId나 companyCode가 변경될 때마다 실행

    if (error) {
        return <p style={{ color: "red" }}>{error}</p>;
    }

    if (!userDetails) {
        return <p>로딩 중...</p>;
    }

    return (
        <div className={"UserDetail"}>
            <h5>사용자 상세 정보</h5>
            <p><strong>이름:</strong> {userDetails.name}</p>
            <p><strong>직급:</strong> {userDetails.position?.join(", ")}</p>
            <p><strong>부서:</strong> {userDetails.department}</p>
            <p><strong>사번:</strong> {userDetails.employeeId}</p>
            <p><strong>이메일:</strong> {userDetails.email}</p>
            <p><strong>전화번호:</strong> {userDetails.phoneNumber}</p>
            <div style={{ marginTop: "16px", fontSize: "0.875rem", color: "#757575" }}>
                마지막 수정: {new Date().toLocaleString()}
            </div>
        </div>
    );
};

export default UserDetailCard;
