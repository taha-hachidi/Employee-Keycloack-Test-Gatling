package com.gestionemp.gestion_emp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployéIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateAndGetEmployé() throws Exception {

        mockMvc.perform(post("/employe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Bob\", \"age\": 30, \"email\": \"bob@example.com\", \"salary\": 4500.0}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].email").value("bob@example.com"))
                .andExpect(jsonPath("$[0].salary").value(4500.0));
    }
}
