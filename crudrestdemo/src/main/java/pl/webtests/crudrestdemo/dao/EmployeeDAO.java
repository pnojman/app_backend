package pl.webtests.crudrestdemo.dao;

import java.util.List;

import pl.webtests.crudrestdemo.entity.Employee;

public interface EmployeeDAO {

	public List<Employee> findAll();

	public Employee findById(int theId);

	public void save(Employee theEmployee);

	public void deleteById(int theId);

	public Employee findByFirstAndLastNameAndEmail(Employee theEmployee);

}
