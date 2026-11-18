package com.example.EmployeeManagement.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeManagement.model.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    // In-memory collection
    private List<Employee> employees = new ArrayList<>();

    // Initial sample data
    public EmployeeController() {

        employees.add(new Employee(
            101, "Rahul", "IT", 50000
        ));

        employees.add(new Employee(
            102, "Amit", "HR", 40000
        ));

        employees.add(new Employee(
            103, "Priya", "Finance", 60000
        ));
    }

    // (B) ADD EMPLOYEE - POST

    @PostMapping
    public ResponseEntity<?> addEmployee(
            @RequestBody Employee employee) {

        for (Employee e : employees) {

            if (e.getId() == employee.getId()) {

                return ResponseEntity.badRequest()
                    .body("Employee already exists");
            }
        }

        employees.add(employee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employee);
    }

    // (C) GET ALL EMPLOYEES

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employees;
    }

    // (C) GET EMPLOYEE BY ID

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(
            @PathVariable int id) {

        for (Employee e : employees) {

            if (e.getId() == id) {
                return ResponseEntity.ok(e);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee not found");
    }

    // (D) UPDATE EMPLOYEE - PUT

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmployee) {

        for (Employee e : employees) {

            if (e.getId() == id) {

                e.setName(updatedEmployee.getName());

                e.setDepartment(
                    updatedEmployee.getDepartment()
                );

                e.setSalary(updatedEmployee.getSalary());

                return ResponseEntity.ok(e);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee not found");
    }

    // (E) DELETE EMPLOYEE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable int id) {

        Iterator<Employee> iterator = employees.iterator();

        while (iterator.hasNext()) {

            Employee e = iterator.next();

            if (e.getId() == id) {

                iterator.remove();

                return ResponseEntity.ok(
                    "Employee deleted successfully"
                );
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee not found");
    }
}

