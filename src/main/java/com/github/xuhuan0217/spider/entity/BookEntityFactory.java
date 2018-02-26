package com.github.xuhuan0217.spider.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Book Entity Factory, Singleton Class
 */
public class BookEntityFactory implements EntityFactory<BookEntity> {

    private static BookEntityFactory ourInstance = new BookEntityFactory();

    public static BookEntityFactory getInstance() {
        return ourInstance;
    }

    private BookEntityFactory() {
    }


    public BookEntity create(){
        return new BookEntity();
    }


    private boolean validate(BookEntity entity){
        return entity.getRatingnum()>=1000;
    }

    public List<BookEntity> validate_all(List<BookEntity> books){
        List<BookEntity> valid_books = new ArrayList<>();
        for(BookEntity book:books){
            if(validate(book)){
                valid_books.add(book);
            }
        }
        return valid_books;
    }
}
