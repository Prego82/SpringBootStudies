package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.CompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;

@Service
public class InitDbService {
	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private EmployeeRepository employeeRepo;

	public void clearDb() {
		companyRepo.deleteAll();
		employeeRepo.deleteAll();
	}

	public void insertTestData() {

		Company apple = new Company("123", "Apple", "USA");
		Company ibm = new Company("456", "IBM", "India");
		Company intel = new Company("789", "Intel", "China");
		Company google = new Company("147", "Google", "Canada");
		Company amd = new Company("258", "AMD", "Brazil");

		companyRepo.save(apple);
		companyRepo.save(ibm);
		companyRepo.save(intel);
		companyRepo.save(google);
		companyRepo.save(amd);

		employeeRepo.save(new Employee("Little Jonny", "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0), apple));
		employeeRepo.save(new Employee("Middle Jonny", "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0), ibm));
		employeeRepo.save(new Employee("Middle Jonny Jr.", "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0), intel));
		employeeRepo.save(new Employee("Senio Jonny", "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0), google));
		employeeRepo.save(new Employee("Old Jonny", "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0), amd));
		employeeRepo.save(new Employee("Teszt Elek", "Tester", 1000, LocalDateTime.of(2014, 1, 1, 0, 0), apple));
		employeeRepo.save(new Employee("Menedzs Elek", "Manager", 5000, LocalDateTime.of(2022, 11, 25, 0, 0), amd));

	}
}
