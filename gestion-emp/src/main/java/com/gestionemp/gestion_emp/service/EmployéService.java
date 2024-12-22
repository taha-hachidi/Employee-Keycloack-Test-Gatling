package com.gestionemp.gestion_emp.service;

import com.gestionemp.gestion_emp.dto.EmployéDto;
import com.gestionemp.gestion_emp.model.Employé;
import com.gestionemp.gestion_emp.repository.EmployéRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployéService implements IEmployéService{


    @Autowired
    private EmployéRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public EmployéDto newEmployé(EmployéDto employéDto) {
        Employé employé = new Employé();
        employé = modelMapper.map(employéDto, Employé.class);
        repository.save(employé);
        return employéDto;
    }

    @Override
    public List<EmployéDto> getEmployé() {
        List<EmployéDto> employéDtos = new ArrayList<>();
        List<Employé> employés = repository.findAll();
        for (Employé employé : employés){
            EmployéDto employéDto = modelMapper.map(employé, EmployéDto.class);
            employéDtos.add(employéDto);
        }
        return employéDtos;
    }

    @Override
    public EmployéDto getEmployéById(Long id) {
        Optional<Employé> employé = repository.findById(id);
        EmployéDto employéDto = modelMapper.map(employé, EmployéDto.class);
        return employéDto;
    }

    @Override
    public EmployéDto updateEmployé(EmployéDto employéDto, Long id) {
        Employé employé = new Employé();
        employé = modelMapper.map(employéDto, Employé.class);
        Employé finalEmployé = employé;
        repository.findById(id).map(ow ->{
            ow.setName(finalEmployé.getName());
            ow.setEmail(finalEmployé.getEmail());
            ow.setAge(finalEmployé.getAge());
            ow.setSalary(finalEmployé.getSalary());


            return repository.save(ow);
        } );
        return employéDto;
    }

    @Override
    public void deleteEmployé(Long id) {
        repository.deleteById(id);
    }
}
