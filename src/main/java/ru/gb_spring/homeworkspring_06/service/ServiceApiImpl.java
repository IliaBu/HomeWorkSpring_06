package ru.gb_spring.homeworkspring_06.service;

import ru.gb_spring.homeworkspring_06.domain.Characters;
import ru.gb_spring.homeworkspring_06.domain.Result;
import ru.gb_spring.homeworkspring_06.exception.CharacterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Реализация интерфейса ServiceApi для работы с API сервисом "Rick and Morty".
 */
@Service
public class ServiceApiImpl implements ServiceApi {

    @Autowired
    private RestTemplate template;

    @Autowired
    private HttpHeaders headers;

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${pagination.perPage}")
    private int paginationPerPage;

    /**
     * Получает все персонажи.
     *
     * @return коллекцию персонажей
     */
    @Override
    public Characters getAllCharacters() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Characters> response = template.exchange(apiUrl, HttpMethod.GET, entity, Characters.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CharacterNotFoundException("Characters not found with");
            } else {
                throw new RuntimeException("Unexpected error occurred");
            }
        } catch (HttpClientErrorException e) {
            throw new CharacterNotFoundException("Characters not found!");
        }
    }

    /**
     * Получает информацию о персонаже по его идентификатору.
     *
     * @param id Идентификатор персонажа
     * @return информацию о персонаже
     */
    @Override
    public Result getCharacterById(Integer id) {
        String urlPathId = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .pathSegment(id.toString())
                .toUriString();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Result> response = template.exchange(urlPathId, HttpMethod.GET, entity, Result.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CharacterNotFoundException("Персонаж не найден по идентификатору: " + id);
            } else {
                throw new RuntimeException("Произошла непредвиденная ошибка");
            }
        } catch (HttpClientErrorException e) {
            throw new CharacterNotFoundException("Персонаж не найден по идентификатору: " + id);
        }
    }

    /**
     * Получает коллекцию персонажей с определенной страницы.
     *
     * @param page Номер страницы
     * @return коллекцию персонажей с указанной страницы
     */
    @Override
    public Characters getCharactersWithPage(String page) {
        String charactersUrlWithPage = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("page", page)
                .toUriString();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Characters> response = template.exchange(charactersUrlWithPage, HttpMethod.GET, entity, Characters.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CharacterNotFoundException("Символы, не найденные на странице: " + page);
            } else {
                throw new RuntimeException("Произошла непредвиденная ошибка");
            }
        } catch (HttpClientErrorException e) {
            throw new CharacterNotFoundException("Символы, не найденные на странице: " + page);
        }
    }

    /**
     * Получает номер страницы, на которой находится персонаж с указанным идентификатором.
     *
     * @param id Идентификатор персонажа
     * @return номер страницы, на которой находится персонаж
     */
    public int getPageNumberById(int id) {
        return (id - 1) / paginationPerPage + 1;
    }

    /**
     * Получает отфильтрованную коллекцию персонажей.
     *
     * @param name    Имя персонажа (необязательный параметр)
     * @param status  Статус персонажа (необязательный параметр)
     * @param species Вид персонажа (необязательный параметр)
     * @param type    Тип персонажа (необязательный параметр)
     * @param gender  Пол персонажа (необязательный параметр)
     * @return отфильтрованную коллекцию персонажей
     */
    @Override
    public Characters getFilteredCharacters(String name, String status, String species, String type, String gender) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        if (StringUtils.hasText(name)) {
            builder.queryParam("name", name);
        }
        if (StringUtils.hasText(status)) {
            builder.queryParam("status", status);
        }
        if (StringUtils.hasText(species)) {
            builder.queryParam("species", species);
        }
        if (StringUtils.hasText(type)) {
            builder.queryParam("type", type);
        }
        if (StringUtils.hasText(gender)) {
            builder.queryParam("gender", gender);
        }

        String filteredCharactersUrl = builder.toUriString();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Characters> response = template.exchange(filteredCharactersUrl, HttpMethod.GET, entity, Characters.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CharacterNotFoundException("Символы, не найденные с помощью параметра - " + "имя: " + name + ", статус: " + status +
                        ", вид: " + species + ", тип: " + type + ", пол: " + gender);
            } else {
                throw new RuntimeException("Произошла непредвиденная ошибка");
            }
        } catch (HttpClientErrorException e) {
            throw new CharacterNotFoundException("Символы, не найденные с помощью параметра - " + "имя: " + name + ", статус: " + status +
                    ", вид: " + species + ", тип: " + type + ", пол: " + gender);
        }
    }
}
