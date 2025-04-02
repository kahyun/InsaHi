import React, { useEffect, useState } from "react";
import ContactList from "@/component/department/contact-list";
import axios from "axios";
import { Department, Employees } from "@/type/DepartmentDTO";
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

    const { data: departments } = useQuery({
        queryKey: ["department"],
        queryFn: async () => {
            const companyCode = typeof window !== "undefined" ? localStorage.getItem("companyCode") : null;
            const { data } = await axios.get<Department[]>(`/api/${companyCode}/department/list`);
            return data;
        }
    });

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
        <div>
            <div>
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
            </div>
        </div>
    );
};

export default DepartmentSidebar;
