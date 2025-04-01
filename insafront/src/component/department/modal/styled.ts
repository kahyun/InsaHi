import styled from '@emotion/styled'

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

// 닫기 버튼 스타일
export const CloseButton = styled.button`
    background-color: #3b82f6;
    color: white;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    border: none;
    font-size: 1rem;
    margin-top: 16px;
    &:hover {
        background-color: #2563eb;
    }
`;
