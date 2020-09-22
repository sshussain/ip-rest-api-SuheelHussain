package com.example.demo.controller;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.service.AddressService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

    @WebMvcTest
    class ControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AddressService addressService;

        @Test
        void testInvalidPath() throws Exception {
            mockMvc.perform(get("/howareyou")).andExpect(status().isNotFound());
        }

        @Test
        public void testGetIpForCidr() throws Exception {
            mockMvc.perform(post("/ipaddress").contentType(TEXT_PLAIN).content("10.0.0.1"))
            .andExpect(status().isOk())
            .andDo(print());
        }

    }
