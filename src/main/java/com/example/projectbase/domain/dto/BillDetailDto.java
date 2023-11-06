package com.example.projectbase.domain.dto;

import com.example.projectbase.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDetailDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int productId;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int billId;

    @Min(value = 1)
    private int quantity;
}
