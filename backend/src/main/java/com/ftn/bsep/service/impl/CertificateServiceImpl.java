package com.ftn.bsep.service.impl;

import com.ftn.bsep.model.Certificate;
import com.ftn.bsep.model.CertificateDAO;
import com.ftn.bsep.service.CertificateService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Override
    public Collection<CertificateDAO> findAll() {
        return null;
    }

    @Override
    public boolean createCA(CertificateDAO certificate) throws Exception {
        return false;
    }

    @Override
    public Certificate createRoot(CertificateDAO certificate) throws Exception {
        return null;
    }
}
