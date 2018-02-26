package com.github.xuhuan0217.spider.core;

import com.github.xuhuan0217.spider.entity.BookEntity;
import com.github.xuhuan0217.spider.entity.BookEntityFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.github.xuhuan0217.spider.util.HTTPSUtil.urlRequest;

public class testHTMLParser {
    private HTMLParser parser;
    private String content;
    private String httpsUrl = "https://book.douban.com/tag/%E7%BC%96%E7%A8%8B?type=S";
    public testHTMLParser() {
        this.parser = new HTMLParser(BookEntityFactory.getInstance());
        this.content = urlRequest(httpsUrl,"GET",true);
    }

    @Test
    public void testParser(){
        List<BookEntity> entityList = parser.parse(content);
        Assert.assertTrue(entityList.size()>0);
        BookEntity entity = entityList.get(0);
        Assert.assertNotNull(entity.toCsv());
    }

    @Test
    public void testCheckEnd(){
        String url1 = httpsUrl+"&start=123";
        String url2 = httpsUrl+"&start=2000";
        String url3 = "https://www.notexists123.com/";
        String content1 = urlRequest(url1,"GET",true);
        String content2 = urlRequest(url2,"GET",true);
        String content3 = urlRequest(url3,"GET",true);
        Assert.assertEquals(false,parser.checkEnd(content1));
        Assert.assertEquals(true,parser.checkEnd(content2));
        Assert.assertEquals(false,parser.checkEnd(content3));
    }

}
