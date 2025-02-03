package hu.cubix.hr.BalazsPeregi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.model.CompanyForm;
import hu.cubix.hr.BalazsPeregi.model.Employee;
import hu.cubix.hr.BalazsPeregi.model.Position;
import hu.cubix.hr.BalazsPeregi.model.PositionDetailsByCompany;
import hu.cubix.hr.BalazsPeregi.model.Qualification;
import hu.cubix.hr.BalazsPeregi.repository.CompanyFormRepository;
import hu.cubix.hr.BalazsPeregi.repository.CompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.EmployeeRepository;
import hu.cubix.hr.BalazsPeregi.repository.HolidayRepo;
import hu.cubix.hr.BalazsPeregi.repository.PositionDetailsByCompanyRepository;
import hu.cubix.hr.BalazsPeregi.repository.PositionRepository;
import jakarta.transaction.Transactional;

@Service
public class InitDbService {
	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private CompanyFormRepository companyFormRepo;
	@Autowired
	private PositionRepository positionRepo;
	@Autowired
	private PositionDetailsByCompanyRepository positionDetailsRepo;

	@Autowired
	private HolidayRepo holidayRepo;

	public void clearDb() {
		holidayRepo.deleteAll();
		positionDetailsRepo.deleteAll();
		employeeRepo.deleteAll();
		companyRepo.deleteAll();
		companyFormRepo.deleteAll();
		positionRepo.deleteAll();
	}

	@Transactional
	public void insertTestData() {

		Position junior = positionRepo.save(new Position("Junior", Qualification.HIGH_SCHOOL, 1000));
		Position mid = positionRepo.save(new Position("Mid", Qualification.COLLEGE, 2000));
		Position senior = positionRepo.save(new Position("Senior", Qualification.COLLEGE, 5000));
		Position architect = positionRepo.save(new Position("Architect", Qualification.UNIVERSITY, 10000));
		Position tester = positionRepo.save(new Position("Tester", Qualification.COLLEGE, 1000));
		Position manager = positionRepo.save(new Position("Manager", Qualification.PHD, 5000));
		Position ceo = positionRepo.save(new Position("CEO", Qualification.PHD, 5000));

		Employee littleJonny = employeeRepo
				.save(new Employee("Little Jonny", junior, 1000, LocalDateTime.of(2022, 11, 19, 0, 0)));
		Employee middleJonny = employeeRepo
				.save(new Employee("Middle Jonny", mid, 2000, LocalDateTime.of(2022, 5, 19, 0, 0)));
		Employee middleJonnyJr = employeeRepo
				.save(new Employee("Middle Jonny Jr.", mid, 2000, LocalDateTime.of(2022, 1, 1, 0, 0)));
		Employee seniorJonny = employeeRepo
				.save(new Employee("Senior Jonny", senior, 5000, LocalDateTime.of(2019, 1, 1, 0, 0)));
		Employee oldJonny = employeeRepo
				.save(new Employee("Old Jonny", architect, 1000, LocalDateTime.of(2014, 1, 1, 0, 0)));
		Employee tesztElek = employeeRepo
				.save(new Employee("Teszt Elek", tester, 1000, LocalDateTime.of(2014, 1, 1, 0, 0)));
		Employee menedzsElek = employeeRepo
				.save(new Employee("Menedzs Elek", manager, 5000, LocalDateTime.of(2022, 11, 25, 0, 0)));

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

		PositionDetailsByCompany appleJunior = positionDetailsRepo
				.save(new PositionDetailsByCompany(2500, apple, junior));
		PositionDetailsByCompany testerIntel = positionDetailsRepo
				.save(new PositionDetailsByCompany(2000, intel, tester));
		PositionDetailsByCompany googleManager = positionDetailsRepo
				.save(new PositionDetailsByCompany(10000, google, manager));
	}
}
