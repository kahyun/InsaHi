'use client';

import {useState, useEffect} from 'react';
import {useQuery, useMutation, useQueryClient} from '@tanstack/react-query';
import {submitLeave, getRemainingLeave, getLeaveUsageByEmployeeId} from '@/services/leaveService';
import {toast} from 'react-toastify';
import {AnnualLeaveDTO, AnnualLeaveRequestDTO} from '@/type/leave';

const useLocalStorage = (key: string, defaultValue: string = '') => {
  const [storedValue, setStoredValue] = useState<string>('');

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const value = localStorage.getItem(key) || defaultValue;
      setStoredValue(value);
    }
  }, [key, defaultValue]);

  return storedValue;
};

export default function LeaveSubmitPage() {
  // ✅ 기본 상태
  const employeeId = useLocalStorage('employeeId', '');
  const companyCode = useLocalStorage('companyCode', '');
  const queryClient = useQueryClient();

  const [statusFilter, setStatusFilter] = useState('PENDING');
  const [form, setForm] = useState({
    startDate: '',
    stopDate: '',
    reason: ''
  });

  // ✅ React Query - 남은 연차 가져오기
  const {
    data: remainingLeave,
    isLoading: remainingLoading,
    isError: remainingError,
  } = useQuery<AnnualLeaveDTO>({
    queryKey: ['remainingLeave', employeeId],
    queryFn: () => getRemainingLeave(employeeId),
    enabled: Boolean(employeeId), // 👉 값이 준비됐을 때만 실행
  });

  // ✅ React Query - 내 신청 내역 가져오기
  const {
    data: usageList = [],
    isLoading: usageLoading,
    isError: usageError,
  } = useQuery<AnnualLeaveRequestDTO[]>({
    queryKey: ['usageList', employeeId, statusFilter],
    queryFn: () => getLeaveUsageByEmployeeId(employeeId, statusFilter),
    enabled: Boolean(employeeId), // 👉 값이 준비됐을 때만 실행
  });

  // ✅ React Query - 휴가 신청
  const leaveMutation = useMutation({
    mutationFn: (data: AnnualLeaveRequestDTO) => submitLeave(data),
    onSuccess: () => {
      toast.success('휴가 신청 완료!');
      queryClient.invalidateQueries({queryKey: ['usageList', employeeId]});
      queryClient.invalidateQueries({queryKey: ['remainingLeave', employeeId]});
      setForm({startDate: '', stopDate: '', reason: ''});
    },
    onError: () => {
      toast.error('휴가 신청 실패!');
    },
  });

  // ✅ 휴가 신청 핸들러
  const handleSubmit = () => {
    if (!employeeId || !companyCode) {
      toast.warning('로그인 정보가 없습니다.');
      return;
    }

    if (!form.startDate || !form.stopDate || !form.reason) {
      toast.warning('모든 항목을 입력하세요.');
      return;
    }

    const requestData: AnnualLeaveRequestDTO = {
      annualLeaveUsageId: 0,
      annualLeaveId: remainingLeave?.annualLeaveId || 0,
      employeeId,
      companyCode,
      startDate: new Date(form.startDate),
      stopDate: new Date(form.stopDate),
      reason: form.reason,
      leaveApprovalStatus: 'PENDING'
    };

    leaveMutation.mutate(requestData);
  };

  // ✅ 필터링된 내역
  const filteredUsageList = statusFilter === 'ALL'
      ? usageList
      : usageList.filter((usage) => usage.leaveApprovalStatus === statusFilter);

  return (
      <div className="p-6 max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">내 휴가 신청하기</h1>

        {/* ✅ 남은 연차 */}
        {remainingLoading ? (
            <p>남은 연차 로딩 중...</p>
        ) : remainingError ? (
            <p className="text-red-500">남은 연차 조회 실패</p>
        ) : (
            <div className="mb-6 bg-gray-100 p-4 rounded">
              <p>총 연차: <strong>{remainingLeave?.totalGrantedLeave ?? 0}</strong>일</p>
              <p>사용한 연차: <strong>{remainingLeave?.usedLeave ?? 0}</strong>일</p>
              <p>남은 연차: <strong>{remainingLeave?.remainingLeave ?? 0}</strong>일</p>
            </div>
        )}

        {/* ✅ 휴가 신청 폼 */}
        <div className="flex flex-col gap-3 mb-8">
          <input
              type="date"
              value={form.startDate}
              onChange={(e) => setForm({...form, startDate: e.target.value})}
              className="border p-2 rounded"
          />
          <input
              type="date"
              value={form.stopDate}
              onChange={(e) => setForm({...form, stopDate: e.target.value})}
              className="border p-2 rounded"
          />
          <textarea
              placeholder="사유를 입력하세요"
              value={form.reason}
              onChange={(e) => setForm({...form, reason: e.target.value})}
              className="border p-2 rounded"
          />
          <button
              onClick={handleSubmit}
              disabled={leaveMutation.isPending}
              className={`p-2 rounded ${
                  leaveMutation.isPending
                      ? 'bg-gray-400 cursor-not-allowed'
                      : 'bg-blue-500 text-white hover:bg-blue-600'
              }`}
          >
            {leaveMutation.isPending ? '신청 중...' : '휴가 신청'}
          </button>
        </div>

        {/* ✅ 상태 필터 */}
        <div className="flex gap-2 mb-6">
          {['ALL', 'PENDING', 'APPROVED', 'REJECTED'].map((status) => (
              <button
                  key={status}
                  onClick={() => setStatusFilter(status)}
                  className={`p-2 rounded ${
                      statusFilter === status
                          ? 'bg-blue-500 text-white'
                          : 'bg-gray-200 hover:bg-gray-300'
                  }`}
              >
                {status}
              </button>
          ))}
        </div>

        {/* ✅ 신청 내역 */}
        {usageLoading ? (
            <p>신청 내역 로딩 중...</p>
        ) : usageError ? (
            <p className="text-red-500">신청 내역 조회 실패</p>
        ) : (
            <ul className="space-y-2">
              {filteredUsageList.length === 0 ? (
                  <li className="text-gray-500">휴가 신청 내역이 없습니다.</li>
              ) : (
                  filteredUsageList.map((usage) => (
                      <li key={usage.annualLeaveUsageId} className="border p-3 rounded shadow-sm">
                        <p>날짜: {new Date(usage.startDate).toLocaleDateString()} ~ {new Date(usage.stopDate).toLocaleDateString()}</p>
                        <p>사유: {usage.reason}</p>
                        <p>
                          상태:{' '}
                          <span
                              className={`font-bold ${
                                  usage.leaveApprovalStatus === 'APPROVED'
                                      ? 'text-green-500'
                                      : usage.leaveApprovalStatus === 'REJECTED'
                                          ? 'text-red-500'
                                          : 'text-yellow-500'
                              }`}
                          >
                    {usage.leaveApprovalStatus}
                  </span>
                        </p>
                      </li>
                  ))
              )}
            </ul>
        )}
      </div>
  );
}
