'use client';

import {useRouter, useParams} from 'next/navigation';
import {useState, useEffect} from 'react';
import {useQuery, useMutation, useQueryClient} from '@tanstack/react-query';
import {getLeaveDetailById, approveLeave, rejectLeave} from '@/services/leaveService';
import {AnnualLeaveRequestDTO} from '@/type/leave';
import {toast} from 'react-toastify';

// ✅ 커스텀 훅
//export
const useLocalStorage = (key: string, defaultValue = '') => {
  const [value, setValue] = useState<string | null>(null);

  useEffect(() => {
    const storedValue = localStorage.getItem(key);
    setValue(storedValue || defaultValue);
  }, [key]);

  return value;
};

export default function LeaveDetailPage() {
  const {id} = useParams();
  const router = useRouter();
  const queryClient = useQueryClient();

  const {data, isLoading, isError} = useQuery({
    queryKey: ['leaveDetail', id],
    queryFn: () => getLeaveDetailById(Number(id)),
    enabled: !!id,
  });

  const approveMutation = useMutation({
    mutationFn: approveLeave,
    onSuccess: () => {
      toast.success('승인 완료!');
      queryClient.invalidateQueries({queryKey: ['leaveDetail', id]});
      router.push('/admin/leave');
    },
  });

  const rejectMutation = useMutation({
    mutationFn: rejectLeave,
    onSuccess: () => {
      toast.success('반려 완료!');
      queryClient.invalidateQueries({queryKey: ['leaveDetail', id]});
      router.push('/admin/leave');
    },
  });

  const auth = useLocalStorage('auth');


  // ✅ 권한 정보가 로딩 중일 때는 아무것도 렌더하지 않기
  if (auth === null) {
    return <p className="text-center mt-10">권한 확인 중...</p>;
  }

  // ✅ ROLE_ADMIN이 아닐 때는 접근 제한
  if (!auth.includes('ROLE_ADMIN')) {
    return <p className="text-center text-red-500 mt-10">접근 권한이 없습니다.</p>;
  }


  if (isLoading) return <p>상세 정보 로딩 중...</p>;
  if (isError || !data) return <p className="text-red-500">상세 정보를 불러오지 못했습니다.</p>;

  const leave = data as AnnualLeaveRequestDTO;

  return (
      <div className="p-6 max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">휴가 신청 상세 정보</h1>

        <div className="border p-4 rounded mb-6 bg-gray-100 space-y-2">
          <p><strong>사번:</strong> {leave.employeeId}</p>
          <p><strong>회사 코드:</strong> {leave.companyCode}</p>
          <p>
            <strong>기간:</strong> {new Date(leave.startDate).toLocaleDateString()} ~ {new Date(leave.stopDate).toLocaleDateString()}
          </p>
          <p><strong>사유:</strong> {leave.reason}</p>
          <p><strong>현재 상태:</strong> {leave.leaveApprovalStatus}</p>
        </div>

        <div className="flex gap-2">
          <button
              onClick={() => approveMutation.mutate(leave)}
              disabled={leave.leaveApprovalStatus === 'APPROVED'}
              className="p-2 bg-green-500 text-white rounded"
          >
            승인
          </button>
          <button
              onClick={() => rejectMutation.mutate(leave)}
              disabled={leave.leaveApprovalStatus === 'REJECTED'}
              className="p-2 bg-red-500 text-white rounded"
          >
            반려
          </button>
          <button onClick={() => router.back()} className="p-2 bg-gray-300 rounded">
            뒤로 가기
          </button>
        </div>
      </div>
  );
}
