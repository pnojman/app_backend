package pl.webtests.crudrestdemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.webtests.crudrestdemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

	private EntityManager entityManager;

	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager theEndityManager) {
		entityManager = theEndityManager;
	}

	@Override
	public List<Employee> findAll() {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

		List<Employee> employees = theQuery.getResultList();

		return employees;
	}

	@Override
	public Employee findById(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Employee theEmployee = currentSession.get(Employee.class, theId);

		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theEmployee);

	}

	@Override
	public void deleteById(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");

		theQuery.setParameter("employeeId", theId);
		theQuery.executeUpdate();

	}

	@Override
	public Employee findByFirstAndLastNameAndEmail(Employee theEmployee) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Employee> theQuery = currentSession
				.createQuery(
						"from Employee where first_name = '" + theEmployee.getFirstName() + "' and last_name = '"
								+ theEmployee.getLastName() + "' and email = '" + theEmployee.getEmail() + "'",
						Employee.class);

		return theQuery.getSingleResult();

	}

}
