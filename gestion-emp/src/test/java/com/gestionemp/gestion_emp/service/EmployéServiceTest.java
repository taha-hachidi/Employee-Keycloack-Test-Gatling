package com.gestionemp.gestion_emp.service;

import static org.junit.jupiter.api.Assertions.*;
import com.gestionemp.gestion_emp.dto.EmployéDto;
import com.gestionemp.gestion_emp.model.Employé;
import com.gestionemp.gestion_emp.repository.EmployéRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmployéServiceTest {

    @Mock
    private EmployéRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployéService employéService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewEmployé() {

        EmployéDto employéDto = new EmployéDto(null, "John Doe", 30, "john.doe@example.com", 3000.0);
        Employé employé = new Employé(null, "John Doe", 30, "john.doe@example.com", 3000.0);

        when(modelMapper.map(employéDto, Employé.class)).thenReturn(employé);
        when(repository.save(employé)).thenReturn(employé);


        EmployéDto result = employéService.newEmployé(employéDto);


        assertEquals(employéDto, result);
        verify(repository, times(1)).save(employé);
    }

    @Test
    void testGetEmployé() {

        List<Employé> employés = new ArrayList<>();
        employés.add(new Employé(1L, "John Doe", 30, "john.doe@example.com", 3000.0));
        employés.add(new Employé(2L, "Jane Doe", 40, "jane.doe@example.com", 5000.0));

        when(repository.findAll()).thenReturn(employés);
        when(modelMapper.map(any(Employé.class), eq(EmployéDto.class)))
                .thenAnswer(invocation -> {
                    Employé employé = invocation.getArgument(0);
                    return new EmployéDto(employé.getId(), employé.getName(), employé.getAge(), employé.getEmail(), employé.getSalary());
                });

        // Act
        List<EmployéDto> result = employéService.getEmployé();


        System.out.println("Liste des employés récupérés :");
        for (EmployéDto employéDto : result) {
            System.out.println(employéDto);
        }


        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetEmployéById() {

        Employé employé = new Employé(1L, "John Doe", 30, "john.doe@example.com", 3000.0);
        when(repository.findById(1L)).thenReturn(Optional.of(employé));
        when(modelMapper.map(employé, EmployéDto.class)).thenReturn(new EmployéDto(1L, "John Doe", 30, "john.doe@example.com", 3000.0));


        EmployéDto result = employéService.getEmployéById(1L);


        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployé() {

        Employé employé = new Employé(1L, "John Doe", 30, "john.doe@example.com", 3000.0);
        EmployéDto employéDto = new EmployéDto(1L, "John Updated", 35, "john.updated@example.com", 4000.0);

        when(repository.findById(1L)).thenReturn(Optional.of(employé));
        when(repository.save(any(Employé.class))).thenReturn(employé);
        when(modelMapper.map(employéDto, Employé.class)).thenReturn(employé);


        EmployéDto result = employéService.updateEmployé(employéDto, 1L);


        assertEquals(employéDto.getName(), result.getName());
        verify(repository, times(1)).save(employé);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDeleteEmployé() {
        // Arrange
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // Act
        employéService.deleteEmployé(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}