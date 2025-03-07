package com.example.employee;

import java.io.FileWriter;
import java.io.IOException;

public class EmployeeService {
    private static final String FILE_NAME = "EMPLOYEE.DAT";

    public String addEmployee(Employee emp) throws IOException {
        String validationError = validateEmployee(emp);
        if (!validationError.isEmpty()) {
            return validationError;
        }
        writeEmployee(emp);
        return "Employee record added";
    }

    public String validateEmployee(Employee emp) {
        // Validação do ID: 4 dígitos numéricos
        if (emp.getId() == null || !emp.getId().matches("\\d{4}")) {
            return "Erro: ID deve conter 4 dígitos numéricos.";
        }
        // Validação do Nome: no máximo 20 caracteres
        if (emp.getName() == null || emp.getName().length() > 20) {
            return "Erro: Nome deve conter até 20 caracteres.";
        }
        // Validação da Idade: deve ser numérico com exatamente 2 dígitos (entre 10 e 99)
        if (emp.getAge() < 10 || emp.getAge() > 99) {
            return "Erro: Idade deve conter 2 dígitos numéricos.";
        }
        // Validação do Endereço: no máximo 30 caracteres
        if (emp.getAddress() == null || emp.getAddress().length() > 30) {
            return "Erro: Endereço deve conter até 30 caracteres.";
        }
        return "";
    }

    public void writeEmployee(Employee emp) throws IOException {
        // Abre o arquivo em modo append para preservar registros anteriores
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            // Formata os dados do funcionário em uma linha: id,name,age,address
            String record = emp.getId() + "," + emp.getName() + "," + emp.getAge() + "," + emp.getAddress() + "\n";
            fileWriter.write(record);
        }
    }
}
