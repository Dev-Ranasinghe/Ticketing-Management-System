package com.ticketing_system.service;

import com.ticketing_system.entity.Vendor;
import com.ticketing_system.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    // Get all vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // Get a single vendor by id
    public Vendor getVendorById(Integer id) {
        return vendorRepository.findById(id).orElse(null);
    }

    // Get a single vendor by email
    public Vendor getVendorByEmail(String email) {
        return vendorRepository.findByVendorEmail(email);
    }

    // Create or update a vendor
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // Delete a vendor by ID
    public void deleteVendor(Integer id) {
        vendorRepository.deleteById(id);
    }

    public boolean vendorVerification(String username, String password){
        boolean valid = false;
        Vendor vendor = vendorRepository.findByVendorEmail(username);
        if(vendor == null){
            return false;
        }
        if(vendor.getVendorPassword().equals(password)){
            valid = true;
        }
        return valid;
    }

}