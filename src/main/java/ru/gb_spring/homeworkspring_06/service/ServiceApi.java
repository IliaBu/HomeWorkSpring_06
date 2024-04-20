package ru.gb_spring.homeworkspring_06.service;

import ru.gb_spring.homeworkspring_06.domain.Characters;
import ru.gb_spring.homeworkspring_06.domain.Result;

/**
 * Интерфейс, предоставляющий методы для работы с API сервисом "Rick and Morty".
 */
public interface ServiceApi {

    public Characters getAllCharacters();

    public Result getCharacterById(Integer id);

    public Characters getCharactersWithPage(String page);

    public int getPageNumberById(int id);

    public Characters getFilteredCharacters(String name, String status, String species, String type, String gender);

}
