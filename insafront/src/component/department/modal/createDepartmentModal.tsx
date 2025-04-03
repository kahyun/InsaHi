import React, { FormEvent, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import { DepartmentListForCreate } from "@/type/DepartmentListForCreate";
import { getParentDepartments } from "@/services/createDepartmentAction";
import SelectDepartment from "@/component/department/SelectDepartment";
import * as Styled from "./styled";

interface ModalProps {
    isOpen: boolean;
    closeModal: () => void;
}

export default function CreateSubDepartmentModal({ isOpen, closeModal }: ModalProps) {
    const { register, handleSubmit, setValue, formState: { errors } } = useForm<{ departmentName: string; parentDepartmentId?: string }>();
    const [departments, setDepartments] = useState<DepartmentListForCreate[]>([]);
    const [selectedParentDepartment, setSelectedParentDepartment] = useState('');

    useEffect(() => {
        const companyCode = localStorage.getItem("companyCode");
        if (companyCode) {
            getParentDepartments(companyCode)
                .then(setDepartments)
                .catch(console.error);
        }
    }, []);

    if (!isOpen) return null;

    const handleCreateDepartment = async (data: { departmentName: string; parentDepartmentId?: string }) => {
        const companyCode = localStorage.getItem("companyCode");

        if (!companyCode) {
            alert("회사 코드가 없습니다. 다시 로그인해주세요.");
            return;
        }

        const requestBody = {
            companyCode: companyCode,
            departmentName: data.departmentName,
            parentDepartmentId: selectedParentDepartment || null, // 부모 부서 ID가 없으면 null 처리
        };

        try {
            await axios.post(`/api/${companyCode}/department/create`, requestBody, {
                headers: {
                    "Content-Type": "application/json",
                },
            });
            alert("하위 부서가 추가되었습니다!");
            closeModal();
        } catch (error) {
            console.error("부서 생성 실패:", error);
            alert("부서 생성에 실패했습니다.");
        }
    };

    return (
        <Styled.ModalBackground>
            <Styled.ModalContainer>
                <Styled.ModalHeader>하위 부서 추가</Styled.ModalHeader>

                <form onSubmit={handleSubmit(handleCreateDepartment)}>
                    <div>
                        <label>부서명</label>
                        <Styled.Input
                            {...register("departmentName", { required: "부서명을 입력하세요" })}
                            placeholder="새로운 부서명 입력"
                        />
                        {errors.departmentName && <Styled.ErrorMessage>{errors.departmentName.message}</Styled.ErrorMessage>}
                    </div>

                    <div>
                        <label>상위 부서 선택</label>
                        <SelectDepartment
                            departments={departments}
                            selected={selectedParentDepartment}
                            onChange={(value) => {
                                setSelectedParentDepartment(value);
                                setValue("parentDepartmentId", value);
                            }}
                        />
                    </div>

                    <Styled.ButtonWrapper>
                        <Styled.CloseButton type="submit">부서 추가</Styled.CloseButton>
                        <Styled.CancelButton type="button" onClick={closeModal}>닫기</Styled.CancelButton>
                    </Styled.ButtonWrapper>
                </form>
            </Styled.ModalContainer>
        </Styled.ModalBackground>
    );
}
