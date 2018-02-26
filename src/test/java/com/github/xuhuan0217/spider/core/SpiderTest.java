package com.github.xuhuan0217.spider.core;

import com.github.xuhuan0217.spider.entity.BookEntity;
import com.github.xuhuan0217.spider.entity.BookEntityFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiderTest {

    @Test
    public void testSpider(){
        String url = "https://book.douban.com/tag/编程";
        HTMLParser parser = new HTMLParser(BookEntityFactory.getInstance());
        List<BookEntity> books = Collections.synchronizedList(new ArrayList<BookEntity>());
        List<Integer> faillist = Collections.synchronizedList(new ArrayList<Integer>());
        AtomicInteger max = new AtomicInteger(5);
        ExecutorService exec = Executors.newFixedThreadPool(10);
        boolean verbose = true;
        for(int i=0;i<max.get();i++) {
            while (faillist.size()>0){
                int offset = faillist.remove(faillist.size()-1);
                exec.execute(new Spider(url, offset, parser, books, faillist, max, verbose));
            }
            exec.execute(new Spider(url, i, parser, books, faillist, max, verbose));
        }
        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            //ignore
        }
        Assert.assertTrue(books.size()>0);
    }
}
