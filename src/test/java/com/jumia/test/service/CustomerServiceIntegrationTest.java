package com.jumia.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jumia.test.AbstracBaseClass;
import com.jumia.test.util.TestUtil;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceIntegrationTest extends AbstracBaseClass {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	// CustomerController controller;

	@Test
	public void getAllCustomersWithoutFilter() throws Exception {
		String url = "http://localhost:8080/v1/customers";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String linkText = null;
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		TestUtil.getPageMap(pageMap, content);

		linkText = JsonPath.from(content).get("links").toString();
		int numberOfLinks = TestUtil.countLinks(linkText);
		assertEquals(HttpStatus.OK.value(), status);
		assertEquals(10, pageMap.get("size").intValue());
		assertEquals(41, pageMap.get("totalElements").intValue());
		assertEquals(5, pageMap.get("totalPages").intValue());
		assertEquals(0, pageMap.get("number").intValue());
		assertEquals(4, numberOfLinks);
	}

	@Test
	public void getNoContent() throws Exception {
		String urlNoContent = "http://localhost:8080/v1/customers?country=1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(urlNoContent).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.NO_CONTENT.value(), status);
	}

	@Test
	public void followPagesNoFilter() throws Exception {
		String url = "http://localhost:8080/v1/customers";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String linkText = null;
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		String nextLinkTemplate = "http://localhost:8080/v1/customers?page=PGNUMBER&size=10&sort=id,asc";
		TestUtil.getPageMap(pageMap, content);

		linkText = JsonPath.from(content).get("links").toString();
		int numberOfLinks = TestUtil.countLinks(linkText);
		int totalPages = pageMap.get("totalPages").intValue();
		assertEquals(HttpStatus.OK.value(), status);
		assertEquals(10, pageMap.get("size").intValue());
		assertEquals(41, pageMap.get("totalElements").intValue());
		assertEquals(5, pageMap.get("totalPages").intValue());
		assertEquals(0, pageMap.get("number").intValue());
		assertEquals(4, numberOfLinks);
		
		for(int i=1;i<totalPages;i++) {
			String nextLink = nextLinkTemplate.replaceFirst("PGNUMBER", Integer.toString(i));
			mvcResult = mvc.perform(MockMvcRequestBuilders.get(nextLink).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
			status = mvcResult.getResponse().getStatus();
			content = mvcResult.getResponse().getContentAsString();
			pageMap.clear();
			TestUtil.getPageMap(pageMap, content);
			linkText = JsonPath.from(content).get("links").toString();
			numberOfLinks = TestUtil.countLinks(linkText);
			totalPages = pageMap.get("totalPages").intValue();
			assertEquals(HttpStatus.OK.value(), status);
			assertEquals(10, pageMap.get("size").intValue());
			assertEquals(41, pageMap.get("totalElements").intValue());
			assertEquals(5, pageMap.get("totalPages").intValue());
			assertEquals(i, pageMap.get("number").intValue());
			if(i == totalPages-1) {
				assertEquals(4, numberOfLinks);	
			}else assertEquals(5, numberOfLinks);
		}
	}

	@Test
	public void getAllCustomersFilteredValid() throws Exception {
		String url = "http://localhost:8080/v1/customers?page=0&size=10&sort=id,asc&country=&state=1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String linkText = null;
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		TestUtil.getPageMap(pageMap, content);

		linkText = JsonPath.from(content).get("links").toString();
		int numberOfLinks = TestUtil.countLinks(linkText);
		assertEquals(HttpStatus.OK.value(), status);
		assertEquals(10, pageMap.get("size").intValue());
		assertEquals(27, pageMap.get("totalElements").intValue());
		assertEquals(3, pageMap.get("totalPages").intValue());
		assertEquals(0, pageMap.get("number").intValue());
		assertEquals(4, numberOfLinks);
	}
	
	@Test
	public void getAllCustomersFilteredNotValid() throws Exception {
		String url = "http://localhost:8080/v1/customers?page=0&size=10&sort=id,asc&country=&state=0";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String linkText = null;
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		TestUtil.getPageMap(pageMap, content);

		linkText = JsonPath.from(content).get("links").toString();
		int numberOfLinks = TestUtil.countLinks(linkText);
		assertEquals(HttpStatus.OK.value(), status);
		assertEquals(10, pageMap.get("size").intValue());
		assertEquals(14, pageMap.get("totalElements").intValue());
		assertEquals(2, pageMap.get("totalPages").intValue());
		assertEquals(0, pageMap.get("number").intValue());
		assertEquals(4, numberOfLinks);
	}		
}
