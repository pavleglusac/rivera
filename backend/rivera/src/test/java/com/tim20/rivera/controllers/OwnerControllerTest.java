package com.tim20.rivera.controllers;

import com.tim20.rivera.dto.JwtAuthenticationRequestDTO;
import com.tim20.rivera.dto.UserTokenState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestRestTemplate template;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .apply(springSecurity())
                                      .build();
    }

    @Test
    public void getAttendance() throws Exception {


        mockMvc
                .perform(get("/api/get-attendance?&startDate=2022-06-01&endDate=2022-06-30&type=week")
                        .with(user("cowner").password("sifra").roles("COTTAGE_OWNER"))
                )
                .andExpect(jsonPath("$.[3].timestamp").value("2022-06-20T00:00:00"))
                .andExpect(jsonPath("$.[3].count").value(2))
                .andExpect(jsonPath("$.[2].timestamp").value("2022-06-13T00:00:00"))
                .andExpect(jsonPath("$.[2].count").value(0))
                .andExpect(jsonPath("$.[1].timestamp").value("2022-06-06T00:00:00"))
                .andExpect(jsonPath("$.[1].count").value(0))
                .andExpect(jsonPath("$.[0].timestamp").value("2022-05-30T00:00:00"))
                .andExpect(jsonPath("$.[0].count").value(0));
    }

}
