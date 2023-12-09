package ru.skypro.socks_app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.skypro.socks_app.dto.SockDTO;
import ru.skypro.socks_app.mapper.SockMapper;
import ru.skypro.socks_app.model.Sock;
import org.springframework.stereotype.Service;
import ru.skypro.socks_app.exception.LessThanZeroException;
import ru.skypro.socks_app.exception.OperationException;
import ru.skypro.socks_app.exception.SockNotFoundException;
import ru.skypro.socks_app.repository.SockRepository;
import ru.skypro.socks_app.service.SockService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {

    private final SockRepository sockRepository;
    private final SockMapper sockMapper;

    /**
     * Метод для приема носков на склад
     *
     * @param sockDTO обьект для хранания параметров приема носков
     */
@Override
@Transactional
    public void addSock(SockDTO sockDTO) {
        log.info("Сохраняет партию носков в базу данных");
    Optional <Sock> sockOrEmpty = sockRepository.findByColorAndCottonPart(sockDTO.getColor().toLowerCase(), sockDTO.getCottonPart());
    if (sockOrEmpty.isEmpty()){
        sockRepository.save(sockMapper.toEntity(sockDTO));
        log.info("{} Сохранен в базе данных", sockMapper.toEntity(sockDTO));
           } else {
        Sock sock = sockOrEmpty.get();
        sock.setQuantity(sock.getQuantity()+sockDTO.getQuantity());
        sockRepository.save(sock);
        log.info("{} Изменен в базе данных", sock);
           }
      }


    /**
     * Метод для отправки носков со склада
     * @param sockDTO обьект для хранания параметров отправки носков
     */

    @Override
    @Transactional
    public void deleteSock(SockDTO sockDTO) {
        log.info("Уменьшает количество носков в базе данных");
        Sock sock = sockRepository.findByColorAndCottonPart(sockDTO.getColor().toLowerCase(), sockDTO.getCottonPart()).orElseThrow(() -> new SockNotFoundException());
        int quantity = sock.getQuantity() - sockDTO.getQuantity();
        if (quantity < 0) {
            log.debug("На складе нет такого колличества носков");
            throw new LessThanZeroException();
        } else if (quantity == 0) {
            sockRepository.deleteById(sock.getId());
            log.info("{} Удален из базы данных", sock);
        } else {
            sock.setQuantity(quantity);
            sockRepository.save(sock);
            log.info("{} Изменен в базе данных", sock);
        }
    }



    /**
     * Метод находит общее колличество носков на складе
     *
     * @param color      цвет носков
     * @param operation  операция сравнения носков
     * @param cottonPart процент хлопка в составе носков
     * @return возращает обьект типа Integer
     */

    @Override
    @Transactional
    public Integer getQuantity(String color, String operation, Integer cottonPart) {
        log.info("Возвращяет общее колличество носков по указанным параметрам");
       if (sockRepository.findByColor(color).isEmpty()) {
           throw new SockNotFoundException();
       }
        switch (operation) {
            case "moreThan" -> {
                Integer count = sockRepository.findByCottonPartMoreThan(color.toLowerCase(), cottonPart);
                    return checkingSockCount(count);
                }
            case "lessThan" -> {
                Integer count = sockRepository.findByCottonPartLessThan(color.toLowerCase(), cottonPart);
                    return checkingSockCount(count);
                }
            case "equal" -> {
                Integer count = sockRepository.findByCottonPartEquals(color.toLowerCase(), cottonPart);
                    return checkingSockCount(count);
                }
            default -> throw new OperationException();
        }
    }

    /**
     * Метод проверки носков на складе
     *
     * @param count колличество носков
     * @return возвращает обьект типа Integer
     */
    private Integer checkingSockCount(Integer count) {
        if (count != null)
            return count;
        else {
            log.debug("На складе нет носков");
            throw new SockNotFoundException();
        }
    }
}


