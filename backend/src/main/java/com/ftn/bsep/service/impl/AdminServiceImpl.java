package com.ftn.bsep.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.repository.AdminRepository;
import com.ftn.bsep.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Collection<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseGet(null);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

	@Override
	public Admin create(Admin admin) throws Exception {
        
		Admin adminModel = new Admin();
        adminModel.copyValues(admin);
        adminModel = adminRepository.save(adminModel);
        
        return adminModel;
	}

    @Override
    public Admin update(Admin admin) throws Exception {
        Admin adminKlinikeZaIzmenu = findById(admin.getId());
        adminKlinikeZaIzmenu.copyValues(admin);
        adminKlinikeZaIzmenu = adminRepository.save(adminKlinikeZaIzmenu);
        return adminKlinikeZaIzmenu;
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        Admin zaObrisati = findByEmail(email);
        adminRepository.deleteById(zaObrisati.getId());
    }
    
}
