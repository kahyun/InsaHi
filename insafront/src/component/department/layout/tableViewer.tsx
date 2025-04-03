import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list";
import { Toolbar } from "@/component/department/toolbar";
import { MemberContent, TableViewerContainer } from "./styled";
import { Contact } from "@/type/EmployeeTable";
import UserDetailCard from "@/component/department/userTable/UserDetailCard";
import CalendarAction from "@/api/mypage/calendaraction";
import { usePositionActions } from "@/services/salaryAction";
import { PositionEntity } from "@/type/Setting";
import { EmployeeData } from "@/type/EmployeeTableDTO";

type TableViewerProps = {
    selectedDepartment: string;
    departmentName?: string;
};

const TableViewer: React.FC<TableViewerProps> = ({ selectedDepartment, departmentName = "" }) => {
    /** 회사 코드 상태 */
    const [storedCompanyCode, setStoredCompanyCode] = useState<string>("");

    /** 직원 및 상세 정보 상태 */
    const [contacts, setContacts] = useState<Contact[]>([]);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState<string | null>(null);
    const [userDetails, setUserDetails] = useState<Record<string, any> | null>(null);
    const [leaveData, setLeaveData] = useState<any[]>([]);
    const [selectedContacts, setSelectedContacts] = useState<Record<string, boolean>>({});

    /** 직급 데이터 상태 */
    const { positions } = usePositionActions(storedCompanyCode);
    const [positionName, setPositionName] = useState<string>("알 수 없음");

    /** 클라이언트에서 localStorage 값 가져오기 */
    useEffect(() => {
        if (typeof window !== "undefined") {
            const companyCode = localStorage.getItem("companyCode") || "";
            setStoredCompanyCode(companyCode);
        }
    }, []);

    /** 직급 데이터 매칭 */
    useEffect(() => {
        if (!positions || !userDetails?.positionSalaryId) return;

        const foundPosition = positions.find((pos: PositionEntity) => pos.id === userDetails.positionSalaryId)?.positionName;
        setPositionName(foundPosition || "알 수 없음");
    }, [positions, userDetails]);

    /** 부서별 연락처 목록 가져오기 */
    useEffect(() => {
        if (!selectedDepartment || !storedCompanyCode) return;

        const fetchContacts = async () => {
            try {
                const response = await fetch(`/api/${storedCompanyCode}/department/${selectedDepartment}/list`);
                if (!response.ok) throw new Error(`사용자 목록 불러오기 실패: ${response.statusText}`);

                const data = await response.json();
                setContacts(Array.isArray(data) ? data : []);
            } catch (error) {
                console.error("연락처 목록 불러오기 실패", error);
                setContacts([]);
            }
        };

        fetchContacts();
    }, [selectedDepartment, storedCompanyCode]);

    /** 직원 상세 정보 및 휴가 정보 가져오기 */
    useEffect(() => {
        if (!selectedEmployeeId || !storedCompanyCode) return;

        const fetchUserData = async () => {
            try {
                const [userResponse, leaveData] = await Promise.all([
                    fetch(`/api/${storedCompanyCode}/employee/${selectedEmployeeId}`).then(res => {
                        if (!res.ok) throw new Error(`사용자 정보 불러오기 실패: ${res.statusText}`);
                        return res.json();
                    }),
                    CalendarAction(selectedEmployeeId, "APPROVED"),
                ]);

                setUserDetails(userResponse);
                setLeaveData(Array.isArray(leaveData) ? leaveData : []);
            } catch (error) {
                console.error("사용자 정보 불러오기 실패", error);
                setUserDetails(null);
                setLeaveData([]);
            }
        };

        fetchUserData();
    }, [selectedEmployeeId, storedCompanyCode]);

    /** 🔹 User -> EmployeeData 변환 함수 */
    const convertToEmployeeData = (user: Record<string, any>): EmployeeData & { positionName: string } => ({
        employeeId: user.employeeId || "",
        name: user.name || "이름 없음",
        role: user.role || "",
        companyCode: user.companyCode || "",
        email: user.email || "",
        phoneNumber: user.phoneNumber || "",
        departmentId: user.departmentId || "",
        departmentName: departmentName || "",
        hireDate: user.hireDate || "",
        retireDate: user.retireDate || "",
        position: {
            positionId: user.positionId || "",
            positionName: positionName, // 직급 정보
            salaryStepId: user.salaryStepId || 0,
        },
        positionName: positionName, // 🔹 추가된 속성
    });

    return (
        <TableViewerContainer className="TableViewer">
            <h1>{departmentName}</h1>
            <Toolbar selectedContacts={selectedContacts} setSelectedContacts={setSelectedContacts} />
            <MemberContent className="right">
                <ContactList
                    contactsData={contacts}
                    companyCode={storedCompanyCode}
                    onSelectContact={setSelectedEmployeeId}
                    departmentName={departmentName}
                    departmentId={selectedDepartment}
                    selectedContacts={selectedContacts}
                    setSelectedContacts={setSelectedContacts}
                />
                {selectedEmployeeId && userDetails && (
                    <UserDetailCard
                        companyCode={storedCompanyCode}
                        employeeId={selectedEmployeeId}
                        departmentName={departmentName}
                        userDetails={convertToEmployeeData(userDetails)}
                        leaveData={leaveData}
                        onClose={() => setSelectedEmployeeId(null)}
                        error={userDetails?.error || null}
                    />
                )}
            </MemberContent>
        </TableViewerContainer>
    );
};

export default TableViewer;
