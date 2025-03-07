package com.example.employee;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private static final String FILE_NAME = "EMPLOYEE.DAT";

    @BeforeEach
    public void setUp() throws IOException {
        employeeService = new EmployeeService();
        // Delete the file if it exists to ensure a clean state
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up the file after each test
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @Test
    public void testValidateEmployee_ValidEmployee() {
        Employee emp = new Employee("1234", "John", 25, "Main St");
        String result = employeeService.validateEmployee(emp);
        assertEquals("", result);
    }

    @Test
    public void testValidateEmployee_InvalidId() {
        Employee emp = new Employee("12a4", "John", 25, "Main St");
        String result = employeeService.validateEmployee(emp);
        assertEquals("Erro: ID deve conter 4 dígitos numéricos.", result);

        emp.setId("123"); // Less than 4 digits
        result = employeeService.validateEmployee(emp);
        assertEquals("Erro: ID deve conter 4 dígitos numéricos.", result);
    }

    @Test
    public void testValidateEmployee_InvalidName() {
        Employee emp = new Employee("1234", "ThisNameIsOverTwentyCharactersLong", 25, "Main St");
        String result = employeeService.validateEmployee(emp);
        assertEquals("Erro: Nome deve conter até 20 caracteres.", result);
    }

    @Test
    public void testValidateEmployee_InvalidAge() {
        Employee emp = new Employee("1234", "John", 9, "Main St"); // Age less than 10
        String result = employeeService.validateEmployee(emp);
        assertEquals("Erro: Idade deve conter 2 dígitos numéricos.", result);

        emp.setAge(100); // Age more than 2 digits
        result = employeeService.validateEmployee(emp);
        assertEquals("Erro: Idade deve conter 2 dígitos numéricos.", result);
    }

    @Test
    public void testValidateEmployee_InvalidAddress() {
        Employee emp = new Employee("1234", "John", 25, "This Address is way over thirty characters long!");
        String result = employeeService.validateEmployee(emp);
        assertEquals("Erro: Endereço deve conter até 30 caracteres.", result);
    }

    @Test
    public void testAddEmployee_SuccessfulInsertion() throws IOException {
        Employee emp = new Employee("1234", "John", 25, "Main St");

        String result = employeeService.addEmployee(emp);
        assertEquals("Employee record added", result);

        // Verify file content
        File file = new File(FILE_NAME);
        assertTrue(file.exists(), "File should exist after insertion.");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            assertNotNull(line);
            String[] parts = line.split(",");
            assertEquals("1234", parts[0]);
            assertEquals("John", parts[1]);
            assertEquals("25", parts[2]);
            assertEquals("Main St", parts[3]);
        }
    }

    @Test
    public void testAddEmployee_PreservesExistingRecords() throws IOException {
        // Insert first employee record
        Employee emp1 = new Employee("1234", "John", 25, "Main St");
        employeeService.addEmployee(emp1);

        // Insert second employee record
        Employee emp2 = new Employee("5678", "Jane", 30, "Second St");
        String result = employeeService.addEmployee(emp2);
        assertEquals("Employee record added", result);

        // Verify the file contains both records
        File file = new File(FILE_NAME);
        assertTrue(file.exists());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line1 = br.readLine();
            String line2 = br.readLine();
            assertNotNull(line1);
            assertNotNull(line2);
            String[] parts1 = line1.split(",");
            String[] parts2 = line2.split(",");
            assertEquals("1234", parts1[0]);
            assertEquals("5678", parts2[0]);
        }
    }
}
