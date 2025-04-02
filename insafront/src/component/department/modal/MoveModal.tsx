import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { usePositionActions } from "@/services/salaryAction";
import { usePositionSalaryStepActions } from "@/services/positionSalaryStepAction";
import * as Styled from './styled'; // Import new styled components

interface MoveUserDepartmentModalProps {
    isOpen: boolean;
    closeModal: () => void;
    userId: string | null;
    departmentId: string | null;
}

const MoveModal: React.FC<MoveUserDepartmentModalProps> = ({ isOpen, closeModal, userId, departmentId }) => {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [departments, setDepartments] = useState([]);
    const [selectedDepartment, setSelectedDepartment] = useState('');

    useEffect(() => {
        // 부서 목록을 가져오는 API 호출 예시
        fetch("/api/departments")
            .then(response => response.json())
            .then(data => setDepartments(data))
            .catch(console.error);
    }, []);

    const onSubmit = async (data: any) => {
        // 부서 이동 요청 API 호출 예시
        if (userId && selectedDepartment) {
            console.log(`Move user ${userId} to department ${selectedDepartment}`);
            alert("부서 이동이 완료되었습니다!");
            closeModal();
        }
    };

    if (!isOpen) return null;

    return (
        <Styled.ModalBackground>
            <Styled.ModalContainer>
                <Styled.ModalHeader>사용자 부서 이동</Styled.ModalHeader>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div>
                        <label>이동할 부서</label>
                        <select
                            {...register("departmentId", { required: "부서를 선택해주세요" })}
                            value={selectedDepartment}
                            onChange={(e) => setSelectedDepartment(e.target.value)}
                        >
                            <option value="">부서 선택</option>
                            {departments.map((department: any) => (
                                <option key={department.id} value={department.id}>
                                    {department.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <Styled.CloseButton type="submit">이동</Styled.CloseButton>
                        <Styled.CloseButton type="button" onClick={closeModal}>닫기</Styled.CloseButton>
                    </div>
                </form>
            </Styled.ModalContainer>
        </Styled.ModalBackground>
    );
};

export default MoveModal;
