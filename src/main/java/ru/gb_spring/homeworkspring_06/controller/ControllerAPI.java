package ru.gb_spring.homeworkspring_06.controller;

import lombok.RequiredArgsConstructor;
import ru.gb_spring.homeworkspring_06.domain.Characters;
import ru.gb_spring.homeworkspring_06.domain.Result;
import ru.gb_spring.homeworkspring_06.exception.CharacterNotFoundException;
import ru.gb_spring.homeworkspring_06.service.ServiceApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов API.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class ControllerAPI {

    private final ServiceApi serviceApi;

    /**
     * Возвращает всех персонажей.
     * @return коллекциб персонажей
     */
    @GetMapping("/")
    public ResponseEntity<Characters> getCharacters() {
        Characters allCharacters = serviceApi.getAllCharacters();
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }

    /**
     * Возвращает информацию о персонаже по его идентификатору.
     * @param id Идентификатор персонажа
     * @return информация о персонаже
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result> getCharacterById(@PathVariable Integer id) {
        try {
            Result character = serviceApi.getCharacterById(id);
            return ResponseEntity.ok(character);
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
