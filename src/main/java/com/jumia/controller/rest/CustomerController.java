package com.jumia.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.controller.util.CustomerUtils;
import com.jumia.db.model.Customer;
import com.jumia.db.model.CustomerView;
import com.jumia.db.repositories.ICustomerRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1")
@Api(value = "Endpoint to retrieve a list of customers.")
public class CustomerController {

	public static String buildLinkHeader(final String uri, final String rel) {
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}

	@Autowired
	private ICustomerRepository repository;

	@CrossOrigin
	@GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "API to GET list of customers filtered or not")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List retrieve success"),
			@ApiResponse(code = 204, message = "List has no contents")
	})	
	public <T> ResponseEntity<PagedResources<CustomerView>> getAllCustomers(
			@RequestParam(defaultValue = "0", name = "page") Integer pageNo,
			@RequestParam(defaultValue = "10", name = "size") Integer pageSize,
			@RequestParam(defaultValue = "id", name = "sort") String sortBy,
			@RequestParam(defaultValue = "", name = "country") String country,
			@RequestParam(defaultValue = "", name = "state") String state, PagedResourcesAssembler assembler) {
		if (pageNo < 0)
			pageNo = 0;
		// Pre processing state and country
		if("".equalsIgnoreCase(state) || "null".equalsIgnoreCase(state) || "undefined".equalsIgnoreCase(state)) state = null;
		if("".equalsIgnoreCase(country) || "null".equalsIgnoreCase(country) || "undefined".equalsIgnoreCase(country)) country = null;
		// End of Pre processing state and country
		
		String sorting[] = sortBy.split(",");
		Sort sort = Sort.by(sorting[0]);
		Pageable pageable = null;
		Page<Customer> customers = null;
		
		// Pre-processing sorting parameters
		pageable = createPeageable(pageNo, pageSize, sorting, sort);
		// End of pre-processing sorting parameters
		
		// Pre-processing filter by country
		if (null == country)
			customers = repository.findAll(pageable);
		else
			customers = repository.findAllByCountry(pageable, "(" + country + ")");
		//End of Pre-processing filter by country
		
		
		
		List<CustomerView> filteredListViews = new ArrayList<>();
		Page<CustomerView> _filtyeredViews = null;
		Page<CustomerView> customerViews = customers.map(new Function<Customer, CustomerView>() {
			public CustomerView apply(Customer customer) {
				CustomerView view = null;
				view = new CustomerView();
				view.setId(customer.getId());
				view.setName(customer.getName());
				view.setPhone(customer.getPhone());
				CustomerUtils.setCountryInfo(customer, view);
				CustomerUtils.validatePhone(customer, view);
				return view;
			}
		});
		
		if(null != state ){
			for(CustomerView view: customerViews.getContent()) {
				if(applyFilterState(state, view)) {
					filteredListViews.add(view);
				}
			}
			_filtyeredViews = new PageImpl<>(filteredListViews);
		}else _filtyeredViews = customerViews;
		
		PagedResources<CustomerView> pr = assembler.toResource(_filtyeredViews);

		HttpHeaders responseHeaders = new HttpHeaders();
		if (customerViews.getTotalElements() != 0) {
			return new ResponseEntity<>(pr, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(assembler.toEmptyResource(customerViews, Page.class),HttpStatus.NO_CONTENT);
			//return new ResponseEntity<>(assembler.toResource(customerViews), responseHeaders, HttpStatus.NO_CONTENT);
		}
	}

	private boolean applyFilterState(String state, CustomerView view) {
		Boolean retorno = Boolean.TRUE;
		if(!view.getState().equalsIgnoreCase(state)) retorno=Boolean.FALSE;
		return retorno;
	}
	
	private Pageable createPeageable(Integer pageNo, Integer pageSize, String[] sorting, Sort sort) {
		Pageable pageable;
		if (sorting.length > 1) {
			if (sorting[1].equalsIgnoreCase("asc"))
				pageable = PageRequest.of(pageNo, pageSize, sort.ascending());
			else
				pageable = PageRequest.of(pageNo, pageSize, sort.descending());
		} else
			pageable = PageRequest.of(pageNo, pageSize, sort.ascending());
		return pageable;
	}

	private String createLinkHeader(PagedResources<CustomerView> pr) {
		final StringBuilder linkHeader = new StringBuilder();
		linkHeader.append(buildLinkHeader(pr.getLinks("first").get(0).getHref(), "first"));
		linkHeader.append(", ");
		try {
			linkHeader.append(buildLinkHeader(pr.getLinks("next").get(0).getHref(), "next"));
		} catch (IndexOutOfBoundsException e) {

		}
		return linkHeader.toString();
	}

}
