package hu.cubix.hr.BalazsPeregi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.cubix.hr.BalazsPeregi.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
