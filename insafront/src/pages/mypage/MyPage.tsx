import React, { useState, useEffect } from "react";
import { NextPage } from "next";
import styles from "@/styles/mypage/MyPage.module.css";
import {fetcher} from "@/pages/api/fetcher";
import {SmallprofileDTO} from "@/type/smallprofile";

const MyPage = ({name,phoneNumber,departmentId,positionSalaryId}:SmallprofileDTO) => {
    const [clockInTime, setClockInTime] = useState<string | null>(null);
    const [clockOutTime, setClockOutTime] = useState<string | null>(null);
    const [userData, setUserData] = useState(null);

    const handleClockIn = () => {
        setClockInTime(new Date().toLocaleTimeString());
    };

    const handleClockOut = () => {
        setClockOutTime(new Date().toLocaleTimeString());
    };

    useEffect(() => {
        fetcher("/mypage/MyPage")
            .then((data)=>setUserData(data))
            .catch(console.error);
    }, []);

    return (
        <div className={styles.container}>
            <div className={styles.topSection}>
                {/* Profile & Quick Menu Section */}
                <div className={styles.profileWrapper}>
                    <div className={styles.profileCard}>
                        <div className={styles.profileInfo}>
                            <div className={styles.avatar}>사진 없음</div>
                            <h2>이름 : {name}</h2>
                            <p>부서명 : {departmentId}</p>
                            <p>직급 : {positionSalaryId}</p>
                            <p>전화번호 : {phoneNumber}</p>
                            <button className={styles.button}>정보 변경</button>
                        </div>
                    </div>
                    {/* Quick Menu */}
                    <div className={styles.quickMenu}>
                        <h2>간편 메뉴</h2>
                        <div className={styles.buttonContainer}>
                            <div className={styles.menuItem}>기안하기</div>
                            <div className={styles.menuItem}>결재 진행 중</div>
                            <div className={styles.menuItem}>결재 완료</div>
                        </div>
                    </div>
                </div>

                {/* Calendar Section */}
                <div className={styles.calendarCard}>
                    <h2>근태 달력</h2>
                    <p>(캘린더 컴포넌트 추가 필요)</p>
                </div>
            </div>

            <div className={styles.attendanceWrapper}>
                {/* Attendance Section */}
                <div className={styles.attendanceCard}>
                    <div className={styles.attendanceHeader}>개인 근태 현황</div>
                    <div className={styles.attendanceBody}>
                        <div className={styles.attendanceItem}>출근 시간<br />{clockInTime || "--:--"}</div>
                        <div className={styles.attendanceItem}>퇴근 시간<br />{clockOutTime || "--:--"}</div>
                    </div>
                </div>

                {/* Additional Work Info */}
                <div className={styles.additionalInfo}>
                    <div className={styles.infoCard}>
                        <h3>시간외근무(당월)</h3>
                        <div className={styles.infoItem}><span>근무</span> <span>2시간 50분</span></div>
                        <div className={styles.infoItem}><span>인정</span> <span>2시간 50분</span></div>
                    </div>
                    <div className={styles.infoCard}>
                        <h3>휴일근무(당월)</h3>
                        <div className={styles.infoItem}><span>근무</span> <span>0시간 0분</span></div>
                        <div className={styles.infoItem}><span>인정</span> <span>0시간 0분</span></div>
                    </div>
                </div>
            </div>

            <div className={styles.bottomSection}>
                {/* Time Buttons */}
                <div className={styles.inputCard}>
                    <h2>근무 기록 입력</h2>
                    <button className={styles.button} onClick={handleClockIn}>출근 시간 입력</button>
                    <button className={styles.button} onClick={handleClockOut}>퇴근 시간 입력</button>
                </div>
            </div>
        </div>
    );
};

export default MyPage;
