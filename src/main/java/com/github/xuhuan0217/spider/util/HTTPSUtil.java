package com.github.xuhuan0217.spider.util;


import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HTTPSUtil
 */
public class HTTPSUtil {
    private static class DefaultTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }


        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }


        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static HttpsURLConnection getHttpsURLConnection(String url) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            //ignore
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //ignore
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL requestUrl = new URL(url);
        HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl.openConnection();

        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL requestUrl=new URL(url);
        HttpURLConnection httpConn=(HttpURLConnection)requestUrl.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        return httpConn;
    }

    public static String urlRequest(String url,String requestMethod,Boolean https){
        StringBuffer buffer=null;
        try{
            InputStream is= null;
            if (https){
                HttpsURLConnection conn = getHttpsURLConnection(url);
                conn.setRequestMethod(requestMethod);
                conn.connect();
                is=conn.getInputStream();
            }else {
                HttpURLConnection conn = getHttpURLConnection(url);
                conn.setRequestMethod(requestMethod);
                conn.connect();
                is=conn.getInputStream();
            }

            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }

        }catch(Exception e){
            return "";
        }
        return buffer.toString();
    }

}
