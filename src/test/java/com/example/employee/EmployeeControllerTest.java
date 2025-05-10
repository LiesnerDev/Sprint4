package com.example.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Test
    public void testAddEmployee_Success() throws Exception {
        Employee emp = new Employee("1234", "John", 25, "Main St");
        String json = (objectMapper != null) ? objectMapper.writeValueAsString(emp) :
            "{\"id\":\"1234\",\"name\":\"John\",\"age\":25,\"address\":\"Main St\"}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().string("Employee record added"));
    }

    @Test
    public void testAddEmployee_InvalidId() throws Exception {
        Employee emp = new Employee("12", "John", 25, "Main St");
        String json = (objectMapper != null) ? objectMapper.writeValueAsString(emp) :
            "{\"id\":\"12\",\"name\":\"John\",\"age\":25,\"address\":\"Main St\"}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Erro: ID deve conter 4 dígitos numéricos."));
    }

    @Test
    public void testAddEmployee_InvalidName() throws Exception {
        Employee emp = new Employee("1234", "NameExceedingTwentyCharacters", 25, "Main St");
        String json = (objectMapper != null) ? objectMapper.writeValueAsString(emp) :
            "{\"id\":\"1234\",\"name\":\"NameExceedingTwentyCharacters\",\"age\":25,\"address\":\"Main St\"}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Erro: Nome deve conter até 20 caracteres."));
    }

    @Test
    public void testAddEmployee_InvalidAge() throws Exception {
        Employee emp = new Employee("1234", "John", 9, "Main St");
        String json = (objectMapper != null) ? objectMapper.writeValueAsString(emp) :
            "{\"id\":\"1234\",\"name\":\"John\",\"age\":9,\"address\":\"Main St\"}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Erro: Idade deve conter 2 dígitos numéricos."));
    }

    @Test
    public void testAddEmployee_InvalidAddress() throws Exception {
        Employee emp = new Employee("1234", "John", 25, "This address is definitely more than thirty characters long");
        String json = (objectMapper != null) ? objectMapper.writeValueAsString(emp) :
            "{\"id\":\"1234\",\"name\":\"John\",\"age\":25,\"address\":\"This address is definitely more than thirty characters long\"}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Erro: Endereço deve conter até 30 caracteres."));
    }
}
