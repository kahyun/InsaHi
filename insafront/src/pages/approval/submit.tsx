import {useState, ChangeEvent, FormEvent, useEffect} from 'react';
import styles from '@/styles/approval/Submit.module.css';

interface FormData {
  id: string;
  name: string;
  text: string;
}

const SubmitPage = () => {
  const [formData, setFormData] = useState<FormData>({
    id: '',
    name: '',
    text: '',
  });
  const [companyCode, setCompanyCode] = useState<string>('');
  const [employeeId, setEmployeeIdToken] = useState<string | null>(null);
  const [approvers, setApprovers] = useState<string[]>(['']);
  const [referencedIds, setReferencedIds] = useState<string[]>(['']);

  const [approverErrors, setApproverErrors] = useState<string[]>(['']);
  const [referencedErrors, setReferencedErrors] = useState<string[]>(['']);
  const handleApproverChange = (index: number, value: string) => {
    const updated = [...approvers];
    updated[index] = value;
    setApprovers(updated);

    const errors = [...approverErrors];
    errors[index] = value.trim() === '' ? '결재자 ID를 입력하세요.' : '';
    setApproverErrors(errors);
  };

  const handleReferencedChange = (index: number, value: string) => {
    const updated = [...referencedIds];
    updated[index] = value;
    setReferencedIds(updated);

    const errors = [...referencedErrors];
    errors[index] = value.trim() === '' ? '참조자 ID를 입력하세요.' : '';
    setReferencedErrors(errors);
  };


  const addApproverField = () => {
    setApprovers([...approvers, '']);
    setApproverErrors([...approverErrors, '']);
  };

  const removeApproverField = (index: number) => {
    const updated = [...approvers];
    updated.splice(index, 1);
    setApprovers(updated);

    const errors = [...approverErrors];
    errors.splice(index, 1);
    setApproverErrors(errors);
  };

  const addReferencedField = () => {
    setReferencedIds([...referencedIds, '']);
    setReferencedErrors([...referencedErrors, '']);
  };

  const removeReferencedField = (index: number) => {
    const updated = [...referencedIds];
    updated.splice(index, 1);
    setReferencedIds(updated);

    const errors = [...referencedErrors];
    errors.splice(index, 1);
    setReferencedErrors(errors);
  };
  const [allUsers, setAllUsers] = useState<{ employeeId: string; name: string }[]>([]);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const storedEmployeeId = localStorage.getItem('employeeId') || '';
      const storedCompanyCode = localStorage.getItem('companyCode') || '';
      setEmployeeIdToken(storedEmployeeId);
      setCompanyCode(storedCompanyCode);

      let token = localStorage.getItem("accessToken");
      if (token && !token.startsWith("Bearer ")) {
        token = `Bearer ${token}`;
      }

      if (!token) {
        console.error("토큰이 없습니다.");
        return;
      }

      fetch("http://127.0.0.1:1006/employee/all", {
        method: "GET",
        headers: {
          Authorization: token,
          "Content-Type": "application/json"
        }
      })
      .then(res => {
        console.log(res)
        if (!res.ok) throw new Error("사용자 목록 조회 실패");
        return res.json();
      })
      .then((data) => {
        console.log(data);
        const filtered = data
        .filter((user: any) => user.employeeId !== storedEmployeeId && user.companyCode === storedCompanyCode);
        setAllUsers(filtered);
      })
      .catch(err => console.error("사용자 목록 불러오기 에러:", err));
    }
  }, []);


  const handleInputChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const {name, value} = e.target;
    setFormData((prevData) => ({...prevData, [name]: value}));
  };

  const [files, setFiles] = useState<File[]>([]);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const fileList = Array.from(e.target.files);
      setFiles(fileList);
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const isApproverValid = approvers.every(v => v.trim() !== '');
    const isReferencedValid = referencedIds.every(v => v.trim() !== '');

    if (!isApproverValid || !isReferencedValid) {
      const updatedApproverErrors = approvers.map(v => v.trim() === '' ? '결재자 ID를 입력하세요.' : '');
      const updatedReferencedErrors = referencedIds.map(v => v.trim() === '' ? '참조자 ID를 입력하세요.' : '');
      setApproverErrors(updatedApproverErrors);
      setReferencedErrors(updatedReferencedErrors);
      alert('결재자 및 참조자 ID를 모두 입력해주세요.');
      return;
    }

    const formPayload = new FormData();

    const jsonData = {
      id: formData.id,
      name: formData.name,
      text: formData.text,
      companyCode: companyCode,
      employeeId: employeeId,
      approvers: approvers.filter(v => v.trim() !== ''),
      referencedIds: referencedIds.filter(v => v.trim() !== '')
    };

    formPayload.append('jsonData', JSON.stringify(jsonData));

    files.forEach(file => {
      formPayload.append('files', file);
    });

    try {
      const response = await fetch('http://127.0.0.1:1006/approval/submit', {
        method: 'POST',
        body: formPayload
      });

      if (response.ok) {
        alert('성공!');
      } else {
        alert(`실패! 상태코드: ${response.status}`);
      }
    } catch (error) {
      console.error('에러:', error);
    }
  };

  return (
      <div className={styles.submitPageContainer}>
        <div className={styles.submitMainContent}>
          <h1 className={styles.submitPageTitle}>결재 문서 상신 (파일 포함)</h1>
          <form onSubmit={handleSubmit} className={styles.submitCard}>
            <div className={styles.submitFormGroup}>
              <label className={styles.submitLabel}>제목</label>
              <input
                  type="text"
                  name="name"
                  className={styles.submitInput}
                  value={formData.name}
                  onChange={handleInputChange}
                  required
              />
            </div>

            <div className={styles.submitFormGroup}>
              <label className={styles.submitLabel}>내용</label>
              <textarea
                  name="text"
                  className={styles.submitTextarea}
                  value={formData.text}
                  onChange={handleInputChange}
                  required
              />
            </div>

            <div className={styles.submitFormGroup}>
              <label className={styles.submitLabel}>결재자</label>
              {approvers.map((value, index) => (
                  <div key={index} className={styles.inputWithRemove}>
                    <select
                        className={styles.submitInput}
                        value={value}
                        onChange={(e) => handleApproverChange(index, e.target.value)}
                    >
                      <option value="">-- 결재자 선택 --</option>
                      {allUsers.map(user => (
                          <option key={user.employeeId} value={user.employeeId}>
                            {user.name} ({user.employeeId})
                          </option>
                      ))}
                    </select>
                    {approvers.length > 1 && (
                        <button type="button" onClick={() => removeApproverField(index)}
                                className={styles.removeButton}>🗑️</button>
                    )}
                    {approverErrors[index] &&
                        <p className={styles.errorText}>{approverErrors[index]}</p>}
                  </div>
              ))}

              <button type="button" onClick={addApproverField} className={styles.addButton}>+ 추가
              </button>
            </div>

            <div className={styles.submitFormGroup}>
              <label className={styles.submitLabel}>참조자</label>
              {referencedIds.map((value, index) => (
                  <div key={index} className={styles.inputWithRemove}>
                    <select
                        className={styles.submitInput}
                        value={value}
                        onChange={(e) => handleReferencedChange(index, e.target.value)}
                    >
                      <option value="">-- 참조자 선택 --</option>
                      {allUsers.map(user => (
                          <option key={user.employeeId} value={user.employeeId}>
                            {user.name} ({user.employeeId})
                          </option>
                      ))}
                    </select>
                    {referencedIds.length > 1 && (
                        <button type="button" onClick={() => removeReferencedField(index)}
                                className={styles.removeButton}>🗑️</button>
                    )}
                    {referencedErrors[index] &&
                        <p className={styles.errorText}>{referencedErrors[index]}</p>}
                  </div>
              ))}
              <button type="button" onClick={addReferencedField} className={styles.addButton}>+ 추가
              </button>
            </div>

            <div className={styles.submitFormGroup}>
              <label className={styles.submitLabel}>첨부파일</label>
              <input
                  type="file"
                  className={styles.submitInput}
                  multiple
                  onChange={handleFileChange}
              />
            </div>

            <div className={styles.submitButtonGroup}>
              <button type="submit" className={styles.submitButton}>문서 상신</button>
            </div>
          </form>
        </div>
      </div>
  );
};

export default SubmitPage;
