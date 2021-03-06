package com.tim20.rivera.controllers;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @Transactional
    public void cancelReservation() throws Exception {
        // na odbrani nece proci jer ce tada biti 1 rezervacija
        mockMvc.perform(get("/api/get-reservations?username=pera&date=&upcoming=upcoming&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(2)));
        mockMvc.perform(get("/api/get-reservations?username=pera&date=&upcoming=all&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(8)));
        mockMvc.perform(post("/api/cancelReservation?username=pera&reservationId=4")
                .with(user("pera").password("sifra").roles("CLIENT")));
        mockMvc.perform(get("/api/get-reservations?username=pera&date=&upcoming=all&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(7)));
    }

    @Test
    @Transactional
    public void reservationPoints() throws Exception {
        mockMvc.perform(get("/api/get-client?id=pera"))
                .andExpect(jsonPath("$.numberOfPoints").value(26));
        mockMvc.perform(get("/api/get-reservations?username=pera&date=&upcoming=upcoming&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(2)));
        mockMvc.perform(post("/api/reserve?&username=pera&rentableId=3&start=12/08/2022 16:00&end=13/08/2022 01:00&price=300&additionalServices=&discountId=6")
                .with(user("pera").password("sifra").roles("CLIENT")));
        mockMvc.perform(get("/api/get-client?id=pera"))
                .andExpect(jsonPath("$.numberOfPoints").value(31));
        mockMvc.perform(get("/api/get-reservations?username=pera&date=&upcoming=upcoming&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    @Transactional
    public void isSubscribed() throws Exception {
        mockMvc.perform(get("/api/get-subscribed-entities?username=pera&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(0)));
        mockMvc.perform(post("/api/is-subscribed?username=pera&id=1"))
                .andExpect(jsonPath("$").value(false));
        mockMvc.perform(post("/api/subscribe?username=pera&id=1")
                .with(user("pera").password("sifra").roles("CLIENT")));
        mockMvc.perform(post("/api/is-subscribed?username=pera&id=1"))
                .andExpect(jsonPath("$").value(true));
        mockMvc.perform(get("/api/get-subscribed-entities?username=pera&search=")
                .with(user("pera").password("sifra").roles("CLIENT")))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    @Transactional
    public void clientData() throws Exception {
        mockMvc.perform(get("/api/get-client-by-username?username=pera"))
                .andExpect(jsonPath("$.country").value("Serbia"));
        mockMvc.perform(get("/api/check-if-client-exists?username=pera"))
                .andExpect(jsonPath("$").value(true));
        mockMvc.perform(get("/api/check-if-client-exists?username=marica"))
                .andExpect(jsonPath("$").value(false));
    }
}
