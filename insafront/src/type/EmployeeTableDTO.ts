// 김민지 작성 - 테이블에서 사용자 테이블
export interface EmployeeData {
    employeeId: string;
    name: string;
    role: string;
    companyCode: string;
    email: string;
    phoneNumber: string;
    departmentId: string;
    departmentName: string;
    hireDate: string;
    retireDate: string;
    position: Position;
}

export interface Position {
    positionId: string;  // 직급 ID
    positionName: string; // 직급 이름
    salaryStepId: number; // 호봉 ID
}
