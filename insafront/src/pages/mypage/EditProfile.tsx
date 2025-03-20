
import React, {useEffect, useState} from "react";
import styles from "@/styles/mypage/EditProfile.module.css";
import EmployeeInfoAction from "@/api/mypage/employeeinfoaction";


function EditProfile () {
    const [employee, setEmployee] = useState<employeeInfoDTO | null>(null);
    const [employeeIdToken, setEmployeeIdToken] = useState<string | null>(null);


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Updated Employee Data:");
        alert("정보가 업데이트되었습니다.");
    };

    useEffect(() => {
        // 클라이언트에서만 실행되도록 보장
        if (typeof window !== "undefined") {
            const storedEmployeeId = localStorage.getItem("employeeId") || "defaultId";
            setEmployeeIdToken(storedEmployeeId);
        }
    }, []);

    useEffect(() => {
        if (employeeIdToken) {
            const fetchData = async () => {
                const data = await EmployeeInfoAction(employeeIdToken);
                if (data) {
                    setEmployee(data);
                } else {
                    console.warn("No data found.");
                }
            };
            fetchData();
        }
    }, [employeeIdToken]);

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>개인정보 수정</h2>
            <form onSubmit={handleSubmit} className={styles.form}>

                {/* 이름 */}
                <div className={styles.formGroup}>
                    <label>이름</label>
                    <input type="text" name="name" value={employee?.name}  disabled/>
                </div>

                {/* 아이디 */}
                <div className={styles.formGroup}>
                    <label>아이디</label>
                    <input type="text" name="employeeId" value={employee?.employeeId} disabled />
                </div>

                {/*/!* 비밀번호 *!/*/}
                {/*<div className={styles.formGroup}>*/}
                {/*    <label>비밀번호</label>*/}
                {/*    <input type="password" name="password" value={employee?.password}  />*/}
                {/*</div>*/}


                {/* 이메일 */}
                <div className={styles.formGroup}>
                    <label>이메일</label>
                    <input type="text" name="email" value={employee?.email}  />
                </div>

                {/* 전화번호 */}
                <div className={styles.formGroup}>
                    <label>전화번호</label>
                    <input type="text" name="phoneNumber" value={employee?.phoneNumber}  />
                </div>

                {/* 부서 ID */}
                <div className={styles.formGroup}>
                    <label>부서 ID</label>
                    <input type="text" name="departmentId" value={employee?.departmentId} disabled/>
                </div>

                {/* 팀 ID */}
                <div className={styles.formGroup}>
                    <label>팀 ID</label>
                    <input type="text" name="teamId" value={employee?.teamId} disabled />
                </div>

                <button type="submit" className={styles.submitButton}>수정 완료</button>
            </form>
        </div>
    );
};

export default EditProfile;
