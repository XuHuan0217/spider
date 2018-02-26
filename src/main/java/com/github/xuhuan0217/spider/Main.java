package com.github.xuhuan0217.spider;

import com.github.xuhuan0217.spider.core.HTMLParser;
import com.github.xuhuan0217.spider.core.Spider;
import com.github.xuhuan0217.spider.entity.BookEntity;
import com.github.xuhuan0217.spider.entity.BookEntityFactory;
import com.github.xuhuan0217.spider.util.ExcelWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    public static void main(String [] args){
        //base url
        String url = "https://book.douban.com/tag/编程";
        // init html parse, specify the entity type
        HTMLParser parser = new HTMLParser(BookEntityFactory.getInstance());
        // synchronizedList, used to store result
        List<BookEntity> books = Collections.synchronizedList(new ArrayList<BookEntity>());
        // synchronizedList, used to catch error
        List<Integer> faillist = Collections.synchronizedList(new ArrayList<Integer>());
        // atomic integer, used to send max signal to other spider
        AtomicInteger max = new AtomicInteger(200);
        // multi-threading pools
        ExecutorService exec = Executors.newFixedThreadPool(10);
        boolean verbose = true;
        //spider start
        for(int i=0;i<max.get();i++) {
            exec.execute(new Spider(url, i, parser, books, faillist, max, verbose));
        }
        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            //ignore
        }
        // spider end
        /*
        while (faillist.size()>0){
            int offset = faillist.remove(faillist.size()-1);
            exec.execute(new Spider(url, offset, parser, books, faillist, max, verbose));
        }
        */
        //System.out.println(books.size());

        // validate and sort
        books = BookEntityFactory.getInstance().validate_all(books);
        Collections.sort(books);

        //write to excel
        try {
            String [] header = {"id","title","rating","rating num","author","press","publish date","price"};
            ExcelWriter writer = new ExcelWriter("result.xls","books");
            writer.createHeader(header);
            int count = 0;
            for(BookEntity book:books){
                count +=1;
                writer.append(count,book);
                if (count>=40) break;
            }
            writer.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");


    }
}
