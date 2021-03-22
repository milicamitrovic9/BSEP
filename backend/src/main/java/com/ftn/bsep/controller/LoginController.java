package com.ftn.bsep.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.LoginRequest;
import com.ftn.bsep.service.AdminService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(method = POST)
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, @Context HttpServletRequest request) {

		Admin ak = adminService.findByEmail(loginRequest.getEmail());

		if (ak != null) {

			if (loginRequest.getPassword().equals(ak.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", ak);
				return new ResponseEntity<Admin>(ak, HttpStatus.CREATED);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = GET, value = "/loggedUser")
	public Object getLoggedUser(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();

		Admin admin = (Admin) session.getAttribute("admin");

		if (admin != null) {
			return admin;
		} else {
			return null;
		}
	}

	@RequestMapping(method = POST, value = "/logout")
	public ResponseEntity<?> logOut(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();

		return ResponseEntity.status(200).build();
	}

}
