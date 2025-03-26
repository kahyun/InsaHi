// @/src/component/mypage/Calendar.tsx

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import styles from "@/styles/mypage/Calendar.module.css";

interface CalendarProps {
  employeeId: string;
}

const Calendar = ({employeeId}: CalendarProps) => {
  return (
      <div className={styles.calendarContainer}>
        <h2 className="mb-2 text-lg font-semibold">캘린더 - 사번: {employeeId}</h2>
        <FullCalendar
            plugins={[dayGridPlugin]}
            initialView="dayGridMonth"
            headerToolbar={{
              left: "prev",
              center: "title",
              right: "next",
            }}
            events={[
              {title: "회의", start: "2025-03-20"},
              {title: "세미나", start: "2025-03-22"},
            ]}
            aspectRatio={2}
            height="500px"
        />
      </div>
  );
};

export default Calendar;
