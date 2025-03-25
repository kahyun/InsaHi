'use client';

import {useEffect, useState} from 'react';
import {useQuery} from '@tanstack/react-query';
import {getLeaveUsageByCompanyWithPagination} from '@/services/leaveService';
import {AnnualLeaveRequestDTO} from '@/type/leave';
import Link from 'next/link';
import {toast} from 'react-toastify';

// ✅ 커스텀 훅
const useLocalStorage = (key: string, defaultValue = '') => {
  const [value, setValue] = useState<string>(defaultValue);

  useEffect(() => {
    const storedValue = localStorage.getItem(key);
    setValue(storedValue ?? defaultValue);
  }, [key]);

  return value;
};


export default function LeaveRequestPage() {
  const companyCode = useLocalStorage('companyCode');
  const auth = useLocalStorage('auth');

  const [statusFilter, setStatusFilter] = useState('ALL');
  const [page, setPage] = useState(0);
  const size = 10;

  const {data, isLoading, isError} = useQuery({
    queryKey: ['usageList', companyCode, statusFilter, page],
    queryFn: () => getLeaveUsageByCompanyWithPagination(companyCode, statusFilter, page, size),
    enabled: !!companyCode,
  });

  const usageList = data?.content ?? [];
  const totalPages = data?.totalPages ?? 0;


  // 권한 로딩이 끝날 때까지 기다리기
  if (auth === null || companyCode === null) {
    return <p className="text-center mt-10">권한 확인 중...</p>;
  }

  // ✅ 권한이 없는 사용자 차단
  if (!auth.includes('ROLE_ADMIN')) {
    return <p className="text-center text-red-500 mt-10">접근 권한이 없습니다.</p>;
  }


  //const usageList = data?.content ?? [];
  //const totalPages = data?.totalPages ?? 0;

  return (
      <div className="p-6 max-w-4xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">휴가 신청 내역 관리</h1>

        {/* ✅ 상태 필터 */}
        <div className="flex gap-2 mb-4">
          {['ALL', 'PENDING', 'APPROVED', 'REJECTED'].map((status) => (
              <button
                  key={status}
                  onClick={() => {
                    setStatusFilter(status);
                    setPage(0);
                  }}
                  className={`p-2 rounded ${
                      statusFilter === status ? 'bg-blue-500 text-white' : 'bg-gray-200'
                  }`}
              >
                {status}
              </button>
          ))}
        </div>

        {/* ✅ 로딩 및 에러 */}
        {isLoading && <p>데이터 로딩 중...</p>}
        {isError && <p className="text-red-500">데이터 조회 실패</p>}

        {/* ✅ 리스트 */}
        <ul className="space-y-2">
          {usageList.length === 0 ? (
              <li className="text-gray-500">휴가 신청 내역이 없습니다.</li>
          ) : (
              usageList.map((usage: AnnualLeaveRequestDTO) => (
                  <li
                      key={usage.annualLeaveUsageId}
                      className="border p-3 rounded shadow-sm flex justify-between"
                  >
                    <div>
                      <p>
                        <strong>사번:</strong> {usage.employeeId}
                      </p>
                      <p>
                        <strong>기간:</strong>{' '}
                        {new Date(usage.startDate).toLocaleDateString()} ~{' '}
                        {new Date(usage.stopDate).toLocaleDateString()}
                      </p>
                      <p>
                        <strong>상태:</strong> {usage.leaveApprovalStatus}
                      </p>
                    </div>
                    <Link
                        href={`/attendance/leave/${usage.annualLeaveUsageId}`}
                        className="p-2 bg-blue-500 text-white rounded"
                    >
                      상세 보기
                    </Link>
                  </li>
              ))
          )}
        </ul>

        {/* ✅ 페이지네이션 */}
        <div className="flex justify-center gap-2 mt-4">
          <button
              onClick={() => setPage((prev) => prev - 1)}
              disabled={page <= 0}
              className="p-2 bg-gray-300 rounded"
          >
            이전
          </button>
          <span>
          {page + 1} / {totalPages}
        </span>
          <button
              onClick={() => setPage((prev) => prev + 1)}
              disabled={page + 1 >= totalPages}
              className="p-2 bg-gray-300 rounded"
          >
            다음
          </button>
        </div>
      </div>
  );
}
