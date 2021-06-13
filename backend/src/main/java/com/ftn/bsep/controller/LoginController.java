package com.ftn.bsep.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.LoginRequest;
import com.ftn.bsep.model.User;
import com.ftn.bsep.model.UserDTO;
import com.ftn.bsep.service.AdminService;
import com.ftn.bsep.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping(value = "/regKorisnika")
	public ResponseEntity<?> dodajKorisnika(@Valid @RequestBody UserDTO userRequest) throws Exception {

		Admin existAdmin = adminService.findByEmail(userRequest.getEmail());
		User existUser = userService.findByEmail(userRequest.getEmail());

		if (existAdmin != null || existUser != null) {
			return new ResponseEntity<>("Email vec postoji u bazi! Pokusajte drugi email.",
					HttpStatus.METHOD_NOT_ALLOWED);
		}

		User user = userService.create(userRequest);
		
		logger.info("User registrated");

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, @Context HttpServletRequest request) {

		Admin admin = adminService.findByEmail(loginRequest.getEmail());

		if (admin != null) {

			boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword());

			if (isPasswordMatch) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);

				logger.info("Admin logged in");

				return new ResponseEntity<>(admin, HttpStatus.CREATED);
			}

		} else {
			User user = userService.findByEmail(loginRequest.getEmail());

			boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

			if (user != null) {
				if (isPasswordMatch) {
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					
					logger.info("User logged in");

					return new ResponseEntity<User>(user, HttpStatus.CREATED);
				}
			}
		}

		return new ResponseEntity<>("Korisnik nije pronadjen", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/loggedUser")
	public Object getLoggedUser(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();

		Admin admin = (Admin) session.getAttribute("admin");

		if (admin != null) {
			return admin;
		} else {
			return (User) session.getAttribute("user");
		}
	}

	@PutMapping(value = "/logOut")
	public ResponseEntity<?> logOut(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("...LOGOUT USER... " + session.getAttribute("user"));
		System.out.println("...LOGOUT ADMIN... " + session.getAttribute("admin"));
		session.invalidate();

		return ResponseEntity.status(200).build();
	}
}
