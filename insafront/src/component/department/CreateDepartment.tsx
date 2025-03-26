// pages/department/create.tsx

import {useState, useEffect} from "react";
import {getParentDepartments, submitDepartment} from "@/services/createDepartmentAction";

const useLocalStorage = (key: string, defaultValue: string | null = null) => {
  const [storedValue, setStoredValue] = useState<string | null>(defaultValue);

  useEffect(() => {
    if (typeof window !== "undefined") {
      const value = localStorage.getItem(key);
      setStoredValue(value);
    }
  }, [key]);

  return storedValue;
};

export default function CreateDepartmentPage() {
  const [departmentName, setDepartmentName] = useState("");
  const [parentDepartments, setParentDepartments] = useState([]);
  const [selectedParentId, setSelectedParentId] = useState("");

  const companyCode = useLocalStorage("companyCode", "");

  useEffect(() => {
    if (companyCode) {
      getParentDepartments()
      .then(setParentDepartments)
      .catch((err) => console.error(err));
    }
  }, [companyCode]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      departmentName,
      parentDepartmentId: selectedParentId || null,
      departmentLevel: 1,
    };

    try {
      await submitDepartment(companyCode!, payload);
      alert("부서가 생성되었습니다!");
      setDepartmentName("");
      setSelectedParentId("");
    } catch (error) {
      alert("생성 중 오류가 발생했습니다.");
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
              {parentDepartments.map((dept: any) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
              ))}
            </select>
          </div>

          <button
              type="submit"
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            부서 등록
          </button>
        </form>
      </div>
  );
}
