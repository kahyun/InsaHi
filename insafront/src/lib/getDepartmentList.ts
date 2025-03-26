// lib/getDepartmentList.ts
export const getDepartmentList = async (companyCode: string): Promise<Department[]> => {
    const response = await fetch(`http://localhost:1010/api/${companyCode}/department/list`);

    if (!response.ok) {
        throw new Error('부서 데이터를 가져오는 데 실패했습니다.');
    }

    const data = await response.json();
    return data; // 부서 데이터 반환
};
