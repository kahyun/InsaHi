import React, { useState } from "react";

interface AddUserModalProps {
    isOpen: boolean;
    onClose: () => void;
}

const AddUserModal: React.FC<AddUserModalProps> = ({ isOpen, onClose }) => {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        department: "",
        position: "",
        birthDate: "",
        phone: "",
        address: "",
        gender: "",
        birthday: "",
        role: "",
        joinDate: ""
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // 여기에 사용자 추가 API 호출을 추가
        console.log("Form data submitted:", formData);
        // 폼 제출 후 모달 닫기
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div>
            <div className="modal-content">
                <h2>사용자 추가</h2>
                <form onSubmit={handleSubmit}>
                    <label>
                        이름:
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        이메일:
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        부서:
                        <input
                            type="text"
                            name="department"
                            value={formData.department}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        직급:
                        <input
                            type="text"
                            name="position"
                            value={formData.position}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        생년월일:
                        <input
                            type="date"
                            name="birthDate"
                            value={formData.birthDate}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        휴대폰번호:
                        <input
                            type="tel"
                            name="phone"
                            value={formData.phone}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        주소:
                        <input
                            type="text"
                            name="address"
                            value={formData.address}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        성별:
                        <input
                            type="text"
                            name="gender"
                            value={formData.gender}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        생일:
                        <input
                            type="date"
                            name="birthday"
                            value={formData.birthday}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        역할:
                        <input
                            type="text"
                            name="role"
                            value={formData.role}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        입사일:
                        <input
                            type="date"
                            name="joinDate"
                            value={formData.joinDate}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <button type="submit">추가</button>
                </form>
                <button onClick={onClose}>닫기</button>
            </div>
        </div>
    );
};

export default AddUserModal;