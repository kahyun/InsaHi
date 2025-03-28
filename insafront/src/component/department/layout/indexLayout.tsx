import React, { useState } from "react";
import DepartmentSidebar from "@/component/department/departmentSidebar"; // Navigation 대신 Sidebar 사용

// NodeViewer와 TableViewer 컴포넌트 임포트
import NodeViewer from "@/component/department/layout/nodeViewer";
import TableViewer from "@/component/department/layout/tableViewer";

const IndexLayout = ({ children }: { children: React.ReactNode }) => {
    const [viewType, setViewType] = useState<"node" | "table">("table"); // 뷰 전환 상태
    const [selectedDepartment, setSelectedDepartment] = useState<string>(""); // 부서 ID 관리

    return (
        <div >
            {/* 사이드바 (DepartmentSidebar 컴포넌트) */}
            <DepartmentSidebar
                setViewType={setViewType}
                setSelectedDepartment={setSelectedDepartment} // 부서 선택 함수 전달
            />

            {/* 콘텐츠 영역 */}
            <div >
                {/* viewType에 따라 다른 컴포넌트 렌더링 */}
                {viewType === "node" ? <NodeViewer selectedDepartment={selectedDepartment} /> : <TableViewer selectedDepartment={selectedDepartment} />}
            </div>
        </div>
    );
};

export default IndexLayout;
