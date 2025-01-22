package hu.cubix.hr.BalazsPeregi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.cubix.hr.BalazsPeregi.model.PositionDetailsByCompany;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {
	PositionDetailsByCompany findByPositionNameAndCompanyId(String positionName, long companyId);
}
