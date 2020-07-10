package org.chance.pattern.structural.composite.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-07 19:06:31
 */
public class Employee {

    private String name;
    private String dept;
    private int salary;


    private List<Employee> subordinates;

    /**
     * 构造函数
     *
     * @param name
     * @param dept
     * @param sal
     */
    public Employee(String name, String dept, int sal) {
        this.name = name;
        this.dept = dept;
        this.salary = sal;
        subordinates = new ArrayList<>();
    }

    public void add(Employee e) {
        subordinates.add(e);
    }

    public void remove(Employee e) {
        subordinates.remove(e);
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public String toString() {
        return ("Employee :[ Name : " + name
                + ", dept : " + dept + ", salary :"
                + salary + " ]");
    }

}
