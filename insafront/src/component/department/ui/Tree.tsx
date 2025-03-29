import React, {useState, useMemo, useEffect} from "react";
import {DndProvider} from "react-dnd";
import {
    Tree,
    NodeModel,
    MultiBackend,
    getBackendOptions
} from "@minoru/react-dnd-treeview";
import {Department, Employees} from '@/type/DepartmentDTO'


interface TreeProps {
    departments?: Department[],
    handleDepartmentClick: (departmentId: string) => void
}

const stringToNumber = (text: string) => parseInt(text.replace("D" , ""))

export default function ({departments, handleDepartmentClick}: TreeProps) {

    const newData: NodeModel<Department>[] = useMemo(() => (departments || []).map<NodeModel<Department>>((o, index) => ({
        id: stringToNumber(o.departmentId),
        parent: stringToNumber(o.parentDepartmentId || "0"),
        text: o.departmentName,
        droppable: false,
        data: o
    })), [departments])

    // console.log(newData)

    const [treeData, setTreeData] = useState<NodeModel<Department>[]>([]);

    useEffect(() => {
        setTreeData(newData)
    }, [newData]);


    const handleDrop = (newTree: NodeModel<Department>[]) => setTreeData(newTree);


    return <div>
        <DndProvider backend={MultiBackend} options={getBackendOptions()}>
            <Tree
                tree={treeData}
                rootId={0}
                render={(node, {depth, isOpen, onToggle}) => (
                    <div
                        style={{marginInlineStart: depth * 10, cursor: 'pointer'}}
                        onClick={() => handleDepartmentClick(node?.data?.departmentId || "D001")}
                    >
                        {node.droppable && (
                            <span onClick={onToggle}>{isOpen ? "[-]" : "[+]"}</span>
                        )}
                        {node.text} {node.data?.employees.length || 0} 명
                    </div>
                )}
                dragPreviewRender={(monitorProps) => (
                    <div>{monitorProps.item.text}</div>
                )}
                onDrop={handleDrop}
            />
        </DndProvider>
    </div>
}