package com.tim20.rivera.controllers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageControllerTest {

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
    public void getCottage() throws Exception {
        mockMvc
                .perform(get("/api/cottage/get-cottage?id=4"))
                .andExpect(jsonPath("$.name").value("Very Cool Cottage"))
                .andExpect(jsonPath("$.address").value("1234 Main St."));
    }

    @Test
    public void getCottages() throws Exception {
        mockMvc
                .perform(get("/api/cottage/get-cottages"))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Very Cool Cottage")))
                .andExpect(jsonPath("$.[*].address").value(hasItem("1234 Main St.")));
    }

}
