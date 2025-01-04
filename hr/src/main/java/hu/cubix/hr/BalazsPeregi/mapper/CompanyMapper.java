package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.cubix.hr.BalazsPeregi.dto.CompanyDto;
import hu.cubix.hr.BalazsPeregi.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	public CompanyDto companyToDto(Company company);

	@Mapping(target = "employees", ignore = true)
	@Named("summary")
	public CompanyDto companyToSummaryDto(Company company);

	public List<CompanyDto> comapniesToDtos(List<Company> companies);

	@IterableMapping(qualifiedByName = "summary")
	public List<CompanyDto> comapniesToSummaryDtos(List<Company> companies);

	public Company dtoToCompany(CompanyDto company);

}
