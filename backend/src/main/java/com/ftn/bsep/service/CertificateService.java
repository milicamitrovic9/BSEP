package com.ftn.bsep.service;

import java.util.Collection;

import com.ftn.bsep.model.Certificate;
import com.ftn.bsep.model.CertificateDAO;

public interface CertificateService {
	
	Collection<CertificateDAO> findAll();

	boolean createCA(CertificateDAO certificate) throws Exception;

	Certificate createRoot(CertificateDAO certificate) throws Exception;
}
