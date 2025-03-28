import { Checkbox } from "@/component/ui/checkbox";  // Checkbox 컴포넌트를 사용하기 위해 임포트

type Contact = {
  id: number;
  name: string;
  department: string;
  position: string;
  email: string;
  phone: string;
  status?: string;
  selected?: boolean;
  highlighted?: boolean;
};

type ContactListProps = {
  contactsData: Contact[]; // 사용자 목록을 받아올 props
  onSelectContact: (contactId: number) => void; // 선택한 연락처 처리 함수
};

const ContactList = ({ contactsData, onSelectContact }: ContactListProps) => {
  console.log("ContactList에 전달된 데이터:", contactsData);  // 데이터 확인

  return (
    <div className="border-t pt-4">
      <h3>소속된 사용자 목록</h3>
      <table className="w-full border-collapse">
        <thead>
          <tr className="bg-gray-200">
            <th><Checkbox checked={false} /> </th> {/* 첫 번째 컬럼 (전체 선택) */}
            <th>이름</th>
            <th>부서</th>
            <th>직급</th>
            <th>이메일</th>
            <th>전화번호</th>
            <th>근무 상태</th>
          </tr>
        </thead>
        <tbody>
          {contactsData.length > 0 ? (
            contactsData.map((contact) => (
              <tr key={contact.id}>
                <td>
                  <Checkbox
                      checked={contact.selected || false}
                      onChange={() => onSelectContact(contact.id)}  // onSelectContact의 인자 contact.id는 number
                  />
                </td>
                <td>{contact.name}</td>
                <td>{contact.department}</td>
                <td>{contact.position}</td>
                <td>{contact.email}</td>
                <td>{contact.phone}</td>
                <td>
                  {contact.status === "근무중" ? (
                      <span className="text-green-600">근무중</span>
                  ) : (
                      contact.status || "상태 없음"
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={7}>연락처가 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ContactList;
