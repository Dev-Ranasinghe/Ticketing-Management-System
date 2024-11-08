package com.ticketing_system.controller;

import com.ticketing_system.entity.Vendor;
import com.ticketing_system.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")

public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping
    public List<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Integer id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/email/{email}")
    public Vendor getVendorByEmail(@PathVariable String email) {
        return vendorService.getVendorByEmail(email);
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.saveVendor(vendor);
    }

    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable Integer id) {
        vendorService.deleteVendor(id);
    }

    @GetMapping("/login")
    public boolean vendorVerification(@RequestParam String username, @RequestParam String password){
        return vendorService.vendorVerification(username, password);
    }

}