// components/sidebar/DepartmentSide.tsx
import React from 'react';
import { Department } from '@/type/Department'; // 부서 타입

interface DepartmentSideProps {
    departments: Department[]; // 부서 리스트
}

const DepartmentSide: React.FC<DepartmentSideProps> = ({ departments }) => {
    const renderDepartmentList = (departmentList: Department[]) => {
        return (
            <ul className="list-none p-0">
                {departmentList.map((department) => (
                    <li key={department.departmentId} className="mb-2">
                        {/* 부서 이름 클릭 시 해당 부서로 스크롤 이동 */}
                        <a
                            href={`#${department.departmentId}`}
                            className="text-blue-600 hover:underline"
                        >
                            {department.departmentName}
                        </a>

                        {/* 하위 부서가 있으면 재귀적으로 렌더링 */}
                        {department.subDepartments && department.subDepartments.length > 0 && (
                            <div className="pl-4 mt-2">
                                {renderDepartmentList(department.subDepartments)}
                            </div>
                        )}
                    </li>
                ))}
            </ul>
        );
    };

    return (
        <div className="bg-gray-200 p-4 w-full h-full overflow-y-auto">
            <h2 className="font-bold text-lg">부서 목록</h2>
            {renderDepartmentList(departments)}
        </div>
    );
};

export default DepartmentSide;
