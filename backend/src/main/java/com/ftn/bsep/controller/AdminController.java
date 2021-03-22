package com.ftn.bsep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@GetMapping(value = "/user/{userId}")
	public Admin loadById(@PathVariable Long userId) {
		return adminService.findById(userId);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> addAdmin(@RequestBody Admin adminRequest) throws Exception {
		System.out.println(adminRequest);
		Admin exist = adminService.findByEmail(adminRequest.getEmail());
		if (exist != null) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		Admin admin = adminService.create(adminRequest);

		return new ResponseEntity<>(admin, HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> promeniPodatkeAdmina(@RequestBody Admin adminRequest) throws Exception {

		Admin adminUpdate = adminService.update(adminRequest);
		return new ResponseEntity<>(adminUpdate, HttpStatus.OK);
	}


	@DeleteMapping(value = "/delete/{email}")
	public ResponseEntity<Admin> deleteAdmin(@PathVariable("email") String email) {
		adminService.deleteByEmail(email);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
