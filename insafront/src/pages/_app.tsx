import "@/styles/globals.css";
import type {AppProps} from "next/app";
import TopBar from "@/component/topbar/Topbar";
import MainLayout from "@/component/MainLayout";

export default function App({Component, pageProps}: AppProps) {
  return (
      <>
        <MainLayout>
          <Component {...pageProps} />
        </MainLayout>
      </>
  );
}
