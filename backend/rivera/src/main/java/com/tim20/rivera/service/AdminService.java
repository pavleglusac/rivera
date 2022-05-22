package com.tim20.rivera.service;

import com.tim20.rivera.model.Admin;
import com.tim20.rivera.model.MemberCategory;
import com.tim20.rivera.model.Rules;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberCategoryRepository memberCategoryRepository;

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Admin findByUsername(String username) {
        return adminRepository.findById(username).orElse(null);
    }

    public List<MemberCategory> getCategories() {
        return memberCategoryRepository.findAll();
    }

    public Rules getRules() {
        return rulesRepository.findAll().get(0);
    }

    public void updateCategories(ArrayList<MemberCategory> categories) {
        clientRepository.findAll().forEach(client -> client.setCategory(null));
        ownerRepository.findAll().forEach(owner -> owner.setCategory(null));
        clientRepository.saveAll(clientRepository.findAll());
        ownerRepository.saveAll(ownerRepository.findAll());
        memberCategoryRepository.deleteAll();
        memberCategoryRepository.saveAll(categories);
        clientRepository.findAll().forEach(client -> client.setCategory(getMaxCategory(categories, client.getNumberOfPoints(), false)));
        ownerRepository.findAll().forEach(owner -> owner.setCategory(getMaxCategory(categories, owner.getNumberOfPoints(), true)));
        clientRepository.saveAll(clientRepository.findAll());
        ownerRepository.saveAll(ownerRepository.findAll());
    }

    private MemberCategory getMaxCategory(ArrayList<MemberCategory> categories, int points, boolean forOwner) {
        int maxPoints = 0;
        MemberCategory maxCategory = null;
        for (MemberCategory category: categories) {
            int categoryPoints = category.getNumberOfPoints();
            if(categoryPoints >= maxPoints && categoryPoints <= points && category.getForOwner() == forOwner) {
                maxPoints = categoryPoints;
                maxCategory = category;
            }
        }
        return maxCategory;
    }

    public void updateRules(Rules rules) {
        Rules oldRule = rulesRepository.getById(1);
        oldRule.setIncomePercentage(rules.getIncomePercentage());
        oldRule.setPointsPerReservation(rules.getPointsPerReservation());
        rulesRepository.save(oldRule);
    }
}
