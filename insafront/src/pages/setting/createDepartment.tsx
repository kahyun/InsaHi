// pages/department/create.tsx

import {useState, useEffect} from "react";
import {getParentDepartments, submitDepartment} from "@/services/createDepartmentAction";
import {DepartmentListForCreate} from "@/type/DepartmentListForCreate";

const useLocalStorage = (key: string, defaultValue = '') => {
  const [value, setValue] = useState<string>(defaultValue);

  useEffect(() => {
    const storedValue = localStorage.getItem(key);
    setValue(storedValue ?? defaultValue);
  }, [key]);

  return value;
};


export default function CreateDepartmentPage() {
  const [departmentName, setDepartmentName] = useState("");
  const [parentDepartments, setParentDepartments] = useState<DepartmentListForCreate[]>([]);
  const [selectedParentId, setSelectedParentId] = useState("");
  const [loading, setLoading] = useState(false);

  const companyCode = useLocalStorage("companyCode", "");
  const accessToken = useLocalStorage("accessToken", "")
  console.log(accessToken);
  // ✅ 상위 부서 목록 가져오기
  useEffect(() => {
    if (companyCode) {
      getParentDepartments(companyCode)
      .then(setParentDepartments)
      .catch((err) => {
        console.error("부서 목록 조회 실패:", err);
        alert("부서 목록을 불러오는 데 실패했습니다.");
      });
    }
  }, [companyCode]);

  // ✅ 부서 생성 요청 처리
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!companyCode) {
      alert("회사 코드가 없습니다.");
      return;
    }

    const payload = {
      departmentName,
      parentDepartmentId: selectedParentId,
      departmentLevel: 1,
    };

    try {
      setLoading(true);
      await submitDepartment(companyCode, payload);
      alert("부서가 성공적으로 생성되었습니다!");
      setDepartmentName("");
      setSelectedParentId("");
    } catch (error) {
      console.error("부서 생성 실패:", error);
      alert("부서 생성 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="p-6 max-w-md mx-auto bg-white rounded shadow">
        <h2 className="text-xl font-bold mb-4">부서 등록</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium">부서명</label>
            <input
                type="text"
                value={departmentName}
                onChange={(e) => setDepartmentName(e.target.value)}
                required
                className="w-full border rounded p-2"
            />
          </div>

          <div>
            <label className="block text-sm font-medium">상위 부서 (선택)</label>
            <select
                value={selectedParentId}
                onChange={(e) => setSelectedParentId(e.target.value)}
                className="w-full border rounded p-2"
            >
              <option value="">없음 (최상위)</option>
              {parentDepartments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
              ))}
            </select>
          </div>

          <button
              type="submit"
              disabled={loading}
              className={`w-full px-4 py-2 rounded text-white ${
                  loading ? "bg-gray-400" : "bg-blue-600 hover:bg-blue-700"
              }`}
          >
            {loading ? "등록 중..." : "부서 등록"}
          </button>
        </form>
      </div>
  );
}
