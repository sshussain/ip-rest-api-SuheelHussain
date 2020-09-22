package com.example.demo.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AddressDao;

@Service
public class AddressService {
    private HashMap<String, String> map;
    private String storageName = "store.txt";

    @Autowired
    @Qualifier("fileDao")
    private AddressDao dao;
    
    public Map<String,String> getAddresses() throws IOException {
        loadAddresses();
        return map;
    }

    public void saveAddresses(List<String> asList) throws IOException {
        for (String s : asList) {
            map.put(s, "available");
        }
        try (BufferedWriter wr = new BufferedWriter(new FileWriter("store.txt"))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                wr.write(entry.getKey() + ":" + entry.getValue());
                wr.newLine();
            }
        }
    }
    
    public void loadAddresses() throws IOException {
        try(Stream<String> lines = Files.lines(Paths.get(storageName))){
            lines.filter(line -> line.contains(":")).forEach(
                line -> map.putIfAbsent(line.split(":")[0], 
                        line.split(":")[1])
            );
        }
    }
    
    public void releaseAddress(String address) throws IOException {
        loadAddresses();
        setAddressStatus(address, "available");
    }

    public void acquireAddress(String address) throws IOException {
        loadAddresses();
        setAddressStatus(address, "acquired");
    }

    private void setAddressStatus(String address, String status) throws IOException {
        if (map.containsKey(address)) {
            map.put(address, status);
            saveAddresses(map);
        }
    }
    

    private void saveAddresses(HashMap<String, String> map) throws IOException {
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(storageName))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                wr.write(entry.getKey() + ":" + entry.getValue());
                wr.newLine();
            }
        }
        
    }
}
