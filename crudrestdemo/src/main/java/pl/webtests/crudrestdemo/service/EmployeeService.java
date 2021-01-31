package pl.webtests.crudrestdemo.service;

import java.util.List;

import pl.webtests.crudrestdemo.entity.Employee;

public interface EmployeeService {
	
public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

}
