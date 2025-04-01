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

  const { startOfThisWeek, endOfThisWeek, startOfNextWeek, endOfNextWeek } = getWeekRange();

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

  // 📌 FullCalendar가 로드되면 오늘 날짜로 이동
  useEffect(() => {
    if (calendarRef.current) {
      const calendarApi = calendarRef.current.getApi();
      if (calendarApi) {
        calendarApi.today();
      }
    }
  }, []);

  return (
      <div className={styles.calendarContainer}>
        <h2 className="mb-2 text-lg font-semibold">내 연차 캘린더</h2>
        <FullCalendar
            ref={calendarRef} // 캘린더 참조 추가
            plugins={[dayGridPlugin]}
            initialView="dayGridWeek"
            headerToolbar={{
              left: "prev today", // '오늘' 버튼 추가
              center: "title",
              right: "next",
            }}
            events={events}
            aspectRatio={2}
            height="500px"
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

export default MiniCalendar;
