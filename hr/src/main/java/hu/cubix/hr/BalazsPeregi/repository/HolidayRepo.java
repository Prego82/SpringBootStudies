package hu.cubix.hr.BalazsPeregi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.cubix.hr.BalazsPeregi.model.HolidayRequest;

public interface HolidayRepo extends JpaRepository<HolidayRequest, Long>, JpaSpecificationExecutor<HolidayRequest> {

}
