package com.example.employee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testEmployeeGettersAndSetters() {
        Employee employee = new Employee();
        employee.setId("1234");
        employee.setName("John Doe");
        employee.setAge(25);
        employee.setAddress("123 Main St");

        assertEquals("1234", employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals(25, employee.getAge());
        assertEquals("123 Main St", employee.getAddress());
    }

    @Test
    public void testEmployeeConstructor() {
        Employee employee = new Employee("5678", "Jane Roe", 30, "456 Elm St");

        assertEquals("5678", employee.getId());
        assertEquals("Jane Roe", employee.getName());
        assertEquals(30, employee.getAge());
        assertEquals("456 Elm St", employee.getAddress());
    }
}
