package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.CompanyForm;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.repository.CompanyFormRepository;
import hu.cubix.hr.BalazsPeregi.repository.CompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class InitDbService {
	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private CompanyFormRepository companyFormRepo;

	public void clearDb() {
		employeeRepo.deleteAll();
		companyRepo.deleteAll();
		companyFormRepo.deleteAll();
	}

	@Transactional
	public void insertTestData() {

		Employee littleJonny = employeeRepo
				.save(new Employee("Little Jonny", "Junior", 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		Employee middleJonny = employeeRepo
				.save(new Employee("Middle Jonny", "Mid", 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		Employee middleJonnyJr = employeeRepo
				.save(new Employee("Middle Jonny Jr.", "Mid2", 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		Employee seniorJonny = employeeRepo
				.save(new Employee("Senior Jonny", "Senior", 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		Employee oldJonny = employeeRepo
				.save(new Employee("Old Jonny", "Architect", 10000, LocalDateTime.of(2014, 1, 1, 0, 0)));
		Employee tesztElek = employeeRepo
				.save(new Employee("Teszt Elek", "Tester", 1000, LocalDateTime.of(2014, 1, 1, 0, 0)));
		Employee menedzsElek = employeeRepo
				.save(new Employee("Menedzs Elek", "Manager", 5000, LocalDateTime.of(2022, 11, 25, 0, 0)));

		CompanyForm limitedPartnership = companyFormRepo.save(new CompanyForm("Limited Partnership"));
		CompanyForm llc = companyFormRepo.save(new CompanyForm("LLC"));
		CompanyForm corporation = companyFormRepo.save(new CompanyForm("Corporation"));

		Company apple = companyRepo.save(new Company("123", "Apple", "USA", llc));
		Company ibm = companyRepo.save(new Company("456", "IBM", "India", corporation));
		Company intel = companyRepo.save(new Company("789", "Intel", "China", corporation));
		Company google = companyRepo.save(new Company("147", "Google", "Canada", corporation));
		Company amd = companyRepo.save(new Company("258", "AMD", "Brazil", limitedPartnership));

		apple.addEmployee(littleJonny);
		apple.addEmployee(tesztElek);
		ibm.addEmployee(middleJonny);
		intel.addEmployee(middleJonnyJr);
		google.addEmployee(seniorJonny);
		amd.addEmployee(oldJonny);
		amd.addEmployee(menedzsElek);
	}
}
