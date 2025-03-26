import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/router'; // next router 사용
import { Department } from '@/type/Department';
import { getDepartmentList } from '@/lib/getDepartmentList'; // 부서 데이터 가져오기
import DepartmentView from '@/component/department/DepartmentView'; // 부서 렌더링 컴포넌트
import DepartmentSide from '@/component/sidebar/DepartmentSide'; // 사이드바 컴포넌트

const DepartmentListPage = () => {
    const router = useRouter();
    const { companyCode } = router.query; // 쿼리에서 companyCode 가져오기

    const [departmentList, setDepartmentList] = useState<Department[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (companyCode) {
            const fetchDepartments = async () => {
                try {
                    const data = await getDepartmentList(companyCode as string);
                    setDepartmentList(data); // 데이터를 받아오면 상태 업데이트
                } catch (error) {
                    setError('부서 데이터를 가져오는 데 실패했습니다.');
                }
            };
            fetchDepartments();
        }
    }, [companyCode]);

    if (error) {
        return <div>{error}</div>;
    }

    if (departmentList.length === 0) {
        return <div>부서 데이터를 로딩 중...</div>;
    }

    return (
        <div className="flex min-h-screen">
            {/* 사이드바 */}
            <aside className="w-1/4 p-4">
                <DepartmentSide departments={departmentList} companyCode={companyCode as string} />
            </aside>

            {/* 메인 콘텐츠 */}
            <main className="w-3/4 p-4">
                <h1 className="text-2xl font-bold mb-6">조직도</h1>
                {departmentList.map((department) => (
                    <DepartmentView key={department.departmentId} department={department} />
                ))}
            </main>
        </div>
    );
};

export default DepartmentListPage;
