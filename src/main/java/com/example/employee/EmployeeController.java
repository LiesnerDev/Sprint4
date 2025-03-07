package com.example.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService = new EmployeeService();

    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee emp) {
        try {
            String validationResult = employeeService.validateEmployee(emp);
            if (!validationResult.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
            }

            employeeService.writeEmployee(emp);
            return ResponseEntity.status(HttpStatus.OK).body("Employee record added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }
}
