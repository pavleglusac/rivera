package com.tim20.rivera.controllers;

import com.tim20.rivera.repository.RentableRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RentableRepository rentableRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void getCottage() throws Exception {
        System.out.println(rentableRepository.findById(4).get().getName());
        mockMvc
                .perform(get("/api/cottage/get-cottage?id=4")).andDo(print())
                .andExpect(jsonPath("$.name").value("Nordic cottage"))
                .andExpect(jsonPath("$.address").value("1234 Main St."));
    }

    @Test
    @Transactional
    public void getCottages() throws Exception {
        mockMvc
                .perform(get("/api/cottage/get-cottages"))
                .andExpect(jsonPath("$.[*].name").value(hasItem("English Cottage")))
                .andExpect(jsonPath("$.[*].address").value(hasItem("1234 Main St.")));
    }

}
