package com.github.xuhuan0217.spider.core;


import com.github.xuhuan0217.spider.entity.BookEntity;
import com.github.xuhuan0217.spider.entity.EntityFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.util.ArrayList;
import java.util.List;

/**
 *  HTMP Parser
 */
public class HTMLParser{

    private EntityFactory<BookEntity> factory;
    public HTMLParser(EntityFactory<BookEntity> factory){

        this.factory = factory;
    }

    /**
     * Check if pages reach to end
     * @param content
     * @return <Code>boolean</Code>
     */
    public boolean checkEnd(String content){
        Document doc = Jsoup.parse(content);
        List<Element> list = doc.select("div.content");
        Element pages = doc.select("div.paginator").first();
        if(list.size()>0&&pages== null) return true;
        return false;
    }


    /**
     *  Parse HTML to BOOKENTITY list
     * @param content
     * @return <code>List<BookEntity></code>
     */
    public List<BookEntity> parse(String content){
        Document doc = Jsoup.parse(content);
        List<Element> subjects = doc.getElementsByClass("subject-item");
        if(subjects.size() == 0){
            return null;
        }
        List<BookEntity> books = new ArrayList<BookEntity>();
        for(Element subject : subjects){
            Element titleele = subject.select("a[title]").first();
            Element infoele = subject.select("div.pub").first();
            Element ratingele = subject.select("span.rating_nums").first();
            Element plele = subject.select("span.pl").first();
            String title = titleele.attr("title");
            String info = infoele.text();

            double rating = ratingele==null?-1:Double.parseDouble(ratingele.text());
            int pl = Integer.parseInt(plele.text().replaceAll("[^0-9]",""));
            String [] bookinfos = info.split("/");
            if(bookinfos.length<4)
                //throw new IllegalArgumentException("book information is not in right format"+bookinfos[0]);
                continue;
            String price = bookinfos[bookinfos.length-1];
            String date = bookinfos[bookinfos.length-2];
            String publication = bookinfos[bookinfos.length-3];
            String author = bookinfos[0];
            for(int i = 1;i <bookinfos.length-3;i++){
                author = author +"/" +bookinfos[i];
            }
            BookEntity entity = factory.create();
            entity.setTitle(title)
                    .setAuthor(author)
                    .setDate(date)
                    .setPrice(price)
                    .setPublication(publication)
                    .setRating(rating)
                    .setRatingnum(pl);

            books.add(entity);
        }
        return books;
    }
}
