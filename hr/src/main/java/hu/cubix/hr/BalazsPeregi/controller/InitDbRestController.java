package hu.cubix.hr.BalazsPeregi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.BalazsPeregi.dto.CompanyDto;
import hu.cubix.hr.BalazsPeregi.mapper.CompanyMapper;
import hu.cubix.hr.BalazsPeregi.model.Company;
import hu.cubix.hr.BalazsPeregi.service.CompanyService;
import hu.cubix.hr.BalazsPeregi.service.InitDbService;

@RestController
@RequestMapping("/api/init")
public class InitDbRestController {
	@Autowired
	private InitDbService init;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMapper mapper;

	@GetMapping("/clear")
	public void clearDb() {
		init.clearDb();
	}

	@GetMapping("/init")
	public List<CompanyDto> insertTestData() {
		init.insertTestData();
		List<Company> companies = companyService.findAll();
		return mapper.companiesToDtos(companies);
	}
}
