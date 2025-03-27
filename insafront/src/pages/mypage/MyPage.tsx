import React, {useEffect, useState} from "react";
import styles from "@/styles/mypage/MyPage.module.css";
import {profileCardDTO} from "@/type/profilecard";
import ProfileCard from "@/component/mypage/ProfileCard";
import Calendar from "@/component/mypage/Calendar";
import profileCardAction from "@/api/mypage/profilecardaction";
import {fetchAttendanceRecords} from "@/services/attendanceService";
import {useQuery} from "@tanstack/react-query";
import AttendanceAction from "@/services/attendanceAction";


const MyPage = () => {

  const [userData, setUserData] = useState<profileCardDTO | null>(null);
  const [employeeId, setEmployeeId] = useState<string>("defaultId"); // 로그인한 사용자의 ID 가져오기


  useEffect(() => {
    // 클라이언트에서만 실행되도록 보장
    if (typeof window !== "undefined") {
      const storedEmployeeId = localStorage.getItem("employeeId") || "defaultId";
      setEmployeeId(storedEmployeeId);
    }
  }, []);
  const {
    data: attendanceRecords = [],
    isLoading,
    isError,
    error
  } = useQuery({
    queryKey: ['attendanceRecords', employeeId],
    queryFn: () => fetchAttendanceRecords(employeeId),
  });


  //profileCard 출력
  useEffect(() => {
    if (employeeId) {
      const fetchData = async () => {
        const data = await profileCardAction(employeeId);
        if (data) {
          setUserData(data);
        } else {
          console.warn("No data found.");
        }
      };
      fetchData();
    }
  }, [employeeId]);

  return (
      <div className={styles.container}>
        <div className={styles.topSection}>
          {/* Profile & Quick Menu Section */}
          {userData ? <ProfileCard{...userData}/> : <p>Loading</p>}

          {/* Calendar Section */}
          <div className={styles.calendarCard}>
            {userData ? <Calendar employeeId={userData.employeeId}/> : <p>Loading</p>}
          </div>
        </div>

        <div className={styles.attendanceWrapper}>
          {/* Attendance Section */}
          {/*<AttendanceCard/>*/}
          <AttendanceAction employeeId={employeeId} attendanceRecords={attendanceRecords}/>

        </div>

      </div>
  );
};

export default MyPage;
