import React from "react";
import * as styles from './styled';  // styles 파일 불러오기

interface ModalProps {
    isOpen: boolean;
    closeModal: () => void;
    leave: any;
}

const CalendarModal: React.FC<ModalProps> = ({ isOpen, closeModal, leave }) => {
    if (!isOpen || !leave) return null;

    return (
        <styles.ModalBackground>
            <styles.ModalContainer>
                <styles.ModalHeader>휴가 상세</styles.ModalHeader>
                <p>
                    <strong>시작일:</strong>{" "}
                    {new Date(leave.startDate).toLocaleDateString("ko-KR")}
                </p>
                <p>
                    <strong>종료일:</strong>{" "}
                    {new Date(leave.stopDate).toLocaleDateString("ko-KR")}
                </p>
                <p>
                    <strong>사유:</strong> {leave.reason}
                </p>
                    <styles.CloseButton onClick={closeModal}>
                        닫기
                    </styles.CloseButton>
            </styles.ModalContainer>
        </styles.ModalBackground>
    );
};

export default CalendarModal;
