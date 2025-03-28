// @/src/component/mypage/Calendar.tsx

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import styles from "@/styles/mypage/Calendar.module.css";
import {CalendarDTO} from "@/api/mypage/calendaraction";


const Calendar = ({employeeId, startDate, stopDate}: CalendarDTO) => {
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
              // {title: "휴가", start: startDate, end: stopDate}
            ]}
            aspectRatio={2}
            height="500px"
        />
      </div>
  );
};

export default Calendar;
