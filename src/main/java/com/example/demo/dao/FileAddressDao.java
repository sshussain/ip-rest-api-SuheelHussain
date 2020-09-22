package com.example.demo.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component("fileDao")
public class FileAddressDao implements AddressDao {

    private String storageFile = "store.txt";
    
    @Override
    public void save(List<String> asList) throws IOException {
        Map<String, String> map = Collections.emptyMap();
        
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

    @Override
    public void save(Map<String, String> map) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Map<String, String> load() throws IOException {
        Map<String, String> map = Collections.emptyMap();
        try(Stream<String> lines = Files.lines(Paths.get(storageFile))){
            lines.filter(line -> line.contains(":")).forEach(
                line -> map.putIfAbsent(line.split(":")[0], 
                        line.split(":")[1])
            );
        }
        return map;
    }

    @Override
    public void release(String address) throws IOException {
        Map<String, String> map = load();
        setAddressStatus(map, address, "acquired");
    }

    @Override
    public void acquire(String address) throws IOException {
        Map<String, String> map = load();
        setAddressStatus(map, address, "acquired");
    }

    private void setAddressStatus(Map<String, String> map, String address, String status) throws IOException {
        if (map.containsKey(address)) {
            map.put(address, status);
            save(map);
        }
    }

}
