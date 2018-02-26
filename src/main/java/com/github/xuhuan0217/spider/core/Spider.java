package com.github.xuhuan0217.spider.core;

import com.github.xuhuan0217.spider.entity.BookEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.xuhuan0217.spider.util.HTTPSUtil.urlRequest;

/**
 *  Spider Thread
 */
public class Spider implements Runnable {
    private final String baseurl;
    private final int offset;
    private final String url;
    private final HTMLParser parser;
    private final List<BookEntity> books;
    private final List<Integer> faillist;
    private final AtomicInteger max;
    private final boolean verbose;

    /**
     *
     * @param baseurl base url
     * @param offset offset for different page
     * @param parser parser to parse html
     * @param books send result to book list
     * @param faillist send fail information to faillist
     * @param max max offset detect by other spider
     * @param verbose verbose or not
     */
    public Spider(String baseurl,int offset,HTMLParser parser,List<BookEntity> books,List<Integer> faillist,AtomicInteger max,boolean verbose){
        this.baseurl = baseurl;
        this.offset = offset;
        this.url = baseurl+"?start="+offset*20+"&type=S";
        this.parser = parser;
        this.books = books;
        this.faillist = faillist;
        this.max = max;
        this.verbose = verbose;
    }

    public void run() {
        if(offset>max.get()) {
            //System.out.println(offset+","+max.get());
            return;
        }
        String content = urlRequest(url,"GET",true);
        List<BookEntity> entities = parser.parse(content);
        if(entities == null){
            if(parser.checkEnd(content)){
                max.set(offset);
                System.out.println("set max offset to "+offset);
            }else {
                faillist.add(offset);
            }
        }else {
            books.addAll(entities);
        }
        if(verbose){
            System.out.println("spider "+offset+" finished " +url);
        }
    }
}
