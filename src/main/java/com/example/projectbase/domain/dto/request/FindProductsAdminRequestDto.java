package com.example.projectbase.domain.dto.request;

import com.example.projectbase.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductsAdminRequestDto {

    private String name;

    private String categoryName;

    private int startQuantity= CommonConstant.ZERO_INT_VALUE;

    private int endQuantity=CommonConstant.HUNDRED_INT_VALUE;

    private int startSelled=CommonConstant.HUNDRED_INT_VALUE;

    private int endSelled=CommonConstant.HUNDRED_INT_VALUE;




}
