package com.ftn.bsep.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ftn.bsep.model.UserDTO;
import com.ftn.bsep.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@GetMapping(value = "/user/{userId}")
	public Admin loadById(@PathVariable Long userId) {
		return adminService.findById(userId);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> addAdmin(@Valid @RequestBody UserDTO adminRequest) throws Exception {

		Admin exist = adminService.findByEmail(adminRequest.getEmail());
		if (exist != null) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		Admin admin = adminService.create(adminRequest);
		logger.info("Admin user created");
		return new ResponseEntity<>(admin, HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> promeniPodatkeAdmina(@Context HttpServletRequest request, @RequestBody Admin admin)
			throws Exception {

		HttpSession session = request.getSession();
		Admin adm = (Admin) session.getAttribute("admin");

		if (adm == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			Admin a = adminService.update(admin);
			logger.info("Admin user updated");
			return new ResponseEntity<Admin>(a, HttpStatus.OK);
		}
	}


	@DeleteMapping(value = "/delete/{email}")
	public ResponseEntity<?> deleteAdmin (@Context HttpServletRequest request, @PathVariable("email") String email) {
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");

		if (admin == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			adminService.deleteByEmail(email);
			logger.info("Admin user deleted");
			return new ResponseEntity<Admin>(HttpStatus.NO_CONTENT);
		}
	}
}
