import {useRouter} from 'next/router';
import {useState, useEffect} from 'react';

interface File {
  id: string;
  name: string;
  text: string;
}

const FilePage = () => {
  const router = useRouter();
  const {fileId} = router.query;
  const [file, setFile] = useState<File | null>(null);
  const [files, setFiles] = useState<File[]>([]);

  // 파일 리스트 불러오기
  useEffect(() => {
    if (!fileId) return;

    const fetchFileDetails = async () => {
      const response = await fetch(`http://127.0.0.1:1005/approval/file/${fileId}`);
      const data = await response.json();
      setFile(data);
    };

    // 모든 파일 목록 가져오기
    const fetchFileList = async () => {
      const response = await fetch(`http://127.0.0.1:1005/approval/file/list`);
      const data = await response.json();
      setFiles(data); // 파일 목록 저장
    };

    fetchFileDetails();
    fetchFileList();
  }, [fileId]);

  // 파일 다운로드 처리
  const handleDownload = async (fileId: string) => {
    const response = await fetch(`http://127.0.0.1:1005/approval/file/download/${fileId}`);
    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `file-${fileId}`;  // 파일명 설정 (fileId로 다운로드)
    link.click();
    window.URL.revokeObjectURL(url); // URL 해제
  };

  return (
      <div>
        <h1>결재 문서 상세</h1>
        {file ? (
            <>
              <p>문서 제목: {file.name}</p>
              <p>문서 내용: {file.text}</p>
            </>
        ) : (
            <div>Loading...</div>
        )}

        {/* 첨부 파일 목록 */}
        <h2>첨부된 파일 목록</h2>
        <ul>
          {files.map((f) => (
              <li key={f.id}>
                <span>{f.name}</span>
                <button onClick={() => handleDownload(f.id)}>다운로드</button>
              </li>
          ))}
        </ul>
      </div>
  );
};

export default FilePage;
