package hu.cubix.hr.BalazsPeregi.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Position {
	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private String name;

	@NotNull
	private Qualification qualification;

	@NotNull
	private long minSalary;

	public Position() {
	}

	public Position(long id, @NotNull String name, @NotNull Qualification qualification, @NotNull long minSalary) {
		super();
		this.id = id;
		this.name = name;
		this.qualification = qualification;
		this.minSalary = minSalary;
	}

	public Position(@NotNull String name, @NotNull Qualification qualification, @NotNull long minSalary) {
		super();
		this.name = name;
		this.qualification = qualification;
		this.minSalary = minSalary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public long getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(long minSalary) {
		this.minSalary = minSalary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		return id == other.id;
	}
}
