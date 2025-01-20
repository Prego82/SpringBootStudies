package hu.cubix.hr.BalazsPeregi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CompanyForm {
	LIMITED_PARTNERSHIP(1, "Limited Partnership"), LLC(2, "LLC"), CORPORATION(3, "Corporation");

	private long id;
	private String name;

	private CompanyForm(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonCreator
	public static CompanyForm findByName(String name) {
		for (CompanyForm form : CompanyForm.values()) {
			if (form.getName().equalsIgnoreCase(name)) {
				return form;
			}
		}
		return null;
	}

}
