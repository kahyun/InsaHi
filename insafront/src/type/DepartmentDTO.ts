// 김민지 작성 - 테이블에서 부서 테이블

export interface Department {
    departmentId: string,
    departmentName: string,
    companyCode:string,
    parentDepartmentId: string | null,
    subDepartments: Department[] | null,
    employees: Employees[]
}

export interface Employees {
    employeeId: string;
    password: string;
    name: string;
    role: 'ROLE_ADMIN' | 'ROLE_USER';
    companyCode: string;
    email: string;
    phoneNumber: string;
    departmentId: string;
    state: string;
    position: Position[]
}

export interface Position {
    salaryStepId : String;
    positionName : String;
    positionSalaryId : String;
}