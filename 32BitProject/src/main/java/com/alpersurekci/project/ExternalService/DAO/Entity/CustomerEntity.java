package com.alpersurekci.project.ExternalService.DAO.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Müşteri entitysinin bulunduğu sınıftır.
 * @author Alper Sürekçi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="customers")
public class CustomerEntity {
    /**
     * Müşteri id'si
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Müşteri ismi
     */
    private String customerName;
    /**
     * Müşteri soyismi
     */
    private String customerSurname;
    /**
     * Müşteri emaili
     */
    private String customerEmail;
}
