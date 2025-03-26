import {useState} from 'react';
import UserCard from '@/component/admin/UserCard';
import '@/styles/admin/adminPermission.module.css';

type User = {
  id: string;
  name: string;
  department: string;
};

const dummyUsers: User[] = [
  {id: '1', name: '김지훈', department: '인사팀'},
  {id: '2', name: '이수정', department: '개발팀'},
  {id: '3', name: '박세영', department: '인사팀'},
  {id: '4', name: '조민수', department: '개발팀'},
  {id: '5', name: '정혜원', department: '인사팀'},
  {id: '6', name: '한지훈', department: '개발팀'},
];

export default function AdminPermissionPage() {
  const [admins, setAdmins] = useState<User[]>([
    dummyUsers[0],
    dummyUsers[1],
  ]);
  const [users, setUsers] = useState<User[]>(
      dummyUsers.slice(2)
  );

  const grantAdmin = (user: User) => {
    setUsers(users.filter((u) => u.id !== user.id));
    setAdmins([...admins, user]);
  };

  const revokeAdmin = (user: User) => {
    setAdmins(admins.filter((a) => a.id !== user.id));
    setUsers([...users, user]);
  };

  const groupedUsers = users.reduce<Record<string, User[]>>((acc, user) => {
    if (!acc[user.department]) {
      acc[user.department] = [];
    }
    acc[user.department].push(user);
    return acc;
  }, {});

  return (
      <div className="container">
        <h2 className="section-title">👑 Admin 사용자</h2>
        <div className="user-list admin-list">
          {admins.map((admin) => (
              <UserCard key={admin.id} user={admin} actionType="revoke" onAction={revokeAdmin}/>
          ))}
        </div>

        <h2 className="section-title">🧑‍💼 일반 사용자 (부서별)</h2>
        <div className="user-list">
          {Object.entries(groupedUsers).map(([dept, users]) => (
              <div key={dept} className="department-section">
                <h3 className="department-title">📁 {dept}</h3>
                {users.map((user) => (
                    <UserCard key={user.id} user={user} actionType="grant" onAction={grantAdmin}/>
                ))}
              </div>
          ))}
        </div>
      </div>
  );
}
