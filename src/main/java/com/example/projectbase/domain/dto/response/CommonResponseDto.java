package com.example.projectbase.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommonResponseDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean status;

  private String message;

  public CommonResponseDto(String message){
    this.message = message;
  }
}
