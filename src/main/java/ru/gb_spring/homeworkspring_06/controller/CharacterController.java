package ru.gb_spring.homeworkspring_06.controller;

import ru.gb_spring.homeworkspring_06.domain.Characters;
import ru.gb_spring.homeworkspring_06.domain.Result;
import ru.gb_spring.homeworkspring_06.service.ServiceApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Контроллер для обработки запросов, связанных с персонажами.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class CharacterController {

    private final ServiceApi serviceApi;

    /**
     * Возвращает страницу со списком персонажей.
     * @param page Номер страницы (необязательный параметр)
     * @param model Модель данных
     * @return страницу со списком персонажей
     */
    @GetMapping()
    public String getCharacters(@RequestParam(value = "page", required = false) String page, Model model) {
        Characters allCharacters;
        if (StringUtils.isEmpty(page)) {
            allCharacters = serviceApi.getAllCharacters();
        } else {
            allCharacters = serviceApi.getCharactersWithPage(page);
        }
        model.addAttribute("characters", allCharacters.getResults());

        String prevUrl = allCharacters.getInfo().getPrev();
        String nextUrl = allCharacters.getInfo().getNext();

        if (prevUrl != null) {
            String prevPage = UriComponentsBuilder.fromUriString(prevUrl).build().getQueryParams().get("page").get(0);
            model.addAttribute("prevPage", prevPage);
        }

        if (nextUrl != null) {
            String nextPage = UriComponentsBuilder.fromUriString(nextUrl).build().getQueryParams().get("page").get(0);
            model.addAttribute("nextPage", nextPage);
        }

        return "characters";
    }

    /**
     * Возвращает страницу с подробной информацией о персонаже.
     * @param id Идентификатор персонажа
     * @param model Модель данных
     * @return страницу персонажа
     */
    @GetMapping("/{id}")
    public String getCharacterDetails(@PathVariable Integer id, Model model) {
        Result character = serviceApi.getCharacterById(id);
        model.addAttribute("character", character);

        int page = serviceApi.getPageNumberById(id);
        model.addAttribute("page", page);

        return "characters";
    }

    /**
     * Фильтрует персонажей с учетом выбранных параметров и возвращает страницу с результатами.
     * @param name Имя персонажа (опциональный параметр)
     * @param status Статус персонажа (опциональный параметр)
     * @param species Вид персонажа (опциональный параметр)
     * @param type Тип персонажа (опциональный параметр)
     * @param gender Пол персонажа (опциональный параметр)
     * @param model Модель данных
     * @return страницу с персонажами по выбранной фильтрацией
     */
    @GetMapping("/filter/result")
    public String filterCharacters(@RequestParam(name = "name", required = false) String name,
                                   @RequestParam(name = "status", required = false) String status,
                                   @RequestParam(name = "species", required = false) String species,
                                   @RequestParam(name = "type", required = false) String type,
                                   @RequestParam(name = "gender", required = false) String gender,
                                   Model model) {

        Characters filteredCharacters = serviceApi.getFilteredCharacters(name, status, species, type, gender);
        model.addAttribute("characters", filteredCharacters.getResults());

        return "characters";
    }

}
