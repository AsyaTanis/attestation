package ru.skypro.socks_app.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import ru.skypro.socks_app.dto.SockDTO;
import ru.skypro.socks_app.service.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "SocksController", description = "Приход и расход носков на складе")
public class SockController {
    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(summary = "Регистрация прихода носков")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Сохраняет партию носков в базу данных"),
                    @ApiResponse(responseCode = "400", description = "Невалидное поле!"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны!"),
            }
    )
    @PostMapping("/api/socks/income")
    private void income(@RequestBody @Valid SockDTO sockDTO ) {
        sockService.addSock(sockDTO);

    }

    @Operation(summary = "Регистрация отпуска носков")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Уменьшает количество носков в базе данных"),
                    @ApiResponse(responseCode = "400", description = "Невалидное поле!"),
                    @ApiResponse(responseCode = "404", description = "Носки с такими параметрами не найдены!"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны!"),
            }
    )
    @PostMapping("/api/socks/outcome")
    private void outcome(@RequestBody  @Valid SockDTO sockDTO) {
        sockService.deleteSock(sockDTO);
    }


    @Operation(summary = "Получение общего колличества носков по указанным параметрам")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Возвращяет общее колличество носков по указанным параметрам"),
                    @ApiResponse(responseCode = "400", description = "Невалидное поле!"),
                    @ApiResponse(responseCode = "404", description = "Носки с такими параметрами не найдены!"),
                    @ApiResponse(responseCode = "409", description = "Операция не найдена!"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны!"),

            }
    )
    @GetMapping
    private ResponseEntity<Integer> getQuantity(@Parameter(description = "Введите цвет", example = "red")
                               @RequestParam @NotBlank  String color,
                                                @Parameter(description = "Введите значение количества хлопка в составе носков " +
                                                        "moreThan, lessThan, equal", example = "equal")
                               @RequestParam @NotBlank String operation,
                                                @Parameter(description = "Введите значение процента хлопка в составе носок", example = "50")
                               @RequestParam @NotNull @Min(1) @Max(100) int cottonPart) {
        return ResponseEntity.ok().body(sockService.getQuantity(color, operation, cottonPart));
    }

}