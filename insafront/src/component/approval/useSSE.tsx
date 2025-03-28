// component/approval/useSSE.tsx
import {useEffect} from 'react';

const useSSE = (employeeId: string | null, onMessage: (msg: string) => void) => {
  useEffect(() => {
    if (!employeeId) return;

    const eventSource = new EventSource(`http://127.0.0.1:1006/approval/sse/subscribe/${employeeId}`);

    // 공통 핸들러 등록
    const handleEvent = (event: MessageEvent) => {
      console.log('수신된 이벤트:', event.data);
      onMessage(event.data);
    };

    eventSource.addEventListener("approval-update", handleEvent);
    eventSource.addEventListener("broadcast", handleEvent);

    eventSource.onerror = (error) => {
      console.error('SSE 연결 오류:', error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, [employeeId, onMessage]);
};

export default useSSE;
