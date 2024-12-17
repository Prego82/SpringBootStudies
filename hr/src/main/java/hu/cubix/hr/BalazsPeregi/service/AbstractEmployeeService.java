package hu.cubix.hr.BalazsPeregi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Employee save(Employee employee) {
		if (findById(employee.getId()) != null) {
			return em.merge(employee);
		} else {
			em.persist(employee);
			return employee;
		}
	}

	public List<Employee> findAll() {
		return em.createQuery("SELECT e FROM Employee e ORDER BY id", Employee.class).getResultList();
	}

	public Employee findById(long id) {
		return em.find(Employee.class, id);
	}

	@Transactional
	public void delete(long id) {
		em.remove(findById(id));
	}

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 0;
	}

}
