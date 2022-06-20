package com.tim20.rivera.controllers;

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

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void getRules() throws Exception {
        mockMvc
                .perform(get("/api/admin/rules")
                        .with(user("admin").password("sifra").roles("ADMIN"))).andExpect(status().isOk())
                .andExpect(jsonPath("$.pointsPerReservation").value(5))
                .andExpect(jsonPath("$.incomePercentage").value(50));
    }

    @Test
    public void getOwnerRequest() throws Exception {
        mockMvc
                .perform(get("/api/admin/owner-request?username=marko")
                        .with(user("admin").password("sifra").roles("ADMIN")))
                .andExpect(jsonPath("$.username").value("marko"))
                .andExpect(jsonPath("$.name").value("Mitar"))
                .andExpect(jsonPath("$.surname").value("Markovic"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    public void getCategories() throws Exception {
        mockMvc
                .perform(get("/api/admin/categories")
                        .with(user("admin").password("sifra").roles("ADMIN")))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(5.0)))
                .andExpect(jsonPath("$.[*].numberOfPoints").value(hasItem(0)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Basic")));
    }


    @Test
    public void getPendingTerminationRequest() throws Exception {
        // (id, description, status, person_id, version) values (1, 'Opis', 2, 'pera', 0)
        mockMvc
                .perform(get("/api/admin/pending-termination-requests")
                        .with(user("admin").password("sifra").roles("ADMIN")))
                .andExpect(jsonPath("$.[*].username").value(hasItem("pera")))
                .andExpect(jsonPath("$.[*].description").value(hasItem("Opis")))
                .andExpect(jsonPath("$.[*].requestId").value(hasItem(1)));
    }

    @Test
    public void getPendingTerminationReviews() throws Exception {
        /*
        DATABASE
        INSERT INTO review (posted, score, status, text, client_id, rentable_id, owner_id, type, version)
VALUES ('2022-05-21 17:44:47.41532', 3.3, 1,
        'Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ',
        'pera', 1, null, 0, 0);

        DTO
        private String reviewedName;
        private String reviewedImage;
        private String clientName;
        private String clientSurname;
        private String clientImage;
        private Integer id;
        private Double score;
        private String text;
        private boolean complaint;
         */
        mockMvc
                .perform(get("/api/admin/pending-reviews")
                        .with(user("admin").password("sifra").roles("ADMIN")))
                .andExpect(jsonPath("$.[*].clientName").value(hasItem("Petar")))
                .andExpect(jsonPath("$.[*].clientSurname").value(hasItem("Petrovic")))
                .andExpect(jsonPath("$.[*].reviewedName").value(hasItem("Mountain Fishing")))
                .andExpect(jsonPath("$.[*].text").value(hasItem("Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ")))
                .andExpect(jsonPath("$.[*].complaint").value(hasItem(true)));
    }
}
