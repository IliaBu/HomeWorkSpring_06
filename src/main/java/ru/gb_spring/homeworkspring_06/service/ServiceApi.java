package ru.gb_spring.homeworkspring_06.service;

import ru.gb_spring.homeworkspring_06.domain.Characters;
import ru.gb_spring.homeworkspring_06.domain.Result;

/**
 * Интерфейс, предоставляющий методы для работы с API сервисом "Rick and Morty".
 */
public interface ServiceApi {

    Characters getAllCharacters();

    Result getCharacterById(Integer id);

    Characters getCharactersWithPage(String page);

    int getPageNumberById(int id);

    Characters getFilteredCharacters(String name, String status, String species, String type, String gender);

}
