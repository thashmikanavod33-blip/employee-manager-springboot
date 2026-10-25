package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String viewHomePage(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("listEmployees", employeeService.listAll(keyword));
        model.addAttribute("employee", new Employee());
        model.addAttribute("keyword", keyword);
        return "employees";
    }

    @PostMapping("/employees")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listEmployees", employeeService.listAll(null));
            return "employees";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/{id}/edit")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/employees/{id}/update")
    public String updateEmployee(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-employee";
        }
        employee.setId(id);
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}