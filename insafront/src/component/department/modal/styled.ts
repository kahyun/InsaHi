import styled from "@emotion/styled";

// Modal 배경
export const ModalBackground = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.5);
`;

// Modal 컨테이너
export const ModalContainer = styled.div`
    background-color: white;
    padding: 24px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-width: 400px;
    width: 100%;
`;

// Modal 헤더
export const ModalHeader = styled.h3`
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 16px;
`;

// 입력 필드 스타일
export const Input = styled.input`
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-top: 4px;
`;
// 입력 필드 스타일
export const SelectDepartment = styled.select`
    width: 100%;
`;

// 에러 메시지 스타일
export const ErrorMessage = styled.p`
    color: red;
    font-size: 0.875rem;
    margin-top: 4px;
`;

// 버튼 그룹 (하단 버튼 정렬)
export const ButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 8px;
    margin-top: 16px;
`;

// 기본 버튼 스타일
const Button = styled.button`
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    border: none;
    font-size: 1rem;
`;

// 닫기 버튼 (파란색)
export const CloseButton = styled(Button)`
    background-color: #3b82f6;
    color: white;
    &:hover {
        background-color: #2563eb;
    }
`;

// 취소 버튼 (회색)
export const CancelButton = styled(Button)`
    background-color: #ccc;
    &:hover {
        background-color: #aaa;
    }
`;
