import styled from '@emotion/styled';

export const TableContainer = styled('div')`
    width: 100%;
    flex: 1;
    background: #fff;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
`;

export const StyledTable = styled('table')`
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  color: #333;
`;

export const StyledTableHead = styled('thead')`
  background-color: #f4f6f8;
  text-align: left;
  
  th {
    padding: 12px;
    border-bottom: 2px solid #ddd;
  }
`;

export const StyledTableRow = styled('tr')`
  &:hover {
    background-color: #f9fafb;
  }
`;

export const StyledTableCell = styled('td')`
  padding: 12px;
  border-bottom: 1px solid #e0e0e0;
`;

export const CheckboxContainer = styled('div')`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const SelectedRow = styled(StyledTableRow)`
  background-color: #e3f2fd;
  &:hover {
    background-color: #d0ebff;
  }
`;
