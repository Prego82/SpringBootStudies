package hu.cubix.hr.BalazsPeregi.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import hu.cubix.hr.BalazsPeregi.model.CompanyForm;

public class CompanyFormDeserializer extends JsonDeserializer<CompanyForm> {

	@Override
	public CompanyForm deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String value = p.getText();
		return CompanyForm.findByName(value);
	}

}