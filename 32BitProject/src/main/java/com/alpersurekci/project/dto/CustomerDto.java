package com.alpersurekci.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Müşteri dto'sunun bulunduğu sınıftır.
 * @author Alper Sürekçi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "User Api model documentation", description = "model")
public class CustomerDto {
    /**
     * müşteri id'si
     */
    @ApiModelProperty(value = "Unique id field of user object")
    private Long id;

    /**
     * Müşteri adı
     */
    @NotEmpty(message = "Customer name should not be empty")
    @ApiModelProperty(value="customerName field of user object")
    private String customerName;

    /**
     * Müşteri email'i
     */
    @NotEmpty(message = "Customer email should not be empty")
    @ApiModelProperty(value = "email field of user object")
    private String customerEmail;

    /**
     * Müşteri soyismi
     */
    @NotEmpty(message = "Customer surname should not be empty")
    @ApiModelProperty(value = "customerSurname field of user object")
    private String customerSurname;
}
