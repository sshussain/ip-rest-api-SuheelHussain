package com.example.demo.controller;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AddressService;

@RestController("demo")
public class IpAddressController {

    @Autowired
    private AddressService addressService;
   
    @GetMapping(path = "/ipaddress")
    public Map<String, String> getIpAddresses() throws Exception {
        return addressService.getAddresses();
    }

    @PostMapping(path = "/ipaddress", consumes=TEXT_PLAIN_VALUE)
    public String createIpAddresses(@RequestBody String cidr) throws Exception {
        SubnetUtils su = new SubnetUtils(cidr);
        addressService.saveAddresses(Arrays.asList(su.getInfo().getAllAddresses()));
        return "success";        
    }
    
    @PutMapping(path = "/ipaddress", consumes=TEXT_PLAIN_VALUE, produces = TEXT_PLAIN_VALUE)
    public String acquireIpAddresses(@RequestBody String address) throws Exception {
        addressService.acquireAddress(address);
        return "success";
    }

    @DeleteMapping(path = "/ipaddress", consumes=TEXT_PLAIN_VALUE, produces = TEXT_PLAIN_VALUE)
    public String releaseIpAddress(@RequestBody String address) throws Exception {
        addressService.releaseAddress(address);
        return "success";
    }
}
