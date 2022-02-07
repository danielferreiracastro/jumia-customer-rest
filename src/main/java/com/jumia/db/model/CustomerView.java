package com.jumia.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
public class CustomerView {

	private Long id;
    private String name;
    private String phone;
    private String countryCode;
    private String country;
    private String state;
}
