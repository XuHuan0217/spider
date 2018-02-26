package com.github.xuhuan0217.spider.entity;

import java.util.List;

/**
 *  Factory interface, use genetic type for extension
 * @param <T>
 */
public interface EntityFactory<T> {
    /**
     * create new Entity
     * @return
     */
    T create();

    /**
     * validate entities
     * @param lists
     * @return
     */
    List<T> validate_all(List<T> lists);
}
