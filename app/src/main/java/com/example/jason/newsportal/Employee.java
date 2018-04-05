package com.example.jason.newsportal;

/**
 * Created by jason on 20/08/2017.
 */

public class Employee {

    private String employeeName;
    private String employeeNumber;
    private String employeeTitle;

    public Employee(String employeeName, String employeeNumber, String employeeTitle) {
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        this.employeeTitle = employeeTitle;
    }

    public static final Employee[] employees = {
            new Employee("Jason", "7340", "Developer"),
            new Employee("Mob", "7341", "Android Dev"),
            new Employee("Mark", "7342", "Sales")
    };

    public static String[] getEmployeeNames(){
        String [] employeeNames = new String[Employee.employees.length];
        for(int i=0; i<Employee.employees.length; i++){
            employeeNames[i] = employees[i].getEmployeeName();
        }
        return employeeNames;
    }

    @Override
    public String toString(){
        return getEmployeeNumber()+" "+getEmployeeName();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmployeeTitle() {
        return employeeTitle;
    }
}