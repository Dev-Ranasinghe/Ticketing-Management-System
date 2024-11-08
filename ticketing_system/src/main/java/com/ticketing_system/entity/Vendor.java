package com.ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data //getters and setters auto generated
@AllArgsConstructor //generate constructor with all the attributes
@NoArgsConstructor //default constructor with no args
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Integer vendorId;
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;
    @Column(name = "vendor_contact", nullable = false)
    private String vendorContact;
    @Column(name = "vendor_email", nullable = false)
    private String vendorEmail;
    @Column(name = "vendor_password", nullable = false)
    private String vendorPassword;

    public String getVendorPassword() {
        return vendorPassword;
    }
}