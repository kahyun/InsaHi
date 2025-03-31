import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list";
import { Toolbar } from "@/component/department/toolbar";
import { MemberContent, TableViewerContainer } from './styled';
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { Department } from "@/type/DepartmentDTO";

type Contact = {
    id: string;
    employeeId: string;  // employeeId 추가
    name: string;
    department: string;
    position: string;
    positionStep: number;
    email: string;
    phone: string;
};

type TableViewerProps = {
    selectedDepartment: string;
};

const TableViewer: React.FC<TableViewerProps> = ({ selectedDepartment }) => {
    const [contacts, setContacts] = useState<Contact[]>([]);  // 사용자 목록 상태
    const [error, setError] = useState<string | null>(null);  // 에러 메시지 상태
    const [storedCompanyCode, setStoredCompanyCode] = useState<string | null>(null); // 회사 코드 상태

    useEffect(() => {
        const companyCode = localStorage.getItem("companyCode");
        setStoredCompanyCode(companyCode);
    }, []);

    useEffect(() => {
        if (!selectedDepartment || !storedCompanyCode) return;

        fetch(`/api/${storedCompanyCode}/department/${selectedDepartment}/list`)
            .then((res) => res.json())
            .then((data) => {
                if (Array.isArray(data)) {
                    setContacts(data); // 유효한 데이터면 사용자 목록 설정
                    setError(null); // 에러 초기화
                } else {
                    setContacts([]); // 비어있는 배열로 설정
                    setError("유효한 사용자 목록을 불러오지 못했습니다.");
                }
            })
            .catch((error) => {
                console.error("사용자 목록 불러오기 실패:", error);
                setContacts([]); // 비어있는 배열로 설정
                setError("사용자 목록을 불러오는데 실패했습니다.");
            });
    }, [selectedDepartment, storedCompanyCode]);

    const handleSelectContact = (contactId: string) => {
        console.log(`선택된 연락처 ID: ${contactId}`);
    };

    return (
        <TableViewerContainer className={"TableViewer"}>
            <Toolbar /> {/* 툴바 컴포넌트 */}
            <MemberContent className={"right"}>
                {error ? (
                    <p style={{ color: "red" }}>{error}</p>
                ) : (
                    <ContactList contactsData={contacts} onSelectContact={handleSelectContact} />
                )}
                <div>member view area</div>
            </MemberContent>
        </TableViewerContainer>
    );
};

export default TableViewer;
