import React, {FormEvent, useState} from 'react';
import styles from '@/styles/admin/RegisterEmployee.module.css';
import {useForm} from "react-hook-form";
import {RegisterEmployeeDTO} from "@/type/registeremployee";
import {RegisterEmployeeAction} from "@/api/admin/registeremployeeaction";

export default function RegisterEmployee() {

  const {
    register,
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
    alert("회원가입이 완료되었습니다!");


    console.log(response); //  응답 메시지 저장
  };

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

              <div className={styles.field}>
                <label className={styles.label}>부서</label>
                <input
                    {...register("department", {required: "부서명을 입력하세요"})}
                    placeholder="회사명 입력"
                    className={styles.input}
                />
                {errors.department &&
                    <p className={styles.error}>{errors.department.message}</p>}
              </div>

              <div className={styles.field}>
                <label className={styles.label}>직급</label>
                <input
                    {...register("positionSalaryId", {required: "직급명을 입력하세요"})}
                    placeholder="회사명 입력"
                    className={styles.input}
                />
                {errors.positionSalaryId &&
                    <p className={styles.error}>{errors.positionSalaryId.message}</p>}
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

              <button type="submit" className={styles.button}>
                등록하기
              </button>
            </form>
        )}
      </div>
  );
};