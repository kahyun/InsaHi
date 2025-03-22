import {useState, useEffect} from 'react';
import styles from '@/styles/approval/Documents.module.css';

interface Document {
  id: string;
  name: string;
  text: string;
  employeeId: string;
  status: string;
}

const DocumentsPage = () => {
  const [menu, setMenu] = useState<number>(0);
  const [documents, setDocuments] = useState<Document[]>([]);
  const [companyCode, setCompanyCode] = useState<string>('');
  const [employeeId, setEmployeeIdToken] = useState<string | null>(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const storedEmployeeId = localStorage.getItem('employeeId') || 'defaultId';
      const storedcompanyCode = localStorage.getItem('companyCode') || 'defaultId';
      setEmployeeIdToken(storedEmployeeId);
      setCompanyCode(storedcompanyCode);
    }
  }, []);

  useEffect(() => {
    const fetchDocuments = async () => {
      if (employeeId) {
        const response = await fetch(`http://127.0.0.1:1005/approval/list/${employeeId}/${menu}`);
        const data = await response.json();
        setDocuments(data);
      }
    };
    fetchDocuments();
  }, [menu, employeeId]);

  return (
      <div className={styles.documentsPageContainer}>
        <div className={styles.documentsMainContent}>
          <h1 className={styles.documentsPageTitle}>결재 문서 목록</h1>

          <div className={styles.documentsFilterContainer}>
            <button
                className={menu === 1 ? styles.documentsCreateButton : styles.documentsPaginationButton}
                onClick={() => setMenu(1)}
            >
              본인이 작성한 문서
            </button>
            <button
                className={menu === 2 ? styles.documentsCreateButton : styles.documentsPaginationButton}
                onClick={() => setMenu(2)}
            >
              결재자로 지정된 문서
            </button>
            <button
                className={menu === 3 ? styles.documentsCreateButton : styles.documentsPaginationButton}
                onClick={() => setMenu(3)}
            >
              결재해야 할 문서
            </button>
          </div>

          <div>
            <h2>문서 목록</h2>
            <ul>
              {documents.length === 0 ? (
                  <p className={styles.documentsEmptyState}>조회된 문서가 없습니다.</p>
              ) : (
                  documents.map((doc) => (
                      <li key={doc.id} className={styles.documentsTable}>
                        <a
                            href={`/approval/file/${doc.id}`}
                            className={styles.documentsActionLink}
                        >
                          {doc.name} - {doc.status === 'APPROVED' ? '승인됨' : doc.status === 'REJECTED' ? '반려됨' : '진행 중'}
                        </a>
                      </li>
                  ))
              )}
            </ul>
          </div>
        </div>
      </div>
  );
};

export default DocumentsPage;
