package ru.simplelib.library.controller.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoContentResponseDto {
    private Boolean success;
    private String error;
}
