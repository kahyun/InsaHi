import React, {useEffect, useState} from "react";
import DepartmentSidebar from "@/component/department/departmentSidebar"; // Navigation 대신 Sidebar 사용

// NodeViewer와 TableViewer 컴포넌트 임포트
import NodeViewer from "@/component/department/layout/nodeViewer";
import TableViewer from "@/component/department/layout/tableViewer";
import {Container,Right} from './styled'
import TopBar from "@/component/topbar/Topbar";
import Topbar from "@/component/topbar/Topbar";
import {useRouter} from "next/router";

const IndexLayout = ({ children }: { children: React.ReactNode }) => {
    const [viewType, setViewType] = useState<"node" | "table">("table"); // 뷰 전환 상태
    const [selectedDepartment, setSelectedDepartment] = useState<string>(""); // 부서 ID 관리

    const [activeSidebar, setActiveSidebar] = useState<string | null>(null);
    const router = useRouter();
    // const isfullWidthPage = ["/mypage", "/chat"].some(path => router.pathname.startsWith(path));
    useEffect(() => {
        if (
            router.pathname.startsWith("/mypage") ||
            router.pathname.startsWith("/chat")
        ) {
            setActiveSidebar(null); // 마이페이지나 채팅으로 이동하면 사이드바 닫기
        }
    }, [router.pathname]);

    return (
        <Container className={"Container"}>
            <Topbar activeSidebar={activeSidebar} setActiveSidebar={setActiveSidebar}/>
            {/* 사이드바 (DepartmentSidebar 컴포넌트) */}
            <DepartmentSidebar
                setViewType={setViewType}
                setSelectedDepartment={setSelectedDepartment} // 부서 선택 함수 전달
            />

            {/* 콘텐츠 영역 */}
            <Right className={"Right"}>
                {/* viewType에 따라 다른 컴포넌트 렌더링 */}
                {viewType === "node" ? <NodeViewer selectedDepartment={selectedDepartment} /> : <TableViewer selectedDepartment={selectedDepartment} />}
            </Right>
        </Container>
    );
};

export default IndexLayout;
