package com.ftn.bsep.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import com.ftn.bsep.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.LoginRequest;
import com.ftn.bsep.model.User;
import com.ftn.bsep.service.AdminService;
import com.ftn.bsep.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private AdminService adminService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/regKorisnika")
    public ResponseEntity<?> dodajKorisnika(@RequestBody UserDTO userRequest) throws Exception {
        Admin existAdmin = adminService.findByEmail(userRequest.getEmail());
        User existUser = userService.findByEmail(userRequest.getEmail());

        if (existAdmin != null || existUser != null) {
			return new ResponseEntity<>("Email vec postoji u bazi! Pokusajte drugi email.",
					HttpStatus.METHOD_NOT_ALLOWED);
        }

        User user = userService.create(userRequest);

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
    
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, @Context HttpServletRequest request) {
		
		Admin admin = adminService.findByEmail(loginRequest.getEmail());

		if (admin != null) {

			if(checkIntegrity(loginRequest.getPassword(), admin.getPassword())){
			//if (loginRequest.getPassword().equals(admin.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);
				return new ResponseEntity<>(admin, HttpStatus.CREATED);
			}

		} else {
            User user = userService.findByEmail(loginRequest.getEmail());
            if (user != null) {
				if(checkIntegrity(loginRequest.getPassword(), user.getPassword())){
                //if (loginRequest.getPassword().equals(user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    return new ResponseEntity<User>(user, HttpStatus.CREATED);
                }
            }
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

	//Metoda koja proverava da li je ostcen integritet poruke
	private boolean checkIntegrity(String data, byte[] dataHash) {
		byte[] newDataHash = hash(data);
		return Arrays.equals(dataHash, newDataHash);
	}
	public byte[] hash(String data) {
		//Kao hes funkcija koristi SHA-256
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] dataHash = sha256.digest(data.getBytes());
			return dataHash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
