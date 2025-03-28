// DepartmentListPage.tsx
import { AppProps } from 'next/app'; // AppProps 가져오기
import IndexLayout from "@/component/department/layout/indexLayout"; // MainLayout을 IndexLayout으로 변경
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// QueryClient 생성
const queryClient = new QueryClient();

function DepartmentListPage({ Component, pageProps }: AppProps) { // AppProps 타입 사용
    return (
        <QueryClientProvider client={queryClient}>
            {/* IndexLayout으로 감싸서 페이지 내용 표시 */}
            <IndexLayout>
                <Component {...pageProps} />
            </IndexLayout>
            {/* ToastContainer는 레이아웃 외부로 빼서 최상단에 위치 */}
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar />
        </QueryClientProvider>
    );
}

export default DepartmentListPage;
