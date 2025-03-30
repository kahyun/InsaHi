// import { Checkbox } from "@/component/department/ui/checkbox";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Checkbox from '@mui/material/Checkbox';

import { TableContainer as TableContainer2 } from './styled'

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
    <TableContainer2 className="TableContainer">
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell><Checkbox checked={false} /></TableCell>
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
                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                    >
                      <TableCell component="th" scope="row">
                        <Checkbox
                            checked={contact.selected || false}
                            onChange={() => onSelectContact(contact.id)}  // onSelectContact의 인자 contact.id는 number
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
