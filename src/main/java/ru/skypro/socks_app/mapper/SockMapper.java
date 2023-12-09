package ru.skypro.socks_app.mapper;

import ru.skypro.socks_app.model.Sock;
import ru.skypro.socks_app.dto.SockDTO;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SockMapper {
    @Mapping(target = "color", expression = "java(sockDTO.getColor().toLowerCase())")
    Sock toEntity(SockDTO sockDTO);

    SockDTO toDto(Sock sock);

    @Mapping(target = "id", ignore = true)
    void enrichSock(SockDTO sockDTO, @MappingTarget Sock sock);

}

