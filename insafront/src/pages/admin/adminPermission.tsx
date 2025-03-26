import {useEffect, useState} from 'react';
import UserCard from '@/component/admin/UserCard';
import '@/styles/admin/adminPermission.module.css';

// 실제 API 응답 타입
type RawUser = {
  employeeId: string;
  name: string;
  department: string;
  authorityList: { authorityName: string }[];
};

// 변환 후 사용할 타입
type User = {
  employeeId: string;
  name: string;
  department: string;
  authorityList: string[];
};

export default function AdminPermissionPage() {
  const [admins, setAdmins] = useState<User[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [companyCodeFromToken, setCompanyCodeFromToken] = useState<string>('');

  useEffect(() => {
    if (typeof window === 'undefined') return;
    const companyCode = localStorage.getItem('companyCode') ?? '';
    setCompanyCodeFromToken(companyCode);

    const ROLE_USER = 'ROLE_USER';

    const fetchUsers = async () => {
      try {
        const res = await fetch(`http://127.0.0.1:1006/employee/auth-list?companyCode=${companyCode}&authorityName=${ROLE_USER}`);
        const rawData: RawUser[] = await res.json();

        const data: User[] = rawData.map((user) => ({
          ...user,
          authorityList: user.authorityList?.map((auth) => auth.authorityName) ?? [],
        }));

        const adminList = data.filter((user) => user.authorityList.includes('ROLE_ADMIN'));
        const userList = data.filter((user) => !user.authorityList.includes('ROLE_ADMIN'));


        setAdmins(adminList);
        setUsers(userList);
      } catch (error) {
        console.error('사용자 데이터를 불러오는 중 오류 발생:', error);
      }
    };

    fetchUsers();
  }, []);

  const grantAdmin = async (user: User) => {
    try {
      const res = await fetch(`http://127.0.0.1:1006/employee/grant-admin?employeeId=${user.employeeId}`, {
        method: 'POST',
      });
      const message = await res.text();
      alert(message);

      setUsers(users.filter((u) => u.employeeId !== user.employeeId));
      setAdmins([...admins, {...user, authorityList: [...user.authorityList, 'ROLE_ADMIN']}]);
    } catch (error) {
      console.error('관리자 권한 부여 실패:', error);
      alert('관리자 권한 부여 실패');
    }
  };

  const revokeAdmin = async (user: User) => {
    try {
      const res = await fetch(`http://127.0.0.1:1006/employee/delete-admin?employeeId=${user.employeeId}`, {
        method: 'DELETE',
      });
      const message = await res.text();
      alert(message);

      setAdmins(admins.filter((a) => a.employeeId !== user.employeeId));
      setUsers([
        ...users,
        {
          ...user,
          authorityList: user.authorityList.filter((role) => role !== 'ROLE_ADMIN'),
        },
      ]);
    } catch (error) {
      console.error('관리자 권한 삭제 실패:', error);
      alert('관리자 권한 삭제 실패');
    }
  };

  return (
      <div className="container">
        <h2 className="section-title"> 관리자 사용자</h2>
        <div className="user-list admin-list">
          {admins.map((admin) => (
              <UserCard key={admin.employeeId} user={admin} actionType="revoke"
                        onAction={revokeAdmin}/>
          ))}
        </div>

        <h2 className="section-title"> 일반 사용자</h2>
        <div className="user-list">
          {users.map((user) => (
              <UserCard key={user.employeeId} user={user} actionType="grant" onAction={grantAdmin}/>
          ))}
        </div>
      </div>
  );
}