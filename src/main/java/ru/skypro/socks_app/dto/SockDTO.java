package ru.skypro.socks_app.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Сущность  для прихода и расхода носков
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Schema(description = "Сущность для носков")
public class SockDTO {

    /**
     * Цвет носков
     */

    @Schema(name = "color", description = "Цвет", maxLength = 20, example = "белый")
    @NotBlank(message = "Color не должно быть null")
    private String color;

    /**
     * Процент хлопка в составе носков
     */

    @Schema(name = "cotton_part", description = "Процент хлопка", minimum = "1", maximum = "100", example = "80")
    @NotNull(message = "Cotton part не должно быть null")
    @Positive(message = "Минимальное число должно быть не меньше 0")
   // @Max(value = 100, message = "Максимальное число должно быть не больше 100")
    private int cottonPart;

    /**
     * Количество носков
     */

    @Schema(name = "quantity", description = "Количество", minimum = "1", example = "120")
    @NotNull(message = "Quantity не должно быть null")
    @Positive(message = "Минимальное целое число должно быть не меньше 0")
    //@Max(value = Integer.MAX_VALUE, message = "Максимальное число должно быть не больше Integer.MAX_VALUE")
    private int quantity;

}




