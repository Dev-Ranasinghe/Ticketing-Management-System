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
        // Create and start a thread to handle fetching all vendors
        VendorService.VendorThread fetchAllThread = vendorService.new VendorThread("fetchAll", null, null);
        fetchAllThread.start();
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Integer id) {
        // Create and start a thread to handle fetching vendor by id
        VendorService.VendorThread fetchByIdThread = vendorService.new VendorThread("fetchById", id, null);
        fetchByIdThread.start();
        return vendorService.getVendorById(id);
    }

    @GetMapping("/email/{email}")
    public Vendor getVendorByEmail(@PathVariable String email) {
        return vendorService.getVendorByEmail(email);
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        // Create and start a thread to handle saving a new vendor
        VendorService.VendorThread saveThread = vendorService.new VendorThread("save", null, vendor);
        saveThread.start();
        return vendorService.saveVendor(vendor);
    }

    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable Integer id) {
        // Create and start a thread to handle deleting a vendor
        VendorService.VendorThread deleteThread = vendorService.new VendorThread("delete", id, null);
        deleteThread.start();
        vendorService.deleteVendor(id);
    }

    @GetMapping("/login")
    public boolean vendorVerification(@RequestParam String username, @RequestParam String password){
        return vendorService.vendorVerification(username, password);
    }
}