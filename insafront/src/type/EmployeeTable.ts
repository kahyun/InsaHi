import React from "react";

export type User = {
    name: string;
    email: string;
    phoneNumber: string;
    employeeId: string;
    positionName?: string;
};


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
    contactsData: Contact[],
    onSelectContact: (employeeId: string) => void,
    selectedContacts: { [key: string]: boolean },
    setSelectedContacts: React.Dispatch<React.SetStateAction<{ [p: string]: boolean }>>,
    departmentName?: string,
    departmentId ?: string,
    companyCode?: string
};
