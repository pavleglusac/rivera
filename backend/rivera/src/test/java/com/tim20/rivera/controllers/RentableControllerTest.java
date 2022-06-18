package com.tim20.rivera.controllers;

import com.tim20.rivera.repository.CottageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentableControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getTypeOfRentable1() throws Exception {
        mockMvc
                .perform(get("/api/get-type-of-rentable?id=4"))
                .andExpect(jsonPath("$").value("cottage"));
    }

    @Test
    public void getTypeOfRentable2() throws Exception {
        mockMvc
                .perform(get("/api/get-type-of-rentable?id=7"))
                .andExpect(jsonPath("$").value("boat"));
    }

}
