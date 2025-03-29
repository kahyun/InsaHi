import { Html, Head, Main, NextScript } from "next/document";
import {DocumentHeadTags, documentGetInitialProps} from '@mui/material-nextjs/v15-pagesRouter';

export default function Document(props: any) {
  return (
    <Html lang="en">
      <Head>
          <DocumentHeadTags {...props} />
      </Head>
      <body>
        <Main />
        <NextScript />
      </body>
    </Html>
  );
}

Document.getInitialProps = async (ctx: any) => {
    const finalProps = await documentGetInitialProps(ctx);
    return finalProps;
};
