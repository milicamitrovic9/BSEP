package com.ftn.bsep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.bsep.model.Certificate;
import com.ftn.bsep.model.CertificateDAO;
import com.ftn.bsep.service.CertificateService;

@RestController
@RequestMapping("/api/certificate")
public class CertificateContoller {

	Logger logger = LoggerFactory.getLogger(CertificateContoller.class);

	@Autowired
	private CertificateService certificateService;

	@PostMapping(value = "/createRoot")
	public ResponseEntity<?> createRootCertificate(@RequestBody CertificateDAO certificate) throws Exception {

		Certificate rootCertificate = certificateService.createRoot(certificate);

		return new ResponseEntity<>(rootCertificate, HttpStatus.CREATED);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> createCertificate(@RequestBody CertificateDAO certificate) throws Exception {

		boolean created = certificateService.createCA(certificate);

		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

}
