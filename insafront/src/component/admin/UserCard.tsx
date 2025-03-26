import React from 'react';

type User = {
  id: string;
  name: string;
  department: string;
};

type Props = {
  user: User;
  actionType: 'grant' | 'revoke';
  onAction: (user: User) => void;
};

export default function UserCard({user, actionType, onAction}: Props) {
  return (
      <div className="user-card">
        <span>{user.name}</span>
        <span className="department">{user.department}</span>
        <button
            className={`action-button ${actionType}`}
            onClick={() => onAction(user)}
        >
          {actionType === 'grant' ? '+' : '-'}
        </button>
      </div>
  );
}
