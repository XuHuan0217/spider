package com.github.xuhuan0217.spider.entity;

import org.junit.Assert;
import org.junit.Test;

public class BookEntityTest {
    private EntityFactory<BookEntity> factory;
    public BookEntityTest() {
        this.factory = BookEntityFactory.getInstance();
    }

    @Test
    public void testFactorySingleton(){
        EntityFactory<BookEntity> factory1 = BookEntityFactory.getInstance();
        EntityFactory<BookEntity> factory2 = BookEntityFactory.getInstance();
        Assert.assertEquals(factory1,factory2);
    }

    @Test
    public void testFactoryCreate(){
        BookEntity entity1 = factory.create();
        entity1.setTitle("test");
        entity1.setAuthor("test");
        entity1.setPublication("test");
        entity1.setDate("test");
        BookEntity entity2 = factory.create();
        entity2.setTitle("test");
        entity2.setAuthor("test");
        entity2.setPublication("test");
        entity2.setDate("test");

        Assert.assertEquals(entity1,entity2);
    }


}
