import React, { useState, useMemo, useEffect } from "react";
import { DndProvider } from "react-dnd";
import { Tree, NodeModel, MultiBackend, getBackendOptions } from "@minoru/react-dnd-treeview";
import { Department } from '@/type/DepartmentDTO'; // 부서 타입 임포트

interface TreeProps {
    departments?: Department[];
    handleDepartmentClick: (departmentId: string) => void;
}

const stringToNumber = (text: string) => parseInt(text.replace("D", ""));

export default function ({ departments, handleDepartmentClick }: TreeProps) {
    const filterNumber = (text: string) => {
        const numbers = text.replace(/\D/g, ""); // 숫자만 추출
        const letters = text.replace(/\d/g, ""); // 문자만 추출
        return { numbers, letters };
    };

    const toUnicodeNumbers = (letters: string): string => {
        return letters
            .split("")
            .map((char) => char.charCodeAt(0))
            .join(" ");
    };

    // 부서 UI 변환 함수
    const convertNumber = (text: string) => {
        const { numbers, letters } = filterNumber(text);
        return {
            numbers: Number(numbers), // 문자열 숫자를 정수로 변환
            unicode: toUnicodeNumbers(letters), // 유니코드 숫자로 변환
        };
    };

    // 새 트리 데이터 생성
    const newData: NodeModel<Department>[] = useMemo(
        () =>
            (departments || []).map<NodeModel<Department>>((o) => {
                const { numbers } = convertNumber(o.departmentId);

                return {
                    id: numbers,
                    parent: Number(o.parentDepartmentId) || 0, // 루트 노드는 parent가 0이어야 함
                    text: `${o.departmentName}`,
                    droppable: false,
                    data: o,
                };
            }),
        [departments]
    );

    const [treeData, setTreeData] = useState<NodeModel<Department>[]>([]);

    useEffect(() => {
        setTreeData(newData);
    }, [newData]);

    const handleDrop = (newTree: NodeModel<Department>[]) => setTreeData(newTree);

    return (
        <div>
            <DndProvider backend={MultiBackend} options={getBackendOptions()}>
                <Tree
                    tree={treeData}
                    rootId={0} // 루트 노드는 id가 0인 항목을 기준으로 렌더링
                    render={(node, { depth, isOpen, onToggle }) => (
                        <div
                            style={{ marginInlineStart: depth * 10, cursor: "pointer" }}
                            onClick={() => handleDepartmentClick(node?.data?.departmentId || "D001")}
                        >
                            {node.droppable && (
                                <span onClick={onToggle}>{isOpen ? "[-]" : "[+]"}</span>
                            )}
                            {node.text} {node.data?.employees.length || 0} 명
                        </div>
                    )}
                    dragPreviewRender={(monitorProps) => <div>{monitorProps.item.text}</div>}
                    onDrop={handleDrop}
                />
            </DndProvider>
        </div>
    );
}
