package com.alpersurekci.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kullanıcı dto'sunun olduğu sınıftır.
 * @author Alper Sürekçi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userID;

    private String userName;

    private String password;
}
