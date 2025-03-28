import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list";  // ContactList 임포트

type NavigationProps = {
    setViewType: (view: "node" | "table") => void;
    setSelectedDepartment: (departmentId: string) => void;  // 부모 컴포넌트로 부서 ID 전달
};

const DepartmentSidebar: React.FC<NavigationProps> = ({ setViewType, setSelectedDepartment }) => {
    const [employeeId, setEmployeeId] = useState<string | null>(null);
    const [companyCode, setCompanyCode] = useState<string | null>(null);
    const [isAdmin, setIsAdmin] = useState(false);
    const [departments, setDepartments] = useState<any[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [contacts, setContacts] = useState<any[]>([]);  // 사용자 연락처 목록

    useEffect(() => {
        const auth = localStorage.getItem("auth");
        const storedEmployeeId = localStorage.getItem("employeeId");
        const storedCompanyCode = localStorage.getItem("companyCode");

        if (auth && auth.includes("ROLE_ADMIN")) {
            setIsAdmin(true);
        }

        if (storedEmployeeId) setEmployeeId(storedEmployeeId);
        if (storedCompanyCode) setCompanyCode(storedCompanyCode);

        if (storedCompanyCode) {
            fetch(`/api/${storedCompanyCode}/department/list`)
                .then((res) => res.json())
                .then((data) => {
                    setDepartments(data);
                })
                .catch((error) => {
                    setError("부서 정보를 불러오는데 실패했습니다.");
                });
        }
    }, []);

    const handleDepartmentClick = (departmentId: string) => {
        console.log(`클릭한 부서 ID: ${departmentId}`);  // 부서 ID 확인
        setSelectedDepartment(departmentId);  // 부서 ID를 부모에게 전달
        if (companyCode) {
            fetch(`/api/${companyCode}/department/${departmentId}/list`)
                .then((res) => res.json())
                .then((data) => {
                    console.log("받은 사용자 목록:", data);  // 받은 사용자 목록 확인
                    setContacts(data);
                })
                .catch((error) => {
                    setError("사용자 목록을 불러오는데 실패했습니다.");
                });
        }
    };

    // 사용자 선택 시 처리 함수 (onSelectContact)
    const handleSelectContact = (contactId: number) => {  // contactId를 number로 수정
        console.log(`선택한 연락처 ID: ${contactId}`);
        // 여기서 선택된 연락처 처리 로직을 추가할 수 있습니다.
    };

    const renderDepartments = (departments: any[]) => {
        return departments.map((dept) => (
            <li key={dept.departmentId}>
                <button onClick={() => handleDepartmentClick(dept.departmentId)}>
                    {dept.departmentName} ({dept.employees?.length || 0}명)
                </button>
                {dept.subDepartments && dept.subDepartments.length > 0 && (
                    <ul>{renderDepartments(dept.subDepartments)}</ul>
                )}
            </li>
        ));
    };

    return (
        <div style={{ width: "300px", backgroundColor: "#f4f4f4", padding: "20px" }}>
            <h2>Sidebar</h2>
            <div>
                <button onClick={() => setViewType("table")}>TableViewer</button>
                <button onClick={() => setViewType("node")}>NodeViewer</button>
            </div>

            <div>
                <ul>
                    <li>사용자 아이디: {employeeId ?? "정보 없음"}</li>
                    <li>회사 코드: {companyCode ?? "정보 없음"}</li>
                    {isAdmin && <li>관리자 권한 보유</li>}
                </ul>
            </div>

            <div>
                <h3>부서 목록</h3>
                {error ? (
                    <p style={{ color: "red" }}>{error}</p>
                ) : (
                    <ul>
                        {departments.length > 0 ? (
                            renderDepartments(departments)
                        ) : (
                            <li>부서 정보 없음</li>
                        )}
                    </ul>
                )}
            </div>

        </div>
    );
};

export default DepartmentSidebar;
