import React from 'react';
import { Department } from '@/type/Department';

interface DepartmentSideProps {
    departments: Department[]; // 부서 리스트
    companyCode: string; // 회사 코드
}

const DepartmentSide: React.FC<DepartmentSideProps> = ({ departments, companyCode }) => {
    return (
        <div className="bg-gray-200 p-4 w-full">
            <h2 className="font-bold text-lg">부서 목록</h2>
            <ul className="list-none p-0">
                {departments.map((department) => (
                    <li key={department.departmentId} className="mb-2">
                        <a href={`#${department.departmentId}`} className="text-blue-600 hover:underline">
                            {department.departmentName}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DepartmentSide;
