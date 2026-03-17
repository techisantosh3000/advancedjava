package org.interview.model;

import java.util.Objects;

/**
 * Contains code for "Java8 in Action"
 * author of book Urma.
 */

// Model to be moved to separate package.
public class Employee implements Comparable {

    private int id;

    private String name;

    int age;

    String gender;

    String department;

    int yearOfJoining;

    double salary;

    public Employee(int id, String name, int age, String gender, String department, int yearOfJoining, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.yearOfJoining = yearOfJoining;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public int getYearOfJoining() {
        return yearOfJoining;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", Name : " + name + ", age : " + age + ", Gender : " + gender + ", Department : "
                + department + ", Year Of Joining : " + yearOfJoining + ", Salary : " + salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && age == employee.age && yearOfJoining == employee.yearOfJoining && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name) && Objects.equals(gender, employee.gender) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, department, yearOfJoining, salary);
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Employee) o).getName());
    }
}
