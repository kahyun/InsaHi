
import React, { useState } from "react";
import Link from "next/link";
import Sidebar1 from "../sidebar/Sidebar1";
import Sidebar2 from "../sidebar/Sidebar2";
import Sidebar3 from "../sidebar/Sidebar3";
import Sidebar4 from "../sidebar/Sidebar4";
import "../../styles/Topbar.css";

const TopBar: React.FC = () => {
    const [activeSidebar, setActiveSidebar] = useState<string | null>(null);

    return (
        <div className="container">
            {/* 헤더 */}
            <header className="topbar">
                <nav className="nav">
                    {/* 왼쪽 로고 */}
                    <div className="logo">인사 HI</div>

                    {/* 중앙 메뉴 */}
                    <ul className="menu">
                        <li>
                            <Link
                                href="#"
                                className="menu-link"
                                onClick={() => setActiveSidebar("sidebar1")}
                            >
                                근태/급여
                            </Link>
                        </li>
                        <li>
                            <Link
                                href="#"
                                className="menu-link"
                                onClick={() => setActiveSidebar("sidebar2")}
                            >
                                전자결재
                            </Link>
                        </li>
                        <li>
                            <Link
                                href="#"
                                className="menu-link"
                                onClick={() => setActiveSidebar("sidebar3")}
                            >
                                주소록
                            </Link>
                        </li>
                        <li>
                            <Link
                                href="#"
                                className="menu-link"
                                onClick={() => setActiveSidebar("sidebar4")}
                            >
                                회의실
                            </Link>
                        </li>
                        <li><Link href="#" className="menu-link">게시판</Link></li>
                        <li><Link href="#" className="menu-link">인사관리</Link></li>
                    </ul>

                    {/* 오른쪽 아이콘 */}
                    <div className="icons">
                        <div>검색</div>
                        <div>알림</div>
                        <div>사용자</div>
                    </div>
                </nav>
            </header>

            {/* 조건부 Sidebar 렌더링 */}
            <div className="sidebar-container">
                {activeSidebar === "sidebar1" && <Sidebar1 />}
                {activeSidebar === "sidebar2" && <Sidebar2 />}
                {activeSidebar === "sidebar3" && <Sidebar3 />}
                {activeSidebar === "sidebar4" && <Sidebar4 />}
            </div>
        </div>
    );
};

export default TopBar;
