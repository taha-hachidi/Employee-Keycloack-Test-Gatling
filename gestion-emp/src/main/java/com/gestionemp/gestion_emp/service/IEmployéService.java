package com.gestionemp.gestion_emp.service;

import com.gestionemp.gestion_emp.dto.EmployéDto;

import java.util.List;

public interface IEmployéService {

    EmployéDto newEmployé(EmployéDto employéDto);
    List<EmployéDto> getEmployé();
    EmployéDto getEmployéById(Long id);
    EmployéDto updateEmployé(EmployéDto employéDto, Long id);
    void deleteEmployé(Long id);

}
