// type/Employee.ts
export interface Employee {
    employeeId: string;       // 직원 ID
    employeeName: string;     // 직원 이름
    departmentId: string;     // 부서 ID
    status: 'ACTIVE' | 'INACTIVE'; // 직원 상태
    positionName: string;     // 직급
    email?: string;           // 이메일 (optional)
    phoneNumber?: string;     // 전화번호 (optional)
}

export interface EmployeeDetail extends Employee {
    hireDate?: string;           // 입사일 (optional)
    departmentName?: string;     // 부서명 (optional)
}
