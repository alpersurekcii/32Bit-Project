package com.alpersurekci.project.Model;

import com.alpersurekci.project.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnModel {

    private boolean successful;

    private String code;

    private String message;

    private List<CustomerDto> result;

}
