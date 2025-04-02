import React, {useEffect, useState} from "react";
import ContactList from "@/component/department/contact-list";
import {Toolbar} from "@/component/department/toolbar";
import {MemberContent, TableViewerContainer} from "./styled";
import {Contact} from "@/type/EmployeeTable";
import UserDetailCard from "@/component/department/userTable/UserDetailCard";
import CalendarAction from "@/api/mypage/calendaraction";

type TableViewerProps = {
    selectedDepartment: string,
    departmentName?: string
};

const TableViewer: React.FC<TableViewerProps> = ({selectedDepartment, departmentName}) => {
    const [contacts, setContacts] = useState<Contact[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [storedCompanyCode, setStoredCompanyCode] = useState<string | null>(null);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState<string | null>(null);
    const [userDetails, setUserDetails] = useState<any>(null);
    const [leaveData, setLeaveData] = useState<any[]>([]);

    useEffect(() => {
        const companyCode = localStorage.getItem("companyCode");
        setStoredCompanyCode(companyCode);
    }, []);

    useEffect(() => {
        if (!selectedDepartment || !storedCompanyCode) return;

        fetch(`/api/${storedCompanyCode}/department/${selectedDepartment}/list`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`사용자 목록 불러오기 실패: ${res.statusText}`);
                }
                return res.json();
            })
            .then((data) => {
                if (Array.isArray(data)) {
                    setContacts(data);
                    setError(null);
                } else {
                    setContacts([]);
                    setError("유효한 사용자 목록을 불러오지 못했습니다.");
                }
            })
            .catch((error) => {
                console.error(error);
                setContacts([]);
                setError("사용자 목록을 불러오는데 실패했습니다.");
            });
    }, [selectedDepartment, storedCompanyCode]);

    useEffect(() => {
        if (!selectedEmployeeId || !storedCompanyCode) return;

        // 직원 상세 정보 가져오기
        fetch(`/api/${storedCompanyCode}/employee/${selectedEmployeeId}`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`사용자 세부 정보 불러오기 실패: ${res.statusText}`);
                }
                return res.json();
            })
            .then((data) => {
                if (data) {
                    setUserDetails(data);
                    setError(null);
                } else {
                    setUserDetails(null);
                    setError("사용자 세부 정보를 불러오지 못했습니다.");
                }
            })
            .catch((error) => {
                console.error(error);
                setUserDetails(null);
                setError("사용자 세부 정보를 불러오는데 실패했습니다.");
            });

        // 휴가 데이터 가져오기
        const fetchLeaveData = async () => {
            const data = await CalendarAction(selectedEmployeeId, "APPROVED"); // "APPROVED" 고정
            if (data) {
                setLeaveData(data); // 휴가 데이터를 상태로 저장
            } else {
                setLeaveData([]); // 데이터가 없으면 빈 배열로 설정
            }
        };

        fetchLeaveData();
    }, [selectedEmployeeId, storedCompanyCode]);

    const handleSelectContact = (employeeId: string) => {
        setSelectedEmployeeId(employeeId);
        console.log(`선택된 연락처 ID: ${employeeId}`);
    };

    return (
        <TableViewerContainer className="TableViewer">
            <h1>{departmentName}</h1> {/* 부서 이름 표시 */}
            <Toolbar/>
            <MemberContent className="right">
                {error ? (
                    <p style={{color: "red"}}>{error}</p>
                ) : (
                    <ContactList contactsData={contacts} onSelectContact={handleSelectContact}/>
                )}

                {selectedEmployeeId && userDetails && (
                    <UserDetailCard
                        companyCode={storedCompanyCode}
                        employeeId={selectedEmployeeId}
                        departmentName={selectedDepartment}
                        userDetails={userDetails}
                        leaveData={leaveData}
                        error={error}
                        onClose={() => setSelectedEmployeeId(null)}
                    />
                )}
            </MemberContent>
        </TableViewerContainer>
    );
};

export default TableViewer;
