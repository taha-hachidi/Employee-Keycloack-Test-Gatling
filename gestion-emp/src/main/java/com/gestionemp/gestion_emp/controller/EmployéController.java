package com.gestionemp.gestion_emp.controller;

import com.gestionemp.gestion_emp.dto.EmployéDto;
import com.gestionemp.gestion_emp.service.IEmployéService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/employe")
public class EmployéController {

    @Autowired
    private IEmployéService service;


    @PreAuthorize("hasRole('client_admin')")
    @PostMapping
    public EmployéDto newEmployé(@RequestBody EmployéDto employéDto) {
        return service.newEmployé(employéDto);
    }


    @PreAuthorize("hasRole('ROLE_client_admin') or hasRole('ROLE_client_user')")
    @GetMapping("/get")
    public List<EmployéDto> getEmployé() {
        return service.getEmployé();
    }


    @PreAuthorize("hasRole('client_admin')")
    @GetMapping("/id/{id}")
    public EmployéDto getEmployéById(@PathVariable Long id) {
        return service.getEmployéById(id);
    }


    @PreAuthorize("hasRole('client_admin')")
    @PutMapping("/put/{id}")
    public EmployéDto updateEmployé(@RequestBody EmployéDto employéDto, @PathVariable Long id) {
        return service.updateEmployé(employéDto, id);
    }


    @PreAuthorize("hasRole('client_admin')")
    @DeleteMapping("/delete/{id}")
    public void deleteEmployé(@PathVariable Long id) {
        service.deleteEmployé(id);
    }

}
