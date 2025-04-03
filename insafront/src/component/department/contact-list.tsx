import { TableContainer, StyledTable, StyledTableHead, StyledTableRow, StyledTableCell, SelectedRow, CheckboxContainer } from './styled';
import TableBody from '@mui/material/TableBody';
import Checkbox from '@mui/material/Checkbox';
import { useState } from "react";
import { Contact, ContactListProps } from '@/type/EmployeeTable';

const ContactList: React.FC<ContactListProps> = ({
                                                   contactsData,
                                                   onSelectContact,
                                                   setSelectedContacts,
                                                   selectedContacts,
                                                   departmentName,
                                                   companyCode
                                                 }) => {
  const [selectAll, setSelectAll] = useState(false);

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
    alert(`선택된 직원 ID: ${employeeId}, 이름: ${name} 체크박스`);
  };

  const handleRowClick = (contact: Contact) => {
    onSelectContact(contact.employeeId);
  };

  return (
      <TableContainer>
        <StyledTable>
          <StyledTableHead>
            <StyledTableRow>
              <StyledTableCell>
                <CheckboxContainer>
                  <Checkbox checked={selectAll} onChange={handleSelectAllChange} />
                </CheckboxContainer>
              </StyledTableCell>
              <StyledTableCell>이름</StyledTableCell>
              <StyledTableCell>이메일</StyledTableCell>
              <StyledTableCell>전화번호</StyledTableCell>
              <StyledTableCell>소속 부서</StyledTableCell>
              <StyledTableCell>직급</StyledTableCell>
            </StyledTableRow>
          </StyledTableHead>
          <TableBody>
            {contactsData.length > 0 ? (
                contactsData.map((contact) => {
                  const isSelected = !!selectedContacts[contact.employeeId];
                  return (
                      <SelectedRow
                          key={contact.employeeId}
                          style={{
                            cursor: 'pointer',
                            backgroundColor: isSelected ? '#e3f2fd' : 'transparent',
                          }}
                          onClick={() => handleRowClick(contact)}
                      >
                        <StyledTableCell>
                          <CheckboxContainer>
                            <Checkbox
                                checked={isSelected}
                                onChange={(e) => {
                                  e.stopPropagation();
                                  handleCheckboxChange(contact.employeeId, contact.name);
                                }}
                                onClick={(e) => e.stopPropagation()}
                            />
                          </CheckboxContainer>
                        </StyledTableCell>
                        <StyledTableCell>{contact.name}</StyledTableCell>
                        <StyledTableCell>{contact.email || "이메일 없음"}</StyledTableCell>
                        <StyledTableCell>{contact.phoneNumber || "없음"}</StyledTableCell>
                        <StyledTableCell>{departmentName || "부서 없음"}</StyledTableCell>
                        <StyledTableCell>{contact.positionName || "미지정"}</StyledTableCell>
                      </SelectedRow>
                  );
                })
            ) : (
                <StyledTableRow>
                  <StyledTableCell colSpan={7}>직원 데이터가 없습니다.</StyledTableCell>
                </StyledTableRow>
            )}
          </TableBody>
        </StyledTable>
      </TableContainer>
  );
};

export default ContactList;
