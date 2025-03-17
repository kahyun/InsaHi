import React from 'react';

/**
 * props
 *  - x, y: 메뉴를 띄울 위치
 *  - visible: 보여줄지 말지 (boolean)
 *  - menuItems: [{ label: "삭제", onClick: ()=>{} }, ...] 식으로
 *  - onClose: 사용자가 메뉴 바깥 클릭 등으로 닫을 때 실행할 함수
 */
function ContextMenu({ x, y, visible, menuItems, onClose }) {
    // 만약 visible=false 라면 아무것도 렌더링하지 않는다
    if (!visible) return null;

    // 메뉴 바깥 클릭 시 닫아주는 방법:
    // 보통 부모에서 window.onclick 설정하거나, 포털을 쓰거나,
    // 다양한 방법이 있지만, 여기서는 가장 간단히 onBlur/onClick 처리 예시.

    return (
        <div
            style={{
                position: 'fixed',
                top: y,
                left: x,
                background: 'white',
                border: '1px solid #ccc',
                zIndex: 9999,
            }}
            onMouseLeave={onClose} // 마우스가 영역을 벗어나면 닫는 식
        >
            {menuItems.map((item, idx) => (
                <div
                    key={idx}
                    style={{ padding: '5px 10px', cursor: 'pointer' }}
                    onClick={() => {
                        item.onClick();
                        onClose(); // 실행 후 메뉴 닫기
                    }}
                >
                    {item.label}
                </div>
            ))}
        </div>
    );
}

export default ContextMenu;
