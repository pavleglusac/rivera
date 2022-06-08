package com.tim20.rivera.service;

import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.dto.IncomeSystemDTO;
import com.tim20.rivera.dto.TerminationRequestDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TerminationRepository terminationRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReservationRepository reservationRepository;

    final String DEFAULT_PHOTO_PATH = "\\images\\default.jpg";


    public Admin findByUsername(String username) {
        return adminRepository.findById(username)
                .orElse(null);
    }

    public List<MemberCategory> getCategories() {
        return memberCategoryRepository.findAll();
    }

    public Rules getRules() {
        return rulesRepository.findAll()
                .get(0);
    }

    public void updateCategories(ArrayList<MemberCategory> categories) {
        clientRepository.findAll()
                .forEach(client -> client.setCategory(null));
        ownerRepository.findAll()
                .forEach(owner -> owner.setCategory(null));
        clientRepository.saveAll(clientRepository.findAll());
        ownerRepository.saveAll(ownerRepository.findAll());
        memberCategoryRepository.deleteAll();
        memberCategoryRepository.saveAll(categories);
        clientRepository.findAll()
                .forEach(client -> client
                        .setCategory(getMaxCategory(categories, client.getNumberOfPoints(), false)));
        ownerRepository.findAll()
                .forEach(owner -> owner
                        .setCategory(getMaxCategory(categories, owner.getNumberOfPoints(), true)));
        clientRepository.saveAll(clientRepository.findAll());
        ownerRepository.saveAll(ownerRepository.findAll());
    }

    private MemberCategory getMaxCategory(ArrayList<MemberCategory> categories, int points, boolean forOwner) {
        int maxPoints = 0;
        MemberCategory maxCategory = null;
        for (MemberCategory category : categories) {
            int categoryPoints = category.getNumberOfPoints();
            if (categoryPoints >= maxPoints && categoryPoints <= points && category.getForOwner() == forOwner) {
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

    public List<String> getUnregisteredUsernames() {
        return ownerRepository
                .findAll()
                .stream()
                .filter(owner -> owner.getStatus()
                        .equals(AccountStatus.WAITING))
                .map(Person::getUsername)
                .collect(Collectors.toList());
    }

    public HashMap<String, Integer> getRegisteredStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        ownerRepository.findAll()
                .forEach(x -> stats.put(x.getStatus().name(), stats.getOrDefault(x.getStatus().name(), 0) + 1));
        return stats;
    }

    public List<TerminationRequestDTO> getTerminationRequests() {
        return terminationRepository.findAllByStatus(TerminationStatus.PENDING)
                .stream()
                .map(this::terminationRequestToDto)
                .collect(Collectors.toList());
    }

    public TerminationRequestDTO terminationRequestToDto(TerminationRequest request) {
        var dto = new TerminationRequestDTO();
        dto.setUsername(request.getPerson().getUsername());
        dto.setDescription(request.getDescription());
        return dto;
    }

    public void terminatePerson(String username) {
        Person person = personRepository.findByUsername(username);
        if (person == null) return;
        person.setStatus(AccountStatus.TERMINATED);
        List<TerminationRequest> requests = terminationRepository.findAllByPersonAndStatus(person, TerminationStatus.PENDING);
        requests.forEach(request -> request.setStatus(TerminationStatus.ACCEPTED));
        personRepository.save(person);
    }

    public void rejectTerminationRequest(String username) {
        Person person = personRepository.findByUsername(username);
        if (person == null) return;
        List<TerminationRequest> requests = terminationRepository.findAllByPersonAndStatus(person, TerminationStatus.PENDING);
        requests.forEach(request -> request.setStatus(TerminationStatus.DENIED));
        terminationRepository.saveAll(requests);
    }

    private Admin clientRequestDTOToAdmin(ClientRequestDTO clientRequestDTO) {
        Admin admin = new Admin();
        admin.setPassword(passwordEncoder.encode(clientRequestDTO.getPassword()));
        admin.setName(clientRequestDTO.getName());
        admin.setUsername(clientRequestDTO.getUsername());
        admin.setSurname(clientRequestDTO.getSurname());
        admin.setPhoneNumber(clientRequestDTO.getPhoneNumber());
        admin.setAddress(clientRequestDTO.getAddress());
        admin.setCity(clientRequestDTO.getCity());
        admin.setEmail(clientRequestDTO.getEmail());
        admin.setCountry(clientRequestDTO.getCountry());
        admin.setPhoto(DEFAULT_PHOTO_PATH);
        admin.setStatus(AccountStatus.WAITING);
        List<Role> roles = roleService.findByName("ROLE_ADMIN");
        admin.setRoles(roles);
        return admin;
    }

    public Person save(ClientRequestDTO clientRequestDTO) {
        Admin admin = clientRequestDTOToAdmin(clientRequestDTO);
        adminRepository.save(admin);
        return admin;
    }

    public List<IncomeSystemDTO> getSystemIncome(String type, LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) return null;
        LocalDateTime nextVal = from;
        String label = "";
        List<IncomeSystemDTO> incomes = new ArrayList<>();
        while (from.isBefore(to)) {
            switch (type) {
                case "week" -> {
                    nextVal = nextVal.plusDays(7);
                    label = String.format("%s", from.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
                }
                case "day" -> {
                    nextVal = nextVal.plusDays(1);
                    label = String.format("%s", from.format(DateTimeFormatter.ISO_LOCAL_DATE));
                }
                case "month" -> {
                    nextVal = nextVal.plusMonths(1);
                    nextVal = nextVal.withDayOfMonth(1);
                    label = String.format("%s %d", from.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), from.getYear());
                }
                case "year" -> {
                    nextVal = nextVal.plusYears(1);
                    nextVal = nextVal.withDayOfMonth(1);
                    nextVal = nextVal.withMonth(1);
                    label = String.format("%d", from.getYear());
                }
            }

            if (nextVal.isAfter(to)) {
                nextVal = to;
            }
            //
            Double income = reservationRepository.findTotalSystemIncome(from, nextVal);
            if (income == null) income = 0.0;
            IncomeSystemDTO incomeDto = new IncomeSystemDTO();
            incomeDto.setIncome(income);
            incomeDto.setLabel(label);
            incomes.add(incomeDto);
            from = nextVal;
        }
        return incomes;
    }
}
