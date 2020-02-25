package com.boatfly.java8.filter;

import com.boatfly.java8.bean.Employee;

public class EmployeeAgeFilter implements MyFilter<Employee> {
    @Override
    public boolean filter(Employee employee) {
        return employee.getAge() > 35;
    }
}
