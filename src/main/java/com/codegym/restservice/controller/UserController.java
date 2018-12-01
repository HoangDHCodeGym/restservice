package com.codegym.restservice.controller;

import com.codegym.restservice.EmployeeNotFoundException;
import com.codegym.restservice.model.Employee;
import com.codegym.restservice.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
  private final EmployeeRepository repository;
  UserController(EmployeeRepository repository) {
      this.repository = repository;
  }

  @GetMapping("/employees")
  List<Employee> all() {
      return repository.findAll();
  }

  @PostMapping("/employees")
  Employee newEmployee(@RequestBody Employee newEmployee) {
      return repository.save(newEmployee);
  }

  @GetMapping("/employees/{id}")
  Employee one(@PathVariable Long id) {
      return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
      return repository.findById(id).map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repository.save(employee);
      }).orElseGet(() -> {
          newEmployee.setId(id);
          return repository.save(newEmployee);
      });
  }

  @DeleteMapping("/employees/{id}")
  void deleteEmployee(@PathVariable Long id) {
      repository.deleteById(id);
  }
}
