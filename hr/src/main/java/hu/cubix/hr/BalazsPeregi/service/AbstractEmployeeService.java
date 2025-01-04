package hu.cubix.hr.BalazsPeregi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {

//	@PersistenceContext
//	private EntityManager em;

	@Autowired
	private EmployeeRepository repo;

	@Transactional
	public Employee save(Employee employee) {
//		if (findById(employee.getId()) != null) {
//			return em.merge(employee);
//		} else {
//			em.persist(employee);
//			return employee;
//		}
		return repo.save(employee);
	}

	public List<Employee> findAll() {
//		return em.createQuery("SELECT e FROM Employee e ORDER BY id", Employee.class).getResultList();
		return repo.findAll();
	}

	public Employee findById(long id) {
//		return em.find(Employee.class, id);
		return repo.findById(id).orElse(null);
	}

	@Transactional
	public void delete(long id) {
//		em.remove(findById(id));
		repo.deleteById(id);
	}

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 0;
	}

}
