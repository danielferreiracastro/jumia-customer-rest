package com.jumia.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jumia.db.model.Customer;
import com.jumia.db.model.CustomerView;

public class CustomerUtils {

	private static List<Pattern> getRegexPattern() {
		List<Pattern> patterns = new ArrayList<>();
		patterns.add(Pattern.compile("\\(237\\)\\ ?[2368]\\d{7,8}$")); // Cameroon
		patterns.add(Pattern.compile("\\(251\\)\\ ?[1-59]\\d{8}$")); // Ethiopia
		patterns.add(Pattern.compile("\\(212\\)\\ ?[5-9]\\d{8}$")); // Marocco
		patterns.add(Pattern.compile("\\(258\\)\\ ?[28]\\d{7,8}$")); // Mozambique
		patterns.add(Pattern.compile("\\(256\\)\\ ?\\d{9}$")); // Uganda

		return patterns;
	}

	public static CustomerView setCountryInfo(Customer customer, CustomerView instance) {
		if (null == instance) {
			instance = new CustomerView();
			instance.setId(customer.getId());
			instance.setName(customer.getName());
			instance.setPhone(customer.getPhone());
		}

		String countryCode = customer.getPhone().substring(1, 4);
		switch (countryCode) {

		case "237":
			instance.setCountry(CountryEnum.CAMEROON.name());
			instance.setCountryCode(countryCode);
			break;
		case "251":
			instance.setCountry(CountryEnum.ETHIOPIA.name());
			instance.setCountryCode(countryCode);
			break;
		case "212":
			instance.setCountry(CountryEnum.MOROCCO.name());
			instance.setCountryCode(countryCode);
			break;
		case "258":
			instance.setCountry(CountryEnum.MOZAMBIQUE.name());
			instance.setCountryCode(countryCode);
			break;
		case "256":
			instance.setCountry(CountryEnum.UGANDA.name());
			instance.setCountryCode(countryCode);
			break;
		default:
			instance.setCountry("");
			instance.setCountryCode("");
			break;
		}
		return instance;
	}

	public static boolean validatePhone(Customer customer, CustomerView instance) {
		if (null == instance) {
			instance = new CustomerView();
			instance.setId(customer.getId());
			instance.setName(customer.getName());
			instance.setPhone(customer.getPhone());
		}
		Boolean retorno = Boolean.FALSE;
		instance.setState("Not Valid");
		List<Pattern> patterns = getRegexPattern();
		for (Pattern p : patterns) {
			Matcher matcher = p.matcher(customer.getPhone());
			if (matcher.matches()) {
				instance.setState("Valid");
				retorno = Boolean.TRUE;
				break;
			}
		}

		return retorno;
	}
}
