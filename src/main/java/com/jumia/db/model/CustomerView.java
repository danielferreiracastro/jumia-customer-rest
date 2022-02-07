package com.jumia.db.model;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "Customer View Model", description = "Customer View Model used to present data")
public class CustomerView {

	private Long id;
    private String name;
    private String phone;
    private String countryCode;
    private String country;
    private String state;
}
