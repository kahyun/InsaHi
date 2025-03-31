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

type Contact = {
  id: string;
  employeeId: string;  // employeeId 추가
  name: string;
  department: string;
  position: string;
  email: string;
  phone: string;
  status?: string;
  selected?: boolean;
};

type ContactListProps = {
  contactsData: Contact[];
  onSelectContact: (contactId: string) => void;
};

const ContactList = ({ contactsData, onSelectContact }: ContactListProps) => {
  const [selectAll, setSelectAll] = useState(false);

  const handleSelectAllChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const checked = event.target.checked;
    setSelectAll(checked);
    contactsData.forEach((contact) => {
      contact.selected = checked;
    });
    const selectedContactIds = contactsData.filter(contact => contact.selected).map(contact => contact.id);
    alert(selectedContactIds.join(', '));
  };

  const handleCheckboxChange = (contactId: string, employeeId: string) => {
    onSelectContact(contactId);
    alert(`선택된 직원 ID: ${employeeId}`);
  };

  const handleRowClick = (contactId: string, employeeId: string) => {
    onSelectContact(contactId);
    alert(`선택된 직원 ID: ${employeeId}`);
  };

  return (
      <TableContainer2 className="TableContainer">
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>
                  <Checkbox checked={selectAll} onChange={handleSelectAllChange} />
                </TableCell>
                <TableCell>이름</TableCell>
                <TableCell>부서</TableCell>
                <TableCell>직급</TableCell>
                <TableCell>이메일</TableCell>
                <TableCell>전화번호</TableCell>
                <TableCell>근무 상태</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {contactsData.length > 0 ? (
                  contactsData.map((contact) => (
                      <TableRow
                          key={contact.id}
                          sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                          onClick={() => handleRowClick(contact.id, contact.employeeId)}  // employeeId 전달
                          style={{
                            cursor: 'pointer',
                            backgroundColor: contact.selected ? '#f0f0f0' : 'transparent',
                          }}
                      >
                        <TableCell component="th" scope="row">
                          <Checkbox
                              checked={contact.selected || false}
                              onChange={() => handleCheckboxChange(contact.id, contact.employeeId)}  // employeeId 전달
                          />
                        </TableCell>
                        <TableCell>{contact.name}</TableCell>
                        <TableCell>{contact.department}</TableCell>
                        <TableCell>{contact.position}</TableCell>
                        <TableCell>{contact.email}</TableCell>
                        <TableCell>{contact.phone}</TableCell>
                        <TableCell>
                          {contact.status === "근무중" ? (
                              <span className="text-green-600">근무중</span>
                          ) : (
                              contact.status || "상태 없음"
                          )}
                        </TableCell>
                      </TableRow>
                  ))
              ) : (
                  <TableRow>
                    <TableCell colSpan={7}>연락처가 없습니다.</TableCell>
                  </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </TableContainer2>
  );
};

export default ContactList;
