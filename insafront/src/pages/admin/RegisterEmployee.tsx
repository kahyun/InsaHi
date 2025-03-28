import React, {FormEvent, useEffect, useState} from 'react';
import styles from '@/styles/admin/RegisterEmployee.module.css';
import {useForm} from "react-hook-form";
import {RegisterEmployeeDTO} from "@/type/registeremployee";
import {RegisterEmployeeAction} from "@/api/admin/registeremployeeaction";
import {DepartmentListForCreate} from "@/type/DepartmentListForCreate";
import {getParentDepartments} from "@/services/createDepartmentAction";
import SelectDepartment from "@/component/department/SelectDepartment";
import {usePositionActions} from "@/services/salaryAction";


export default function RegisterEmployee() {

  const {
    register,
    setValue,
    formState: {errors},
  } = useForm<RegisterEmployeeDTO>();
  const [submittedData, setSubmittedData] = useState<RegisterEmployeeDTO | null>(null);

  async function onRegisterHandleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    // FormData 객체 생성 (HTML 폼에서 가져옴)
    const formData = new FormData(event.currentTarget);
    console.log("최초 요청 데이터:", formData);

    // 서버 액션 호출
    const response = await RegisterEmployeeAction(formData);
    console.log("서버 응답:", response);
    alert("직원등록이 완료되었습니다!");


    console.log(response); //  응답 메시지 저장
  };


  const [departments, setDepartments] = useState<DepartmentListForCreate[]>([]);
  const [selectedDepartment, setSelectedDepartment] = useState('');

  useEffect(() => {
    const companyCode = localStorage.getItem("companyCode");
    if (companyCode) {
      getParentDepartments(companyCode) // ← 이게 flat 구조를 주고 있다고 가정
      .then(setDepartments)
      .catch(console.error);
      // getPositionList(companyCode).then(setPositions); // 너가 만드는 함수
    }
  }, []);

  const [companyCodeFromToken, setCompanyCodeFromToken] = useState<string>('');

  const {
    positions,
    newPosition,
    handlePositionChange,
    handleSubmitPosition
  } = usePositionActions(companyCodeFromToken);

  useEffect(() => {
    const storedCompanyCode = localStorage.getItem('companyCode');

    if (storedCompanyCode) {
      setCompanyCodeFromToken(storedCompanyCode);
    } else {
      alert('회사 코드가 없습니다. 다시 로그인 해주세요.');
    }
  }, []);

  return (
      <div className={styles.container}>
        <h2 className={styles.title}>직원 등록</h2>

        {submittedData ? (
            <p> 등록이 완료되었습니다 !!!</p>
        ) : (
            <form className={styles.form} onSubmit={onRegisterHandleSubmit}>
              <div className={styles.field}>
                <label className={styles.label}>이름</label>
                <input
                    {...register("name", {required: "이름을 입력하세요"})}
                    placeholder="이름 입력"
                    className={styles.input}
                />
                {errors.name &&
                    <p className={styles.error}>{errors.name.message}</p>}
              </div>

              <div className={styles.field}>
                <label className={styles.label}>비밀번호</label>
                <input
                    {...register("password", {required: "비밀번호를 입력하세요"})}
                    placeholder="비밀번호 입력"
                    className={styles.input}
                />
                {errors.password &&
                    <p className={styles.error}>{errors.password.message}</p>}
              </div>

              <div className={styles.field}>
                <label className={styles.label}>이메일</label>
                <input
                    {...register("email", {required: "이메일을 입력하세요"})}
                    placeholder="회사명 입력"
                    className={styles.input}
                />
                {errors.email &&
                    <p className={styles.error}>{errors.email.message}</p>}
              </div>
              {/*부서명 입력*/}
              <div className={styles.field}>
                <label className={styles.label}>부서</label>

                <SelectDepartment
                    departments={departments}
                    selected={selectedDepartment}
                    onChange={(value) => {
                      setSelectedDepartment(value);
                      setValue("department", value); // react-hook-form에 선택 값 저장
                    }}
                />
                {errors.department && (
                    <p className={styles.error}>{errors.department.message}</p>
                )}
              </div>
              {/*직급명 입력*/}
              <div className={styles.field}>
                <label className={styles.label}>직급</label>
                <select
                    {...register("positionId", {required: "직급을 선택하세요"})}
                    className={styles.input}
                >
                  <option value="">직급을 선택하세요</option>
                  {positions.map((position) => (
                      <option key={position.positionId} value={position.positionId}>
                        {position.positionName}
                      </option>
                  ))}
                  {/* 여기에 직급 데이터를 받아서 map으로 옵션을 추가할 예정 */}
                </select>
                {errors.positionId && (
                    <p className={styles.error}>{errors.positionId.message}</p>
                )}
              </div>

              <div className={styles.field}>
                <label className={styles.label}>연락처</label>
                <input
                    {...register("phoneNumber", {required: "연락처을 입력하세요"})}
                    placeholder="회사명 입력"
                    className={styles.input}
                />
                {errors.phoneNumber &&
                    <p className={styles.error}>{errors.phoneNumber.message}</p>}
              </div>

              <div className={styles.field}>
                <label className={styles.label}>입사일</label>
                <input
                    {...register("hireDate", {required: "입사일을 입력하세요"})}
                    placeholder="입사일 입력"
                    className={styles.input}
                />
                {errors.hireDate &&
                    <p className={styles.error}>{errors.hireDate.message}</p>}
              </div>

              <button type="submit" className={styles.button}>
                등록하기
              </button>
            </form>
        )}
      </div>
  );
};