// components/department/DepartmentView.tsx
import React from 'react';
import { Department } from '@/type/Department'; // 부서 타입

interface DepartmentViewProps {
    department: Department; // 부서 정보
}

const DepartmentView: React.FC<DepartmentViewProps> = ({ department }) => {
    return (
        <div className="border p-4 mb-4">
            <h2 className="text-xl font-bold">{department.departmentName}</h2>
            <div className="text-gray-600">부서 ID: {department.departmentId}</div>

            {/* 직원 목록 */}
            {department.employees && department.employees.length > 0 && (
                <div className="mt-4">
                    <h3 className="font-semibold">직원 목록</h3>
                    <ul className="list-disc pl-5">
                        {department.employees.map((employee) => (
                            <li key={employee.employeeId}>
                                {employee.employeeName} ({employee.positionName})
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {/* 하위 부서 */}
            {department.subDepartments && department.subDepartments.length > 0 && (
                <div className="mt-4">
                    <h3 className="font-semibold">하위 부서</h3>
                    <div className="pl-5">
                        {department.subDepartments.map((subDept) => (
                            <DepartmentView key={subDept.departmentId} department={subDept} />
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
};

export default DepartmentView;
