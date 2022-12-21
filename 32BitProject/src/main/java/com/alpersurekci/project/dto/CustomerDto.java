package com.alpersurekci.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "User Api model documentation", description = "model")
public class CustomerDto {

    @ApiModelProperty(value = "Unique id field of user object")
    private Long id;

    @NotEmpty(message = "Customer name should not be empty")
    @ApiModelProperty(value="customerName field of user object")
    private String customerName;

    @NotEmpty(message = "Customer email should not be empty")
    @ApiModelProperty(value = "email field of user object")
    private String customerEmail;

    @NotEmpty(message = "Customer surname should not be empty")
    @ApiModelProperty(value = "customerSurname field of user object")
    private String customerSurname;
}
