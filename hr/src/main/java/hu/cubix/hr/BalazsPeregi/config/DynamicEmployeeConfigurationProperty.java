package hu.cubix.hr.BalazsPeregi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@ConfigurationProperties(prefix = "raises")
@Component
public class DynamicEmployeeConfigurationProperty {

	private String raiseListFromToPercentage;

	private List<Raise> raises = new ArrayList<>();

	public static class Raise {

		private int from;

		private int to;
		private int raisePercentage;

		public Raise(String property) {
			String[] splittedRaise = property.split(";");
			this.from = Integer.parseInt(splittedRaise[0]);
			this.to = Integer.parseInt(splittedRaise[1]) == -1 ? Integer.MAX_VALUE : Integer.parseInt(splittedRaise[1]);
			this.raisePercentage = Integer.parseInt(splittedRaise[2]);
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}

		public int getRaisePercentage() {
			return raisePercentage;
		}
	}

	public String getRaiseListFromToPercentage() {
		return raiseListFromToPercentage;
	}

	public void setRaiseListFromToPercentage(String raiseListFromToPercentage) {
		this.raiseListFromToPercentage = raiseListFromToPercentage;
	}

	@PostConstruct
	public void init() {
		if (raiseListFromToPercentage != null) {
			String[] splittedProperty = raiseListFromToPercentage.split(",");
			for (String property : splittedProperty) {
				raises.add(new Raise(property));
			}
		}
	}

	public List<Raise> getRaises() {
		return raises;
	}

}
