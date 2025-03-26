// type/Department.ts
export interface Employee {
    employeeId: string;       // 직원 ID
    employeeName: string;     // 직원 이름
    departmentId: string;     // 부서 ID
    status: 'ACTIVE' | 'INACTIVE'; // 직원 상태
    positionName: string;     // 직급
    email?: string;           // 이메일 (optional)
    phoneNumber?: string;     // 전화번호 (optional)
}

export interface Department {
    departmentId: string;     // 부서 ID
    departmentName: string;   // 부서명
    employees?: Employee[];   // 해당 부서의 직원 배열 (optional)
    subDepartments?: Department[]; // 하위 부서 배열 (optional)
    action?: string;         
}

export interface OrganizationChart {
    organization: Department[];    // 조직도의 부서 목록
}
