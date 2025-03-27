// src/component/department/SelectDepartment.tsx
import React from "react";
import {DepartmentListForCreate} from "@/type/DepartmentListForCreate";

interface Props {
  departments: DepartmentListForCreate[];
  selected: string;
  onChange: (value: string) => void;
}

const renderOptions = (
    departments: DepartmentListForCreate[],
    level: number = 0
): React.ReactNode[] => {
  return departments.flatMap((dept) => {
    const indent = " ".repeat(level);
    const currentOption = (
        <option key={dept.departmentId} value={dept.departmentId}>
          {indent}📁 {dept.departmentName}
        </option>
    );
    const childOptions = dept.subDepartments
        ? renderOptions(dept.subDepartments, level + 1)
        : [];
    return [currentOption, ...childOptions];
  });
};

export default function SelectDepartment({departments, selected, onChange}: Props) {
  return (
      <div>
        <label className="block text-sm font-medium">상위 부서 (선택)</label>
        <select
            value={selected}
            onChange={(e) => onChange(e.target.value)}
            className="w-full border rounded p-2"
        >
          {renderOptions(departments)}
        </select>
      </div>
  );
}
