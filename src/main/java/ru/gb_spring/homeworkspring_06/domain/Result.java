package ru.gb_spring.homeworkspring_06.domain;

import lombok.Data;

import java.util.Date;

/**
 * Представляет информацию о конкретном персонаже.
 */
@Data
public class Result {
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private String url;
    private Date created;
}
