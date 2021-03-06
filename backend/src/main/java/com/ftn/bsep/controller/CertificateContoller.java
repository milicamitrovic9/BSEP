package com.ftn.bsep.controller;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.User;
import com.ftn.bsep.service.CertificateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.bsep.model.Certificate;
import com.ftn.bsep.model.CertificateDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/certificate")
public class CertificateContoller {

	@Autowired
	private CertificateService certificateService;

	private static Logger logger = LoggerFactory.getLogger(CertificateContoller.class);

	@RequestMapping(method = POST, value = "/create")
	public ResponseEntity<?> napraviSertifikat(@Context HttpServletRequest request, @RequestBody CertificateDAO certificate)
			throws Exception {

		System.out.println("pre ulaska: " + certificate.toString());
		boolean povratna = certificateService.createCA(certificate);
		logger.info("Sertifikat je napravljen");
		return new ResponseEntity<>(povratna, HttpStatus.CREATED);
	}

	@RequestMapping(method = POST, value = "/createRoot")
	public ResponseEntity<?> napraviRootSertifikat(@Context HttpServletRequest request, @RequestBody CertificateDAO certificate)
			throws Exception {

		Certificate certificate1 = certificateService.createRoot(certificate);
		logger.info("Root sertifikat je napravljen");
		return new ResponseEntity<Certificate>(certificate1, HttpStatus.CREATED);
	}

	@RequestMapping(method = GET, value = "/aliasi")
	public ResponseEntity<?> vratiAliase(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			List<String> imena = new ArrayList<>();
			imena = certificateService.naziviSertifikata();
			return new ResponseEntity<>(imena, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/aliasiEE")
	public ResponseEntity<?> vratiAliaseEE(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			List<String> imena = new ArrayList<>();
			imena = certificateService.naziviEESertifikata();

			return new ResponseEntity<>(imena, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/aliasiSvi")
	public ResponseEntity<?> vratiAliaseSve(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("korisnik");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			List<String> imena = new ArrayList<>();
			List<String> imena1 = certificateService.naziviSertifikata(); //ca
			List<String> imena2 = certificateService.naziviEESertifikata();
			List<String> imena3 = certificateService.naziviPovucenihSertifikata(); //povuceni
			imena.addAll(imena1);
			imena.addAll(imena2);
			imena.addAll(imena3);

			return new ResponseEntity<>(imena, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/sviCASertifikati")
	public ResponseEntity<?> vratiSveCA(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			ArrayList<CertificateDAO> sertifikati = new ArrayList<>();
			sertifikati = certificateService.vratiSveCA();
			return new ResponseEntity<>(sertifikati, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/sviEESertifikati")
	public ResponseEntity<?> vratiSveEE(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			ArrayList<CertificateDAO> sertifikati = new ArrayList<>();
			sertifikati = certificateService.vratiSveEE();
			return new ResponseEntity<>(sertifikati, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/downloadCA/{uid}")
	public ResponseEntity<?> skiniEE(@Context HttpServletRequest request, @PathVariable("uid") String uid) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			certificateService.skiniCa(uid);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = GET, value = "/downloadEE/{uid}")
	public ResponseEntity<?> skiniCa(@Context HttpServletRequest request, @PathVariable("uid") String uid) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User user = (User) session.getAttribute("user");

		if (admin == null && user == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			certificateService.skiniEE(uid);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = GET, value = "/povuciCA/{uid}")
	public ResponseEntity<?> povuciSertCA(@Context HttpServletRequest request, @PathVariable("uid") String uid) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			certificateService.povuciCertificateCA(uid);
			logger.info("SertifikatCA je povucen");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(method = GET, value = "/povuciEE/{uid}")
	public ResponseEntity<?> povuciSertEE(@Context HttpServletRequest request, @PathVariable("uid") String uid) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			certificateService.povuciCertificateEE(uid);
			logger.info("SertifikatEE je povucen");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(method = GET, value = "/sviPovuceniSertifikati")
	public ResponseEntity<?> sviPovuceniSertifikati(@Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		User korisnik = (User) session.getAttribute("user");

		if (admin == null && korisnik == null) {
			return new ResponseEntity<>("Nedozvoljeno ponasanje!", HttpStatus.FORBIDDEN);
		} else {
			ArrayList<CertificateDAO> sertifikati = new ArrayList<>();
			sertifikati = certificateService.vratiSvePovucene();

			return new ResponseEntity<>(sertifikati, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = GET, value = "/validacijaCA/{izabraniAliasCA}")
	public ResponseEntity<?> validacijaCA(@PathVariable("izabraniAliasCA") String izabraniAliasCA) throws Exception {
		String uid = izabraniAliasCA.replace("CA", "");
		boolean ret = certificateService.validacijaCA(uid);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@RequestMapping(method = GET, value = "/validacijaEE/{izabraniAliasEE}")
	public ResponseEntity<?> validacijaEE(@PathVariable("izabraniAliasEE") String izabraniAliasEE) throws Exception {
		String uid = izabraniAliasEE.replace("EE", "");
		boolean ret = certificateService.validacijaEE(uid);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@RequestMapping(method = GET, value = "/validacijaSvi/{izabraniAliasSvi}")
	public ResponseEntity<?> validacijaSvi(@PathVariable("izabraniAliasSvi") String izabraniAliasSvi) throws Exception {
		boolean ret = certificateService.validacijaSvi(izabraniAliasSvi);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

}
