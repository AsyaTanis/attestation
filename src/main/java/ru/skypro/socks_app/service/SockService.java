package ru.skypro.socks_app.service;

import ru.skypro.socks_app.dto.SockDTO;

/**
 * Интерфейс, определяющий методы бизнес-логики приложения
 */
public interface SockService {

    /**
     * Создание и изменение партии носков в базе данных
     *
     * @param sockDTO - сущность получаямая с фронта
     */
    void addSock(SockDTO sockDTO);

    /**
     * Уменьшение партии носков
     * @param sockDTO - сущность получаямая с фронта
     */
    void deleteSock(SockDTO sockDTO);

    /**
     * Получение количества носков
     * @param color - сущность получаямая с фронта
     * @param operation - сущность получаямая с фронта
     * @param cottonPart - сущность получаямая с фронта
     * @return - количество носков
     */
    Integer getQuantity(String color, String operation, Integer cottonPart);
}