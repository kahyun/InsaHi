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
    address: string;
    gender: "여성" | '남성';
    departmentId: string;
    state: string;
    positionName:string;
}
