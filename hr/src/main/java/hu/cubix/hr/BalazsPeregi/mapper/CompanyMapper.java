package hu.cubix.hr.BalazsPeregi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.cubix.hr.BalazsPeregi.dto.CompanyDto;
import hu.cubix.hr.BalazsPeregi.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	public CompanyDto companyToDto(Company company);

	public List<CompanyDto> comapniesToDtos(List<Company> companies);

	public Company dtoToCompany(CompanyDto company);

}
