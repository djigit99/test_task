package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        final List<Employee> employeesList = new ArrayList<>();
        Iterable<Employee> employees = employeeDao.findAll();

        employees.forEach(employeesList::add);

        Optional<Employee> employee = employeesList.stream()
                .filter((emp -> emp.getName().equals(name)))
                .findFirst();

        if (employee.isPresent()) {
            final long employeeId = employee.get().getId();
            employeesList.remove(employee.get());
            employees = employeesList;
            employeeDao.deleteById(employeeId);
            employeeDao.saveAll(employees);

            return employeeId;
        } else {
            throw new IllegalArgumentException("Can't fire employee. Check employee's name.");
        }
    }

    @Override
    public Long changeSalary(String name) {
        final List<Employee> employeesList = new ArrayList<>();
        Iterable<Employee> employees = employeeDao.findAll();

        employees.forEach(employeesList::add);

        Optional<Employee> employee = employeesList.stream()
                .filter((emp -> emp.getName().equals(name)))
                .findFirst();

        if (employee.isPresent()) {
            final long employeeId = employee.get().getId();
            BigDecimal oldSalary = employee.get().getSalary();
            employee.get().setSalary(oldSalary.add(BigDecimal.valueOf(new Random().nextDouble() * 100)));
            employeeDao.saveAll(employees);

            return employeeId;
        } else {
            throw new IllegalArgumentException("Can't change employee's salary. Check employee's name.");
        }
    }

    @Override
    public Long hireEmployee(Employee employee) {
        return employeeDao.save(employee).getId();
    }
}
