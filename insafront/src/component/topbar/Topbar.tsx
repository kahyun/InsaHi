import React, {useState} from "react";
import Link from "next/link";
import Sidebar1 from "../sidebar/Sidebar1";
import Sidebar2 from "../sidebar/Sidebar2";
import Sidebar4 from "../sidebar/Sidebar4";
import Sidebar5 from "../sidebar/Sidebar5";
import Sidebar6 from "../sidebar/Sidebar6";


import styles from "@/styles/Topbar.module.css";
import {useRouter} from "next/router";
import {profileCardDTO} from "@/type/profilecard";

import useSSE from "@/component/approval/useSSE";
import Toast from "@/component/approval/Toast";

const TopBar = ({name}: profileCardDTO) => {
  const [activeSidebar, setActiveSidebar] = useState<string | null>(null);
  const [hasNotification, setHasNotification] = useState(false);
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  const router = useRouter();

  // 전자결재 - 유저 정보 가져오기 (employeeId)
  const employeeId =
      typeof window !== "undefined" ? localStorage.getItem("employeeId") : null;

  // 전자결재 - SSE 알림 수신 (알림 상태 갱신 + 토스트)
  useSSE(employeeId, (message) => {
    setHasNotification(true); // 뱃지 표시
    setToastMessage(message); // 토스트 알림 표시
  });

  // 전자결재 - 알림 클릭 시 핸들러
  // const handleNotificationClick = () => {
  //   setHasNotification(false);
  //   router.push("/notifications"); // 알림 센터 페이지로 이동
  // };


  // 로그아웃 핸들러
  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("employeeId");
    localStorage.removeItem("companyCode");
    alert("로그아웃 되었습니다.");
    router.push("/");
  };


  return (
      <div className={styles.topcontainer}>
        {/* 헤더 */}
        <header className={styles.toptopbar}>
          <nav className={styles.topnav}>
            {/* 왼쪽 로고 */}
            <Link href={"/mypage/MyPage"} className={styles.toplogo}>인사 HI</Link>

            {/* 중앙 메뉴 */}
            <ul className={styles.topmenu}>
              <li>
                <Link
                    href="#"
                    className={styles.topmenulink}
                    onClick={() => setActiveSidebar("sidebar1")}
                >
                  근태/급여
                </Link>
              </li>
              <li>
                <Link
                    href="#"
                    className={styles.topmenulink}
                    onClick={() => setActiveSidebar("sidebar2")}
                >
                  전자결재
                </Link>
                {hasNotification && <span className={styles.notificationDot}/>}
              </li>
              <li>
                <Link
                    href="#"
                    className={styles.topmenulink}
                    onClick={() => setActiveSidebar("sidebar3")}
                >
                  주소록
                </Link>
              </li>
              <li>
                <Link
                    href="#"
                    className={styles.topmenulink}
                    onClick={() => setActiveSidebar("sidebar4")}
                >
                  회의실
                </Link>
              </li>
              <li><Link href="#"
                        className={styles.topmenulink}
                        onClick={() => setActiveSidebar("sidebar5")}
              >게시판</Link></li>
              <li><Link href="#"
                        className={styles.topmenulink}
                        onClick={() => setActiveSidebar("sidebar6")}
              >인사관리</Link></li>
            </ul>

            {/* 오른쪽 아이콘 */}
            <div className={styles.topicons}>
              <div>알림</div>
              <Link href={"/mypage/MyPage"} className={styles.user}>
                {name} 님
              </Link>
              <button className={styles.logoutButton} onClick={handleLogout}>
                로그아웃
              </button>
            </div>
          </nav>
        </header>

        {/* 조건부 Sidebar 렌더링 */}
        <div className={styles.topsidebarcontainer}>
          {activeSidebar === "sidebar1" && <Sidebar1/>}
          {activeSidebar === "sidebar2" && <Sidebar2/>}
          {activeSidebar === "sidebar4" && <Sidebar4/>}
          {activeSidebar === "sidebar5" && <Sidebar5/>}
          {activeSidebar === "sidebar6" && <Sidebar6/>}
        </div>
        {/* 전자결재 - Toast 알림 */}
        {toastMessage && (
            <Toast message={toastMessage} onClose={() => setToastMessage(null)}/>
        )}

      </div>
  );
};

export default TopBar;