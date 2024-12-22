package com.gestionemp.gestion_emp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployéDto {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Double salary;
}
