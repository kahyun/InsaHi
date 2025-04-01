import { TableContainer as TableContainer2 } from './styled';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Checkbox from '@mui/material/Checkbox';
import { useState } from "react";
import { Contact, ContactListProps } from '@/type/EmployeeTable';

const ContactList: React.FC<ContactListProps> = ({ contactsData, onSelectContact }) => {
  const [selectAll, setSelectAll] = useState(false);
  const [selectedContacts, setSelectedContacts] = useState<{ [key: string]: boolean }>({});

  const handleSelectAllChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const checked = event.target.checked;
    setSelectAll(checked);

    const updatedSelections = contactsData.reduce((acc, contact) => {
      acc[contact.employeeId] = checked;
      return acc;
    }, {} as { [key: string]: boolean });

    setSelectedContacts(updatedSelections);
    const selectedEmployeeIds = Object.keys(updatedSelections).filter(id => updatedSelections[id]);
    alert(`선택된 직원 IDs: ${selectedEmployeeIds.join(', ')}`);
  };

  const handleCheckboxChange = (employeeId: string, name: string) => {
    setSelectedContacts(prev => ({
      ...prev,
      [employeeId]: !prev[employeeId],
    }));
    alert(`선택된 직원 ID: ${employeeId}, 이름: ${name}`);
  };

  const handleRowClick = (contact: Contact) => {
    onSelectContact(contact.employeeId);

  };

  return (
      <TableContainer2 className="TableContainer">
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 800 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>
                  <Checkbox checked={selectAll} onChange={handleSelectAllChange} />
                </TableCell>
                <TableCell>이름</TableCell>
                <TableCell>이메일</TableCell>
                <TableCell>전화번호</TableCell>
                <TableCell>소속 부서</TableCell>
                <TableCell>직급</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {contactsData.length > 0 ? (
                  contactsData.map((contact) => (
                      <TableRow
                          key={contact.employeeId}
                          sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                          style={{
                            cursor: 'pointer',
                            backgroundColor: selectedContacts[contact.employeeId] ? '#f0f0f0' : 'transparent',
                          }}
                          onClick={() => handleRowClick(contact)} // 행 클릭 이벤트 추가
                      >
                        <TableCell>
                          <Checkbox
                              checked={!!selectedContacts[contact.employeeId]}
                              onChange={(e) => {
                                e.stopPropagation(); // 행 클릭과 체크박스 클릭 이벤트 분리
                                handleCheckboxChange(contact.employeeId, contact.name);
                              }}
                              onClick={(e) => e.stopPropagation()} // 체크박스 클릭 시 이벤트 전파 방지
                          />
                        </TableCell>
                        <TableCell>{contact.name}</TableCell>
                        <TableCell>{contact.email || "이메일 없음"}</TableCell>
                        <TableCell>{contact.phoneNumber || "없음"}</TableCell>
                        <TableCell>{contact.departmentName || "부서 없음"}</TableCell>
                        <TableCell>{contact.positionName || "미지정"}</TableCell>
                      </TableRow>
                  ))
              ) : (
                  <TableRow>
                    <TableCell colSpan={7}>직원 데이터가 없습니다.</TableCell>
                  </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </TableContainer2>
  );
};

export default ContactList;
