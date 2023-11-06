package com.example.projectbase.domain.dto;

import com.example.projectbase.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String nameCustomer;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:\\+84|0)(?:1[2689]|9[0-9]|3[2-9]|5[6-9]|7[0-9])(?:\\d{7}|\\d{8})$")
    private String phonenumber;

    private int feeShip;

    private String address;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String status;

    private int payment;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int customerId;


}
