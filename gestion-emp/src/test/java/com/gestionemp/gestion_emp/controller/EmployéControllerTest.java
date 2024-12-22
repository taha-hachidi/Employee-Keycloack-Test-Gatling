package com.gestionemp.gestion_emp.controller;

import com.gestionemp.gestion_emp.dto.EmployéDto;
import com.gestionemp.gestion_emp.service.IEmployéService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployéControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEmployéService employéService;

    @InjectMocks
    private EmployéController employéController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employéController).build();
    }

    @Test
    void testGetEmployé() throws Exception {
        // Arrange
        EmployéDto employé1 = new EmployéDto(1L, "John Doe", 30, "john.doe@example.com", 3000.0);
        EmployéDto employé2 = new EmployéDto(2L, "Jane Doe", 40, "jane.doe@example.com", 5000.0);
        List<EmployéDto> employéList = Arrays.asList(employé1, employé2);

        when(employéService.getEmployé()).thenReturn(employéList);

        // Act & Assert
        mockMvc.perform(get("/employe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].salary").value(3000.0))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(40))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$[1].salary").value(5000.0));

        verify(employéService, times(1)).getEmployé();
    }

    @Test
    void testNewEmployé() throws Exception {
        // Arrange
        EmployéDto newEmployé = new EmployéDto(null, "Alice", 28, "alice@example.com", 4000.0);
        EmployéDto savedEmployé = new EmployéDto(3L, "Alice", 28, "alice@example.com", 4000.0);

        when(employéService.newEmployé(any(EmployéDto.class))).thenReturn(savedEmployé);

        // Act & Assert
        mockMvc.perform(post("/employe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Alice\", \"age\": 28, \"email\": \"alice@example.com\", \"salary\": 4000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.salary").value(4000.0));

        verify(employéService, times(1)).newEmployé(any(EmployéDto.class));
    }
}