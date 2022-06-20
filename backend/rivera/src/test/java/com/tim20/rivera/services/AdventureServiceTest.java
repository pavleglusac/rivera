package com.tim20.rivera.services;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.repository.PricelistRepository;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.AdventureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdventureServiceTest {
    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private AdventureService adventureService;

    @Test
    @Transactional
    public void testAdventuresOfOwner() {
        List<AdventureDTO> adventureList = adventureService.getAdventuresOfOwner("marko", false);
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Mountain Fishing"));
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Some adventure"));
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Very Cool Fishing"));
        assert adventureList.size() == 3;
    }

    @Test
    @Transactional
    public void testGetAllAdventures() {
        List<AdventureDTO> adventureList = adventureService.getAdventures(false);
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Mountain Fishing"));
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Some adventure"));
        assert adventureList.stream().anyMatch(x -> x.getName().equals("Very Cool Fishing"));
        assert adventureList.size() == 3;
    }

    @Test
    @Transactional
    public void testCreatePricelist() {
        AdventureDTO dto1 = new AdventureDTO();
        dto1.setCancellationTerms("0.2");
        dto1.setPerDay(100.0);
        dto1.setPerHour(120.0);

        Pricelist pricelist = adventureService.createPricelist(dto1);

        assert pricelist.getPricePerDay().equals(dto1.getPerDay());
        assert pricelist.getPricePerHour().equals(dto1.getPerHour());
        assert pricelist.getCancellationTerms().equals(dto1.getCancellationTerms());
        assert pricelist.getEndDateTime().equals(LocalDateTime.of(9999, 12, 31, 0, 0));
    }


    @Test
    @Transactional
    public void testSearchAdventures() {
        SearchParams params = new SearchParams();
        params.setTags(new ArrayList<>());
        params.setSearch("");
        params.setOwnerUsername("marko");
        params.setOrderBy("name-a");

        List<AdventureDTO> searchAdventures = adventureService.searchAdventures(params);
        searchAdventures.forEach(x -> System.out.println(x.getName()));

    }
}
