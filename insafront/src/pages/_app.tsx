import "@/styles/globals.css";
import type { AppProps } from "next/app";
import MainLayout from "@/component/MainLayout";
import IndexLayout from "@/component/department/layout/indexLayout"; // IndexLayout 추가
import { useEffect } from "react";
import { useRouter } from "next/router";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Global } from "@emotion/react";
import {globalStyles } from '../styles/globalStyles'
import { AppCacheProvider } from '@mui/material-nextjs/v15-pagesRouter';

// QueryClient 생성
const queryClient = new QueryClient();

export default function App(props: AppProps) {
  const router = useRouter();
  const { Component, pageProps } = props

  // 로그인과 회원가입 페이지에서는 MainLayout을 사용하지 않음
  const noLayoutPaths = ["/Login", "/SignupForm"];
  const isNoLayoutPage = noLayoutPaths.includes(router.pathname);

  // department 경로에 대해서는 IndexLayout을 사용
  const isDepartmentPage = router.pathname.includes("department");

  useEffect(() => {
    const publicPaths = ["/Login", "/SignupForm", "/"]; // 인증 없이 접근 가능한 페이지
    const pathIsPublic = publicPaths.includes(router.pathname);
    const token = localStorage.getItem("accessToken");

    if (!token && !pathIsPublic) {
      router.push("/"); // 로그인 안 했으면 로그인 페이지로 이동
    }
  }, [router.pathname]);

  return (
      <AppCacheProvider {...props}>
      // QueryClientProvider로 전체 애플리케이션 감싸기
        <QueryClientProvider client={queryClient}>
          <Global styles={globalStyles} />
          {isNoLayoutPage ? (
              <Component {...pageProps} />
          ) : isDepartmentPage ? (
              // department 경로에는 IndexLayout 사용
              <IndexLayout>
                <Component {...pageProps} />
                <ToastContainer position="top-right" autoClose={3000} hideProgressBar />
              </IndexLayout>
          ) : (
              <MainLayout>
                <Component {...pageProps} />
                <ToastContainer position="top-right" autoClose={3000} hideProgressBar />
              </MainLayout>
          )}
        </QueryClientProvider>
      </AppCacheProvider>
  );
}
