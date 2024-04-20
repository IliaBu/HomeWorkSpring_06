package ru.gb_spring.homeworkspring_06.domain;

import lombok.Data;

/**
 * Представляет информацию о коллекции персонажей.
 */
@Data
public class Info {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
