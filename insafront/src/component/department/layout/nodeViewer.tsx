// components/NodeViewer.tsx
import React from "react";

interface NodeViewerProps {
    selectedDepartment?: string
}

const NodeViewer = ({selectedDepartment}: NodeViewerProps) => {
    return (
        <div className={"NodeViewer"}>
            <h3>Node Viewer</h3>
            {/* NodeViewer 관련 내용 */}
            <p>여기에는 Node Viewer 내용이 표시됩니다.</p>
        </div>
    );
};

export default NodeViewer;
