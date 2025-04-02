import React, { useState, useRef, useEffect } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import styles from "@/styles/mypage/Calendar.module.css";
import { CalendarDTO } from "@/api/mypage/calendaraction";
import CalendarModal from "@/component/department/modal/CalendarModal";

interface CalendarProps {
  leaveList: CalendarDTO[];
}

const MiniCalendar = ({ leaveList }: CalendarProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedLeave, setSelectedLeave] = useState<any>(null);
  const calendarRef = useRef<FullCalendar | null>(null);
  const [currentDate, setCurrentDate] = useState(new Date());

  const { startOfThisWeek, endOfThisWeek, startOfNextWeek, endOfNextWeek } = getWeekRange();

  // 날짜 포맷팅 함수
  const formatDate = (date: Date) => {
    const options: Intl.DateTimeFormatOptions = { year: "numeric", month: "2-digit", day: "2-digit", weekday: "short" };
    return new Intl.DateTimeFormat("ko-KR", options).format(date);
  };

  // 이번 주 & 다음 주 필터링
  const filteredLeaveList = leaveList.filter((leave) => {
    const leaveStartDate = new Date(leave.startDate);
    const leaveEndDate = new Date(leave.stopDate);
    return (
        (leaveStartDate >= startOfThisWeek && leaveStartDate <= endOfThisWeek) ||
        (leaveEndDate >= startOfNextWeek && leaveEndDate <= endOfNextWeek)
    );
  });

  const events = filteredLeaveList.map((leave) => ({
    title: "연차",
    start: leave.startDate,
    end: getNextDay(leave.stopDate),
    allDay: true,
    extendedProps: leave,
  }));

  const handleEventClick = (info: any) => {
    setSelectedLeave(info.event.extendedProps);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  // 오늘 날짜로 이동
  const moveToToday = () => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.today();
      setCurrentDate(new Date());
    }
  };

  // 이전 주 이동
  const moveToPrev = () => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.prev();
      setCurrentDate(new Date(calendarApi.getDate()));
    }
  };

  // 다음 주 이동
  const moveToNext = () => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.next();
      setCurrentDate(new Date(calendarApi.getDate()));
    }
  };

  useEffect(() => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      calendarApi.today();
    }
  }, []);

  return (
      <div>
        {/* 캘린더 헤더 */}
        <div className="CalendarHeader">
            <p>휴가기록</p>
          <div style={headerRightStyle}>
            <button onClick={moveToPrev}>{"<"}</button>
            <div >
              <h3>{formatDate(currentDate)}</h3>
              <button className={styles.todayButton} onClick={moveToToday}>
                오늘 날짜로 이동하기
              </button>
            </div>
            <button onClick={moveToNext}>{">"}</button>
          </div>
        </div>

        {/* 풀 캘린더 */}
        <FullCalendar
            ref={calendarRef}
            plugins={[dayGridPlugin]}
            initialView="dayGridWeek"
            headerToolbar={false} // 기본 헤더 제거
            events={events}
            aspectRatio={2}
            eventClick={handleEventClick}
        />

        {/* 모달 */}
        <CalendarModal isOpen={isModalOpen} closeModal={closeModal} leave={selectedLeave} />
      </div>
  );
};

// 주간 범위 계산 함수
function getWeekRange() {
  const today = new Date();
  const startOfThisWeek = new Date(today);
  const endOfThisWeek = new Date(today);

  startOfThisWeek.setDate(today.getDate() - today.getDay() + 1);
  startOfThisWeek.setHours(0, 0, 0, 0);

  endOfThisWeek.setDate(today.getDate() - today.getDay() + 7);
  endOfThisWeek.setHours(23, 59, 59, 999);

  const startOfNextWeek = new Date(startOfThisWeek);
  startOfNextWeek.setDate(startOfThisWeek.getDate() + 7);

  const endOfNextWeek = new Date(endOfThisWeek);
  endOfNextWeek.setDate(endOfThisWeek.getDate() + 7);

  return { startOfThisWeek, endOfThisWeek, startOfNextWeek, endOfNextWeek };
}

// 마지막 날짜 포함 함수
function getNextDay(dateString: string) {
  const date = new Date(dateString);
  date.setDate(date.getDate() + 1);
  return date.toISOString().split("T")[0];
}

// 스타일 객체
const headerStyle = {
  width: '100%',
  display: 'flex',
  flexDirection: 'column',
};

const headerLeftStyle = {
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
};

const headerRightStyle = {
  display: 'flex',
  width: '100%',
  justifyContent: "space-between",
};

const dateContainerStyle = {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  margin: '0 10px',
};

export default MiniCalendar;
