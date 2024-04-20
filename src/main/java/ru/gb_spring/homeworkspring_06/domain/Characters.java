package ru.gb_spring.homeworkspring_06.domain;

import lombok.Data;

import java.util.List;

/**
 * Представляет собой коллекцию персонажей "Rick and Morty".
 */
@Data
public class Characters {
     Info info;
     List<Result> results;
}
