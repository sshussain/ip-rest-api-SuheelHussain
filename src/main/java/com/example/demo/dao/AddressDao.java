package com.example.demo.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AddressDao {
    void save(List<String> asList) throws IOException;

    void save(Map<String, String> map);

    Map<String, String> load() throws IOException;

    void release(String address) throws IOException;

    void acquire(String address) throws IOException;
}
