package hu.cubix.hr.BalazsPeregi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.cubix.hr.BalazsPeregi.model.CompanyForm;

public interface CompanyFormRepository extends JpaRepository<CompanyForm, Long> {
	CompanyForm findByName(String name);
}
