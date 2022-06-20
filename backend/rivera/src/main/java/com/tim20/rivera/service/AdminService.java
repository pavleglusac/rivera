package com.tim20.rivera.service;

import com.tim20.rivera.controller.AdminReviewDTO;
import com.tim20.rivera.dto.AdminReservationReportDTO;
import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.dto.IncomeSystemDTO;
import com.tim20.rivera.dto.TerminationRequestDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
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

    @Autowired
    private ReservationReportRepository reservationReportRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ConfigurableEnvironment env;

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
        dto.setRequestId(request.getId());
        return dto;
    }

    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
    public boolean resolveTerminationRequest(String username, boolean terminate, int requestId, String reason) {
        try {
            Person person = personRepository.findByUsername(username);
            TerminationRequest request = terminationRepository.findById(requestId);
            if (person == null || request == null) return false;
            handlePersonTermination(terminate, person, request);
            if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
                Thread.sleep(1000);
            }
            terminationRepository.save(request);
            notifyUserOnTerminationRequest(username, terminate, reason);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private void handlePersonTermination(boolean terminate, Person person, TerminationRequest request) {
        if(terminate) {
            person.setStatus(AccountStatus.TERMINATED);
            personRepository.save(person);
            request.setStatus(TerminationStatus.ACCEPTED);
        } else {
            request.setStatus(TerminationStatus.DENIED);
        }
    }

    private void notifyUserOnTerminationRequest(String username, boolean terminate, String reason) {
        if(!terminate) {
            emailService.sendNotificaitionToUsername(username, "Termination rejected", reason);
        } else {
            emailService.sendNotificaitionToUsername(username, "Terminated account", "Your account has been terminated. Thank you for using rivera");
        }
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

    public List<AdminReservationReportDTO> getPendingReports() {
        return reservationReportRepository
                .findAllByResolved(false)
                .stream()
                .map(this::reportToAdminReportDTO)
                .collect(Collectors.toList());
    }

    private AdminReservationReportDTO reportToAdminReportDTO(ReservationReport report) {
        AdminReservationReportDTO dto = new AdminReservationReportDTO();
        dto.setOwnerName(report.getReservation().getRentable().getOwner().getName());
        dto.setOwnerImage(report.getReservation().getRentable().getOwner().getPhoto());
        dto.setClientName(report.getReservation().getClient().getName());
        dto.setClientImage(report.getReservation().getClient().getPhoto());
        dto.setText(report.getText());
        dto.setType(report.getReservationReportType().name());
        dto.setId(report.getId());
        dto.setSanction(report.getSanction());
        return dto;
    }


    @Transactional
    public boolean resolveReport(int reportId, String responseText, boolean assignPenalty) {
        try {
            ReservationReport report = reservationReportRepository.getById(reportId);
            Client client = report.getReservation().getClient();
            Owner owner = report.getReservation().getRentable().getOwner();
            if (assignPenalty)  {
                assignPenaltyAndSendEmails(responseText, report, client, owner);
            } else if(report.getSanction()) {
                rejectPenalytAndSendEmail(responseText, client, owner);
            }
            report.setResolved(true);
            reservationReportRepository.save(report);
            if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private void rejectPenalytAndSendEmail(String responseText, Client client, Owner owner) {
        emailService.sendNotificaitionToUsername(owner.getUsername(), "Penalty",
                "Dear " + owner.getUsername() + ",\n\nWe inform you that the user with username "
                        + client.getUsername() + " will not be assigned a penalty. Reason:\n"
                        +responseText+
                        "\n\nSincerely,\n Rivera.");
    }

    private void assignPenaltyAndSendEmails(String responseText, ReservationReport report, Client client, Owner owner) {
        int totalPenalties = client.getNumberOfPenalties();
        report.getReservation().getClient().setNumberOfPenalties(totalPenalties + 1);
        emailService.sendNotificaitionToUsername(client.getUsername(), "Penalty",
                "Dear " + client.getUsername() + ",\n\nUnfortunately we must inform" +
                        "you that you have been assigned a penalty. Three of these penalties per month " +
                        "will stop you from using our services. Reason: " + responseText +
                        "\n\nSincerely,\n Rivera.");

        emailService.sendNotificaitionToUsername(owner.getUsername(), "Penalty",
                "Dear " + owner.getUsername() + ",\n\nWe inform you that the user with username "
                        + client.getUsername() + " was assigned a penalty as per your request." +
                        "\n\nSincerely,\n Rivera.");
    }

    public List<AdminReviewDTO> getPendingReviews() {
        return reviewRepository
                .getReviewsByStatus(ReviewStatus.PENDING)
                .stream()
                .map(this::reviewToAdminReviewDTO)
                .collect(Collectors.toList());
    }

    public AdminReviewDTO reviewToAdminReviewDTO(Review review) {
        AdminReviewDTO dto = new AdminReviewDTO();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setClientName(review.getClient().getName());
        dto.setClientSurname(review.getClient().getSurname());
        dto.setClientImage(review.getClient().getPhoto());
        dto.setText(review.getText());
        dto.setComplaint(review.getType() == ReviewType.COMPLAINT);
        if(review.getOwner() != null) {
            dto.setReviewedName(review.getOwner().getName() + " " + review.getOwner().getSurname());
            dto.setReviewedImage(review.getOwner().getPhoto());
        } else {
            dto.setReviewedName(review.getRentable().getName());
            dto.setReviewedImage(review.getRentable().getPictures().get(0));
        }
        return dto;
    }

    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
    public boolean resolveReview(int reviewId, String responseText, boolean allowed) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()) return false;
        try {
            Review review = optionalReview.get();
            Client client = review.getClient();
            Owner owner = review.getOwner() == null ? review.getRentable().getOwner() : review.getOwner();
            if (review.getType() == ReviewType.REVIEW_WITH_RATING)  {
                if(allowed) {
                    emailService.sendNotificaitionToUsername(owner.getUsername(), "Review",
                        "Dear " + owner.getUsername() + ",<br><br>We inform you that the user with username "
                                + client.getUsername() + " has just review you or your rentable with a rating of " + review.getScore() + " with" +
                                " text \"" + review.getText() + "\"." +
                                "<br><br>Sincerely,<br>Rivera.");
                    review.setStatus(ReviewStatus.ACCEPTED);
                } else {
                    emailService.sendNotificaitionToUsername(client.getUsername(), "Review",
                            "Dear " + client.getUsername() + ",\n\nWe inform you that your review with text \" "+ review.getText()+"\" " +
                                    "didn't get approved by the admin team. Reason: " + responseText +
                                    "\n\nSincerely,\n Rivera.");
                    review.setStatus(ReviewStatus.DECLINED);
                }

            } else {
                emailService.sendNotificaitionToUsername(owner.getUsername(), "Complaint",
                        "Dear " + owner.getUsername() + ",<br><br>We inform you that the user with username "
                                + client.getUsername() + " has just filed a complaint on you or your rentable with " +
                                " text \"" + review.getText() + "\". The admin team has responded with \"" + responseText + "\"" +
                                "<br><br>Sincerely,<br> Rivera.");

                emailService.sendNotificaitionToUsername(client.getUsername(), "Complaint",
                        "Dear " + client.getUsername() + ",<br><br>We inform you that your complaint has just been reviewed by the admin team. " +
                                "The admin team has responded with: " + responseText +
                                "<br><br>Sincerely,<br>Rivera.");
                review.setStatus(ReviewStatus.ACCEPTED);
            }
            if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
                Random random = new Random();
                int r = random.nextInt(1, 10);
                Thread.sleep(r* 100L);
            }
            reviewRepository.save(review);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
