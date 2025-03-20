import {useState, useEffect} from 'react';
import {useRouter} from 'next/router';
import styles from '@/styles/approval/ApprovalDetail.module.css';

interface File {
  approvalFileNo: string;
  originalFilename: string;
  storeFilename: string;
}

interface ApprovalLine {
  id: string;
  approvalOrder: number;
  employeeId: string;
  approvalStatus: string;
}

interface ApprovalFile {
  id: string;
  name: string;
  text: string;
  companyCode: string;
  employeeId: string;
  status: string;
  files: File[];
  approvalLines: ApprovalLine[];
}

const FilePage = () => {
  const router = useRouter();
  const {approvalFileId} = router.query;
  const [approvalFile, setApprovalFile] = useState<ApprovalFile | null>(null);
  const [employeeIdToken, setEmployeeIdToken] = useState<string | null>(null);
  const [reason, setReason] = useState<string>('');

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const storedEmployeeId = localStorage.getItem('employeeId') || 'defaultId';
      setEmployeeIdToken(storedEmployeeId);
    }
  }, []);

  useEffect(() => {
    if (!approvalFileId) return;

    const fetchApprovalFileDetails = async () => {
      try {
        const response = await fetch(`http://127.0.0.1:1005/approval/file/${approvalFileId}`);
        if (!response.ok) throw new Error(`Failed to fetch, status: ${response.status}`);
        const data = await response.json();
        setApprovalFile(data);
      } catch (error) {
        console.error("Error fetching file details:", error);
      }
    };
    fetchApprovalFileDetails();
  }, [approvalFileId]);

  const handleApproval = async (lineId: string, status: string) => {
    const response = await fetch(
        `http://127.0.0.1:1005/approval/permit?lineId=${lineId}&approveOrNot=${status}&reason=${reason}`,
        {method: 'GET'}
    );
    if (response.ok) {
      alert('결재 처리 완료');
    } else {
      alert('결재 처리 실패');
    }
  };

  const handleDownload = async (fileId: string, fileName: string) => {
    try {
      const response = await fetch(`http://127.0.0.1:1005/approval/file/download/${fileId}`);
      const blob = await response.blob();
      const link = document.createElement('a');
      const url = window.URL.createObjectURL(blob);
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error("Error during file download:", error);
    }
  };

  if (!approvalFile) return <div>Loading...</div>;

  return (
      <div className={styles.approvalDetailPageContainer}>
        <div className={styles.approvalDetailMainContent}>
          <h1 className={styles.approvalDetailPageTitle}>결재 문서 상세</h1>
          <div className={styles.approvalDetailCard}>
            <p>문서 제목: {approvalFile.name}</p>
            <p>문서 내용: {approvalFile.text}</p>
            <p>결재 문서 상신자: {approvalFile.employeeId}</p>
            <p>결재 상태: {approvalFile.status}</p>
          </div>

          <div className={styles.approvalDetailCard}>
            <h2>결재자 목록</h2>
            {approvalFile.approvalLines.map(line => (
                <div key={line.id} className={styles.approvalDetailFieldGroup}>
                  <span>순서: {line.approvalOrder}, 결재자 ID: {line.employeeId}</span>
                  {line.employeeId === employeeIdToken && line.approvalStatus === 'PENDING' ? (
                      <>
                  <textarea
                      className={styles.approvalDetailTextarea}
                      placeholder="승인/반려 사유를 입력하세요"
                      value={reason}
                      onChange={(e) => setReason(e.target.value)}
                  />
                        <div className={styles.approvalDetailActionButtons}>
                          <button
                              className={styles.approvalDetailApproveButton}
                              onClick={() => handleApproval(line.id, 'approved')}
                          >
                            승인
                          </button>
                          <button
                              className={styles.approvalDetailRejectButton}
                              onClick={() => handleApproval(line.id, 'rejected')}
                          >
                            반려
                          </button>
                        </div>
                      </>
                  ) : (
                      <span>결재 완료</span>
                  )}
                </div>
            ))}
          </div>

          <div className={styles.approvalDetailCard}>
            <h2>첨부파일 목록</h2>
            {approvalFile.files.map(file => (
                <div key={file.approvalFileNo} className={styles.approvalDetailFieldGroup}>
                  <span>{file.originalFilename}</span>
                  <button
                      className={styles.approvalDetailApproveButton}
                      onClick={() => handleDownload(file.approvalFileNo, file.originalFilename)}
                  >
                    다운로드
                  </button>
                </div>
            ))}
          </div>
        </div>
      </div>
  );
};

export default FilePage;
