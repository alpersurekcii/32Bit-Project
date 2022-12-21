package com.alpersurekci.project.ExternalService.DAO.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Customer name not empty")
    private String customerName;
    @NotEmpty(message = "Customer surname not empty")
    private String customerSurname;
    @NotEmpty(message = "Customer email not empty")
    private String customerEmail;
}
