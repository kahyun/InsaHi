import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list";
import { Toolbar } from "@/component/department/toolbar";
import  {MemberContent,TableViewerContainer} from './styled'

type Contact = {
    id: number;
    name: string;
    department: string;
    position: string;
    email: string;
    phone: string;
    status?: string;
    selected?: boolean;
    highlighted?: boolean;
};

type TableViewerProps = {
    selectedDepartment: string;
};

const TableViewer: React.FC<TableViewerProps> = ({ selectedDepartment }) => {
    const [contacts, setContacts] = useState<Contact[]>([]);  // 사용자 목록 상태
    const [error, setError] = useState<string | null>(null);  // 에러 메시지 상태
    const [storedCompanyCode, setStoredCompanyCode] = useState<string | null>(null); // 회사 코드 상태

    // 로컬 스토리지에서 companyCode 값을 가져오는 useEffect
    useEffect(() => {
        const companyCode = localStorage.getItem("companyCode"); // 로컬 스토리지에서 companyCode 가져오기
        setStoredCompanyCode(companyCode); // companyCode 상태에 저장
    }, []); // 컴포넌트가 처음 마운트될 때 한 번만 호출

    // 부서 ID와 회사 코드가 변경될 때마다 사용자 목록을 가져오는 useEffect
    useEffect(() => {
        // selectedDepartment와 storedCompanyCode 값 확인
        console.log("Selected Department:", selectedDepartment);
        console.log("Stored Company Code:", storedCompanyCode);

        if (!selectedDepartment || !storedCompanyCode) return;  // 부서 ID와 회사 코드가 있어야 API 호출

        // 사용자 목록을 가져오는 API 호출
        fetch(`/api/${storedCompanyCode}/department/${selectedDepartment}/list`)
            .then((res) => res.json())
            .then((data) => {
                console.log("API Response:", data); // API 응답 데이터 확인
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
    }, [selectedDepartment, storedCompanyCode]);  // selectedDepartment 또는 storedCompanyCode가 변경될 때마다 실행

    // 연락처 선택 시 처리하는 함수
    const handleSelectContact = (contactId: number) => {
        console.log(`선택된 연락처 ID: ${contactId}`);
        // 선택된 연락처 ID 처리 로직을 추가
    };

    return (
        <TableViewerContainer className={"TableViewer"}>
            <Toolbar /> {/* 툴바 컴포넌트 */}
            <MemberContent className={"right"}>
                {/* 에러가 있으면 에러 메시지 출력, 그렇지 않으면 사용자 목록 출력 */}
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
