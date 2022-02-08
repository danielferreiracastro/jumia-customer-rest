package com.jumia.controller.util;

import com.jumia.db.model.Customer;
import com.jumia.db.model.CustomerView;

public class CustomerUtils {

	public static CustomerView setCountryInfo(Customer customer, CustomerView instance) {
		if(null == customer) throw new IllegalArgumentException("customer parameter must not be null");
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
		if(customer.getValid().intValue()==1)instance.setState("Valid");
		return retorno;
	}
}
