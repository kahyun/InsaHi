import React, { useEffect, useState } from "react";
import { SideDepartment } from "./styled";
import axios from "axios";
import { Department } from "@/type/DepartmentDTO";
import Tree from "@/component/department/ui/Tree";
import { useQuery } from "@tanstack/react-query";
import { UserPlus } from "lucide-react";
import CreateDepartmentModal from "@/component/department/modal/createDepartmentModal";

interface NavigationProps {
    setViewType: (view: "node" | "table") => void;
    setSelectedDepartment: (departmentId: string) => void;
    setSelectedDepartmentName?: (value: string | ((prevState: string) => string)) => void;
}

const DepartmentSidebar: React.FC<NavigationProps> = ({
                                                          setViewType,
                                                          setSelectedDepartment,
                                                          setSelectedDepartmentName
                                                      }) => {
    const [auth, setAuth] = useState<string | null>(null);
    const [employeeId, setEmployeeId] = useState<string | null>(null);
    const [companyCode, setCompanyCode] = useState<string | null>(null);
    const [isAdmin, setIsAdmin] = useState(false);
    const [contacts, setContacts] = useState<any[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [isAddUserModalOpen, setIsAddUserModalOpen] = useState(false);

    /** 부서 데이터 가져오기 */
    const { data: departments } = useQuery({
        queryKey: ["department"],
        queryFn: async () => {
            const companyCode = typeof window !== "undefined" ? localStorage.getItem("companyCode") : null;
            const { data } = await axios.get<Department[]>(`/api/${companyCode}/department/list`);
            return data;
        }
    });

    /** 사용자 인증 및 회사 코드 로드 */
    useEffect(() => {
        const storedAuth = localStorage.getItem("auth");
        const storedEmployeeId = localStorage.getItem("employeeId");
        const storedCompanyCode = localStorage.getItem("companyCode");

        if (storedAuth) {
            setAuth(storedAuth);
            setIsAdmin(storedAuth.includes("ROLE_ADMIN"));
        }

        if (storedEmployeeId) setEmployeeId(storedEmployeeId);
        if (storedCompanyCode) setCompanyCode(storedCompanyCode);
    }, []);

    /** 페이지 로딩 시 항상 root 부서 선택 */
    useEffect(() => {
        if (departments && departments.length > 0) {
            const rootDepartment = departments.find(dept => dept.departmentName === "root") || departments[0];

            setSelectedDepartment(rootDepartment.departmentId);
            if (setSelectedDepartmentName) {
                setSelectedDepartmentName(rootDepartment.departmentName);
            }

            if (companyCode) {
                fetch(`/api/${companyCode}/department/${rootDepartment.departmentId}/list`)
                    .then(res => res.json())
                    .then(data => setContacts(data))
                    .catch(() => setError("사용자 목록을 불러오는 데 실패했습니다."));
            }
        }
    }, [departments, companyCode, setSelectedDepartment, setSelectedDepartmentName]);

    /** 부서 클릭 이벤트 */
    const handleDepartmentClick = (departmentId: string) => {
        console.log(`클릭한 부서 ID: ${departmentId}`);
        setSelectedDepartment(departmentId);

        const selectedDept = departments?.find(dept => dept.departmentId === departmentId);
        if (selectedDept && setSelectedDepartmentName) {
            setSelectedDepartmentName(selectedDept.departmentName);
        }

        if (companyCode) {
            fetch(`/api/${companyCode}/department/${departmentId}/list`)
                .then(res => res.json())
                .then(data => setContacts(data))
                .catch(() => setError("사용자 목록을 불러오는 데 실패했습니다."));
        }
    };

    return (
        <SideDepartment>
            <button
                onClick={() => setIsAddUserModalOpen(true)}
                className="bg-blue-500 text-white p-2 rounded flex items-center"
            >
                <UserPlus size={16} className="mr-2" />
                부서 추가하기
            </button>

            {isAddUserModalOpen && (
                <CreateDepartmentModal
                    isOpen={isAddUserModalOpen}
                    closeModal={() => setIsAddUserModalOpen(false)}
                />
            )}

            <Tree departments={departments} handleDepartmentClick={handleDepartmentClick} />
        </SideDepartment>
    );
};

export default DepartmentSidebar;
