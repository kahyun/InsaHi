import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list"; // ContactList 임포트
import axios from "axios";
import {Department,Employees} from '@/type/DepartmentDTO'
import Tree from "@/component/department/ui/Tree";
import {useQuery} from "@tanstack/react-query";

type NavigationProps = {
    setViewType: (view: "node" | "table") => void;
    setSelectedDepartment: (departmentId: string) => void; // 부모 컴포넌트로 부서 ID 전달
};

const DepartmentSidebar: React.FC<NavigationProps> = ({ setViewType, setSelectedDepartment }) => {
    const [auth, setAuth] = useState<string | null>(null); // 권한 상태 추가
    const [employeeId, setEmployeeId] = useState<string | null>(null);
    const [companyCode, setCompanyCode] = useState<string | null>(null);
    const [isAdmin, setIsAdmin] = useState(false);
    const [employees, setEmployees] = useState<Employees[]>([])

    const [error, setError] = useState<string | null>(null);
    const [contacts, setContacts] = useState<any[]>([]); // 사용자 연락처 목록


    const { data: departments } = useQuery({
        queryKey: ["department"],
        queryFn: async () => {
            let companyCode = null
            if (typeof window !== 'undefined') {
                companyCode = localStorage.getItem('companyCode');
            }
            const {data} = await axios.get<Department[]>(`/api/${companyCode}/department/list`)
            return data
        }
    })


    // useEffect(() => {
    //     (async () => {
    //         try {
    //             const res = await axios.get<Department[]>(`/api/Com1041/department/list`);
    //             console.log(res.data)
    //             setDepartments(res.data)
    //         } catch (e) {
    //             console.error(e)
    //         }
    //     })()
    // }, []);



    useEffect(() => {
        const storedAuth = localStorage.getItem("auth");
        const storedEmployeeId = localStorage.getItem("employeeId");
        const storedCompanyCode = localStorage.getItem("companyCode");

        if (storedAuth) {
            setAuth(storedAuth); // auth 상태 설정
            if (storedAuth.includes("ROLE_ADMIN")) {
                setIsAdmin(true);
            }
        }

        if (storedEmployeeId) setEmployeeId(storedEmployeeId);
        if (storedCompanyCode) setCompanyCode(storedCompanyCode);

    }, []);

    const handleDepartmentClick = (departmentId: string) => {
        console.log(`클릭한 부서 ID: ${departmentId}`); // 부서 ID 확인
        setSelectedDepartment(departmentId); // 부서 ID를 부모에게 전달
        if (companyCode) {
            fetch(`/api/${companyCode}/department/${departmentId}/list`)
                .then((res) => res.json())
                .then((data) => {
                    console.log("받은 사용자 목록:", data); // 받은 사용자 목록 확인
                    setContacts(data);
                })
                .catch(() => {
                    setError("사용자 목록을 불러오는데 실패했습니다.");
                });
        }
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
        <div>
            <div>
                <button onClick={() => setViewType("table")}>TableViewer</button>
                <button onClick={() => setViewType("node")}>NodeViewer</button>
            </div>

            <div>
                <ul>
                    <li>사용자 아이디: {employeeId ?? "정보 없음"}</li>
                    <li>회사 코드: {companyCode ?? "정보 없음"}</li>
                    <li>권한: {auth ?? "정보 없음"}</li> {/* auth 상태를 직접 표시 */}
                </ul>
            </div>

            <div>
                <Tree departments={departments} handleDepartmentClick={handleDepartmentClick}/>
                {/*{error ? (*/}
                {/*    <p style={{ color: "red" }}>{error}</p>*/}
                {/*) : (*/}
                {/*    <ul>*/}
                {/*        {departments.length > 0 ? (*/}
                {/*            renderDepartments(departments)*/}
                {/*        ) : (*/}
                {/*            <li>부서 정보 없음</li>*/}
                {/*        )}*/}
                {/*    </ul>*/}
                {/*)}*/}
            </div>
        </div>
    );
};

export default DepartmentSidebar;
