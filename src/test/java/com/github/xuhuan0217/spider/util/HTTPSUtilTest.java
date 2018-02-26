package com.github.xuhuan0217.spider.util;


import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import static com.github.xuhuan0217.spider.util.HTTPSUtil.urlRequest;

public class HTTPSUtilTest {
    private String httpsUrl =  "https://book.douban.com";

    @Test
    public void testHttpsConnection(){
        try {
            HttpsURLConnection conn = HTTPSUtil.getHttpsURLConnection(httpsUrl);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            StringBuffer buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
            Assert.assertTrue(buffer.length()>0);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testUrlRequest(){
        String result = urlRequest(httpsUrl,"GET",true);
        Assert.assertTrue(result.length()>0);
    }
}
