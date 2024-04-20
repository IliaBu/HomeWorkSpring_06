package ru.gb_spring.homeworkspring_06.exception;

/**
 * Исключение, возникающее при отсутствии информации о персонаже.
 */
public class CharacterNotFoundException extends RuntimeException{
    /**
     * Конструктор класса CharacterNotFoundException.
     * @param message Сообщение об исключении
     */
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
