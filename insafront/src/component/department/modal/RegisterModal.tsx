import React, {FormEvent, useEffect, useState} from 'react';
import {useForm} from "react-hook-form";
import {RegisterEmployeeDTO} from "@/type/registeremployee";
import {RegisterEmployeeAction} from "@/api/admin/registeremployeeaction";
import {DepartmentListForCreate} from "@/type/DepartmentListForCreate";
import {getParentDepartments} from "@/services/createDepartmentAction";
import SelectDepartment from "@/component/department/SelectDepartment";
import {usePositionActions} from "@/services/salaryAction";
import {usePositionSalaryStepActions} from "@/services/positionSalaryStepAction";
import * as Styled from './styled'; // Import new styled components

interface ModalProps {
    isOpen: boolean,
    closeModal: () => void,
    onClose?: () => void
}

export default function RegisterEmployeeModal({isOpen, closeModal, onClose}: ModalProps) {
    const {register, setValue, formState: {errors}} = useForm<RegisterEmployeeDTO>();
    const [departments, setDepartments] = useState<DepartmentListForCreate[]>([]);
    const [selectedDepartment, setSelectedDepartment] = useState('');
    const [selectedPositionSalaryId, setSelectedPositionSalaryId] = useState('');

    useEffect(() => {
        const companyCode = localStorage.getItem("companyCode");
        if (companyCode) {
            getParentDepartments(companyCode)
                .then(setDepartments)
                .catch(console.error);
        }
    }, []);

    const [companyCodeFromToken, setCompanyCodeFromToken] = useState<string>('');
    const {positions} = usePositionActions(companyCodeFromToken);
    const {positionSalarySteps} = usePositionSalaryStepActions(companyCodeFromToken);

    useEffect(() => {
        const storedCompanyCode = localStorage.getItem('companyCode');
        if (storedCompanyCode) {
            setCompanyCodeFromToken(storedCompanyCode);
        } else {
            alert('회사 코드가 없습니다. 다시 로그인 해주세요.');
        }
    }, []);

    if (!isOpen) return null;

    const onRegisterHandleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        formData.append("departmentId", selectedDepartment);
        formData.append("positionSalaryId", String(selectedPositionSalaryId));

        const response = await RegisterEmployeeAction(formData);
        alert("직원등록이 완료되었습니다!");
    };

    return (
        <Styled.ModalBackground>
            <Styled.ModalContainer>
                <Styled.ModalHeader>직원 등록</Styled.ModalHeader>

                <form onSubmit={onRegisterHandleSubmit}>
                    <div>
                        <label>이름</label>
                        <input
                            {...register("name", {required: "이름을 입력하세요"})}
                            placeholder="이름 입력"
                        />
                        {errors.name && <p>{errors.name.message}</p>}
                    </div>

                    <div>
                        <label>비밀번호</label>
                        <input
                            {...register("password", {required: "비밀번호를 입력하세요"})}
                            placeholder="비밀번호 입력"
                        />
                        {errors.password && <p>{errors.password.message}</p>}
                    </div>

                    <div>
                        <label>이메일</label>
                        <input
                            {...register("email", {required: "이메일을 입력하세요"})}
                            placeholder="이메일 입력"
                        />
                        {errors.email && <p>{errors.email.message}</p>}
                    </div>

                    <div>
                        <label>부서</label>
                        <SelectDepartment
                            departments={departments}
                            selected={selectedDepartment}
                            onChange={(value) => {
                                setSelectedDepartment(value);
                                setValue("departmentId", value);
                            }}
                        />
                        {errors.departmentId && <p>{errors.departmentId.message}</p>}
                    </div>

                    <div>
                        <label>직급</label>
                        <select
                            value={selectedPositionSalaryId}
                            onChange={(e) => {
                                setSelectedPositionSalaryId(e.target.value);
                                // setValue("positionSalaryId", e.target.value);
                            }}
                        >
                            <option value="">호봉 선택</option>
                            {positionSalarySteps.map((item) => (
                                <option key={item.positionSalaryId} value={item.positionSalaryId}>
                                    {positions.find(pos => pos.positionId === item.positionId)?.positionName || item.positionId} - {item.salaryStepId}호봉
                                </option>
                            ))}
                        </select>
                        {errors.positionSalaryId && <p>{errors.positionSalaryId.message}</p>}
                    </div>

                    <div>
                        <label>연락처</label>
                        <input
                            {...register("phoneNumber", {required: "연락처를 입력하세요"})}
                            placeholder="전화번호 입력"
                        />
                        {errors.phoneNumber && <p>{errors.phoneNumber.message}</p>}
                    </div>

                    <div>
                        <label>입사일</label>
                        <input
                            {...register("hireDate", {required: "입사일을 입력하세요"})}
                            placeholder="입사일 입력"
                        />
                        {errors.hireDate && <p>{errors.hireDate.message}</p>}
                    </div>

                    <Styled.CloseButton type="submit">등록하기</Styled.CloseButton>
                    <Styled.CloseButton type="button" onClick={closeModal}>닫기</Styled.CloseButton>
                </form>
            </Styled.ModalContainer>
        </Styled.ModalBackground>
    );
}
