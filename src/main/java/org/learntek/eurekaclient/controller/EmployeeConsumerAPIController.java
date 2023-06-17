/**
 * 
 */
package org.learntek.eurekaclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.learntek.eurekaclient.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;

/**
 * @author HP
 *
 */
@RestController
public class EmployeeConsumerAPIController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getEmpoyees(){
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("employee-producer");
		ServiceInstance serviceInstance = serviceInstances.get(0);
		String hostName = serviceInstance.getUri().toString();
		System.out.println("hostName ::::"+hostName);
		List employees = restTemplate.getForObject(hostName+"/employee", List.class);
		ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
		return responseEntity;
	} 
}