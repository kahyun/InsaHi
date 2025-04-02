import styled from '@emotion/styled';

// 전체 트리 컨테이너
export const TreeContainer = styled.div`
  width: 100%;
  padding: 10px;
  background-color: #f8f9fa;
  
`;

// 트리 노드 리스트
export const TreeList = styled.ul`
  list-style: none;
  padding-left: 20px;
  margin: 0;
`;

// 개별 트리 노드
export const TreeNode = styled.li<{ depth: number; isOpen: boolean }>`
  display: flex;
  align-items: center;
  padding: 6px 8px;
  background-color: ${({ isOpen }) => (isOpen ? "#e9ecef" : "transparent")};
  transition: background-color 0.2s ease-in-out;

  &:hover {
    background-color: #dee2e6;
    cursor: pointer;
  }

  // 들여쓰기 조정
  padding-left: ${({ depth }) => depth * 16}px;
`;

// 드래그 중인 아이템 스타일
export const DraggingNode = styled(TreeNode)`
  opacity: 0.5;
  background-color: #adb5bd;
`;

// 드롭 가능 위치 강조
export const DropTargetNode = styled(TreeNode)`
  background-color: #d1e7dd;
  border: 1px dashed #20c997;
`;

// 플래이스홀더 (드롭 가능한 위치 표시)
export const Placeholder = styled.div`
  height: 32px;
  background-color: #e9ecef;
  border: 1px dashed #6c757d;
  margin: 4px 0;
  border-radius: 4px;
`;
