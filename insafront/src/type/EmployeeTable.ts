export type Contact = {
    employeeId: string;
    name: string;
    email: string;
    phoneNumber: string;
    departmentId: string;
    departmentName: string;
    state?: string;
    hireDate?: string | null;
    retireDate?: string | null;
    positionName?: string;
    positionSalaryId?: string | null;
    salaryStepId?: number;
    authorityList?: string[] | null;
    companyCode?: string | null;
};

export type ContactListProps = {
    contactsData: Contact[]; // 연락처 데이터 배열
    onSelectContact: (employeeId: string) => void; // 선택된 연락처의 employeeId를 부모로 전달하는 함수
};
