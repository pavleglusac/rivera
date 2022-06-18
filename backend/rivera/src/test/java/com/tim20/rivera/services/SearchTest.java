package com.tim20.rivera.services;

import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.service.AdventureService;
import com.tim20.rivera.service.BoatService;
import com.tim20.rivera.service.CottageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchTest {
    @Autowired
    AdventureService adventureService;
    @Autowired
    BoatService boatService;
    @Autowired
    CottageService cottageService;

    @Test
    @Transactional
    public void testAdventureSearch() {
        SearchParams search = new SearchParams();
        search.setSearch("");
        List<String> tags = new ArrayList<>();
        tags.add("wifi");
        search.setTags(tags);
        search.setNumberOfResults(10);
        search.setOrderBy("name-a");
        assert adventureService.searchAdventures(search).size() == 2;
    }

    @Test
    @Transactional
    public void testBoatSearch() {
        SearchParams search = new SearchParams();
        search.setSearch("cool");
        List<String> tags = new ArrayList<>();
        search.setTags(tags);
        search.setNumberOfResults(10);
        search.setOrderBy("name-a");
        assert boatService.searchBoats(search).size() == 3;
    }

    @Test
    @Transactional
    public void testCottageSearch() {
        SearchParams search = new SearchParams();
        search.setSearch("cottage");
        List<String> tags = new ArrayList<>();
        tags.add("river");
        search.setTags(tags);
        search.setNumberOfResults(10);
        search.setOrderBy("name-a");
        assert cottageService.searchCottages(search).size() == 2;
    }
}
