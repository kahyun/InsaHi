import React, { useState, useMemo, useEffect } from "react";
import axios from "axios";
import { DndProvider } from "react-dnd";
import { Tree, NodeModel, MultiBackend, getBackendOptions } from "@minoru/react-dnd-treeview";
import { Department } from "@/type/DepartmentDTO";

interface TreeProps {
    departments?: Department[];
    handleDepartmentClick: (departmentId: string) => void;
    companyCode: string;
}

const stringToNumber = (text: string) => {
    let hash = 0;
    for (let i = 0; i < text.length; i++) {
        hash = text.charCodeAt(i) + ((hash << 5) - hash);
    }
    return Math.abs(hash);
};

export default function DepartmentTree({ departments, handleDepartmentClick, companyCode }: TreeProps) {
    const newData: NodeModel<Department>[] = useMemo(() => {
        if (!departments) return [];

        return departments
            .map<NodeModel<Department>>((dept) => {
                const id = stringToNumber(dept.departmentId);
                const parentId = dept.parentDepartmentId ? stringToNumber(dept.parentDepartmentId) : 0;

                return {
                    id,
                    parent: parentId,
                    text: dept.departmentName,
                    droppable: true,
                    data: dept,
                };
            })
            .filter((node) => !isNaN(node.id) && !isNaN(node.parent))
            .sort((a, b) => {
                if (a.parent !== b.parent) return a.parent - b.parent;
                return a.id - b.id;
            });
    }, [departments]);

    const [treeData, setTreeData] = useState<NodeModel<Department>[]>([]);

    useEffect(() => {
        setTreeData(newData);
    }, [newData]);

    const handleDrop = (newTree: NodeModel<Department>[]) => setTreeData(newTree);

    /** 부서 삭제 함수 */
    const handleDelete = async (id: number, departmentId: string) => {
        try {
            // 직원 이동
            await axios.patch(`/api/${companyCode}/department/move`, null, {
                params: { fromDepartmentId: departmentId, toDepartmentId: "1" }
            });

            // 부서 삭제
            await axios.delete(`/api/${companyCode}/department/${departmentId}`);

            // 트리에서 삭제된 부서 제거
            setTreeData((prev) => prev.filter((node) => node.id !== id && node.parent !== id));
        } catch (error) {
            console.error("부서 삭제 실패: ", error);
        }
    };

    return (
        <div>
            <DndProvider backend={MultiBackend} options={getBackendOptions()}>
                <Tree
                    tree={treeData}
                    rootId={0}
                    render={(node, { depth, isOpen, onToggle }) => (
                        <div
                            style={{
                                marginLeft: depth * 20,
                                display: "flex",
                                alignItems: "center",
                                cursor: "pointer",
                            }}
                            onClick={() => handleDepartmentClick(node?.data?.departmentId || "D001")}
                        >
                            {node.droppable && (
                                <span onClick={onToggle} style={{ marginRight: 5 }}>
                                    {isOpen ? "[-]" : "[+]" }
                                </span>
                            )}
                            {node.text} ({node.data?.employees?.length || 0} 명)
                            <button
                                style={{ all: "unset", cursor: "pointer", marginLeft: "5px" }}
                                onClick={(e) => {
                                    e.stopPropagation();
                                    if (typeof node.id === "number" && node.data?.departmentId) {
                                        handleDelete(node.id, node.data.departmentId);
                                    } else {
                                        console.error("삭제 실패: departmentId가 유효하지 않음", node);
                                    }
                                }}
                            >
                                삭제
                            </button>
                        </div>
                    )}
                    dragPreviewRender={(monitorProps) => <div>{monitorProps.item.text}</div>}
                    onDrop={handleDrop}
                />
            </DndProvider>
        </div>
    );
}
