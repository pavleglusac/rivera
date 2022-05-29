package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;
    @Autowired
    private BoatOwnerRepository boatOwnerRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private RentableService rentableService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewService reviewService;


    public Person save(OwnerRequestDTO userRequestDTO) {
        switch (userRequestDTO.getType()){
            case "Cottage Owner": return cottageOwnerRepository.save(OwnerRequestDTOToCottageOwner(userRequestDTO));
            case "Boat Owner": return boatOwnerRepository.save(OwnerRequestDTOToBoatOwner(userRequestDTO));
            case "Fishing Instructor": return fishingInstructorRepository.save(OwnerRequestDTOToFishingInstructor(userRequestDTO));
            default: return null;
        }
    }

    public Owner findByUsername(String username) {
        return ownerRepository.findByUsername(username);
    }

    public Owner findByUsernameForLogin(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        if (owner.getStatus().equals(AccountStatus.ACTIVE)) {
            return owner;
        } else return null;
    }

    public CottageOwner OwnerRequestDTOToCottageOwner(OwnerRequestDTO userRequestDTO) {
        CottageOwner owner = new CottageOwner();
        owner.setAddress(userRequestDTO.getAddress());
        owner.setCity(userRequestDTO.getCity());
        owner.setCountry(userRequestDTO.getCountry());
        owner.setEmail(userRequestDTO.getEmail());
        owner.setName(userRequestDTO.getName());
        owner.setSurname(userRequestDTO.getSurname());
        owner.setPhoto(userRequestDTO.getPhoto());
        owner.setPhoneNumber(userRequestDTO.getPhoneNumber());
        owner.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        owner.setStatus(AccountStatus.WAITING);
        owner.setSignUpDescription(userRequestDTO.getDescription());
        owner.setUsername(userRequestDTO.getUsername());
        List<Role> roles = roleService.findByName("ROLE_COTTAGE_OWNER");
        owner.setRoles(roles);
        return owner;
    }

    public BoatOwner OwnerRequestDTOToBoatOwner(OwnerRequestDTO userRequestDTO) {
        BoatOwner owner = new BoatOwner();
        owner.setAddress(userRequestDTO.getAddress());
        owner.setCity(userRequestDTO.getCity());
        owner.setCountry(userRequestDTO.getCountry());
        owner.setEmail(userRequestDTO.getEmail());
        owner.setName(userRequestDTO.getName());
        owner.setSurname(userRequestDTO.getSurname());
        owner.setPhoto(userRequestDTO.getPhoto());
        owner.setPhoneNumber(userRequestDTO.getPhoneNumber());
        owner.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        owner.setStatus(AccountStatus.WAITING);
        owner.setSignUpDescription(userRequestDTO.getDescription());
        owner.setUsername(userRequestDTO.getUsername());
        List<Role> roles = roleService.findByName("ROLE_BOAT_OWNER");
        owner.setRoles(roles);
        return owner;
    }

    private FishingInstructor OwnerRequestDTOToFishingInstructor(OwnerRequestDTO userRequestDTO) {
        FishingInstructor instructor = new FishingInstructor();
        instructor.setAddress(userRequestDTO.getAddress());
        instructor.setCity(userRequestDTO.getCity());
        instructor.setCountry(userRequestDTO.getCountry());
        instructor.setEmail(userRequestDTO.getEmail());
        instructor.setName(userRequestDTO.getName());
        instructor.setSurname(userRequestDTO.getSurname());
        instructor.setPhoto(userRequestDTO.getPhoto());
        instructor.setPhoneNumber(userRequestDTO.getPhoneNumber());
        instructor.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        instructor.setStatus(AccountStatus.WAITING);
        instructor.setSignUpDescription(userRequestDTO.getDescription());
        instructor.setUsername(userRequestDTO.getUsername());
        instructor.setPhoneNumber(userRequestDTO.getBiography());
        List<Role> roles = roleService.findByName("ROLE_FISHING_INSTRUCTOR");
        instructor.setRoles(roles);
        return instructor;
    }

    public OwnerRequestDTO ownerToOwnerRequestDTO(Owner owner){
        if(owner == null)
            return null;
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setAddress(owner.getAddress());
        ownerRequestDTO.setCity(owner.getCity());
        ownerRequestDTO.setCountry(owner.getCountry());
        ownerRequestDTO.setEmail(owner.getEmail());
        ownerRequestDTO.setSurname(owner.getSurname());
        ownerRequestDTO.setUsername(owner.getUsername());
        ownerRequestDTO.setName(owner.getName());
        ownerRequestDTO.setPhoneNumber(owner.getPhoneNumber());
        ownerRequestDTO.setDescription(owner.getSignUpDescription());
        ownerRequestDTO.setStatus(owner.getStatus());
        return ownerRequestDTO;
    }

    public OwnerRequestDTO findByUsernameDTO(String username) {
        return ownerToOwnerRequestDTO(ownerRepository.findByUsername(username));
    }

    public void activateOwner(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        System.out.println(owner.getUsername());
        System.out.println(owner.getStatus());
        owner.setStatus(AccountStatus.ACTIVE);
        System.out.println(owner.getStatus());
        ownerRepository.save(owner);
    }

    public void deactivateOwner(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        owner.setStatus(AccountStatus.TERMINATED);
        ownerRepository.save(owner);
    }

    public boolean checkIfApprovedOrNonExistent(String username){
        Owner owner = ownerRepository.findByUsername(username);
        return owner == null || owner.getStatus()==AccountStatus.ACTIVE;
    }

    public List<ReviewProfileDTO> getReviewsForOwner(String username) {
        List<Rentable> rentables = rentableService.getRentablesForOwner(username);
        List<Review> reviews = new ArrayList<>();
        rentables.forEach(x -> reviews.addAll(x.getReviews()));
        return reviews.stream().map(reviewService::reviewToRPDTO).collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendance(String startDate, String endDate, String type) {
        startDate+= "T00:00:00";
        endDate+= "T00:00:00";
        LocalDateTime startDateTime = LocalDateTime.parse(startDate.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(endDate.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Owner loggedPerson = ((Owner) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<AttendanceDTO> attendanceDTOS = null;
        switch (type){
            case "month":
                attendanceDTOS = addMonths(reservationRepository.findMonthlyAttendance(loggedPerson.getUsername(),startDateTime,endDateTime
                    , false, type).stream().map(e -> new AttendanceDTO(((Timestamp)e[0]).toLocalDateTime(),((Long) e[1]).intValue())).collect(Collectors.toList()),startDateTime,endDateTime); break;
            case "year":
                attendanceDTOS = addYears(reservationRepository.findYearlyAttendance(loggedPerson.getUsername(),startDateTime,endDateTime
                    , false, type).stream().map(e -> new AttendanceDTO(((Timestamp)e[0]).toLocalDateTime(),((Long) e[1]).intValue())).collect(Collectors.toList()),startDateTime,endDateTime); break;
            case "week":
                attendanceDTOS = addWeeks(reservationRepository.findWeeklyAttendance(loggedPerson.getUsername(),startDateTime,endDateTime
                    , false, type).stream().map(e -> new AttendanceDTO(((Timestamp)e[0]).toLocalDateTime(),((Long) e[1]).intValue())).collect(Collectors.toList()),startDateTime,endDateTime); break;
        }
        return attendanceDTOS.stream().sorted(Comparator.comparing(AttendanceDTO::getTimestamp)).collect(Collectors.toList());
       }
    private List<AttendanceDTO>  addYears(List<AttendanceDTO> attendanceDTOS,LocalDateTime startDate, LocalDateTime endDate){
        startDate = startDate.withMonth(1);
        startDate = startDate.withDayOfMonth(1);
        while (startDate.isBefore(endDate)){
            LocalDateTime finalStartDate = startDate;
            if(attendanceDTOS.stream().noneMatch(e -> e.getTimestamp().isEqual(finalStartDate))){
                attendanceDTOS.add(new AttendanceDTO(startDate,0));
            }
            startDate = startDate.plusYears(1);
        }
        return attendanceDTOS;
    }
    private List<AttendanceDTO>  addMonths(List<AttendanceDTO> attendanceDTOS,LocalDateTime startDate, LocalDateTime endDate){
        startDate = startDate.withDayOfMonth(1);
        while (startDate.isBefore(endDate)){
            LocalDateTime finalStartDate = startDate;
            if(attendanceDTOS.stream().noneMatch(e -> e.getTimestamp().isEqual(finalStartDate))){
                attendanceDTOS.add(new AttendanceDTO(startDate,0));
            }
            startDate = startDate.plusMonths(1);
        }
        return attendanceDTOS;
    }
    private List<AttendanceDTO>  addWeeks(List<AttendanceDTO> attendanceDTOS,LocalDateTime startDate, LocalDateTime endDate){
        startDate = startDate.with(DayOfWeek.MONDAY);
        while (startDate.isBefore(endDate)){
            LocalDateTime finalStartDate = startDate;
            if(attendanceDTOS.stream().noneMatch(e -> e.getTimestamp().isEqual(finalStartDate))){
                attendanceDTOS.add(new AttendanceDTO(startDate,0));
            }
            startDate = startDate.plusWeeks(1);
        }
        return attendanceDTOS;
    }

    public List<IncomeFrontDTO> getIncome(String  startDate, String endDate) {
        Owner loggedPerson = ((Owner) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        startDate+= "T00:00:00";
        endDate+= "T00:00:00";
        LocalDateTime startDateTime = LocalDateTime.parse(startDate.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(endDate.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return  addRentables(reservationRepository.findIncome(loggedPerson.getUsername(), startDateTime,endDateTime).stream().map(this::incomeDTOToIncomeFrontDTO).collect(Collectors.toList()));
    }

    private List<IncomeFrontDTO> addRentables(List<IncomeFrontDTO> collect) {
        Owner loggedPerson = ((Owner) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        for(Rentable r : loggedPerson.getRentables()){
            if(collect.stream().noneMatch(e -> e.getRentableId().equals(r.getId()))){
                collect.add(new IncomeFrontDTO(r.getId(),r.getName(),0d));
            }
        }
        return collect;
    }


    public IncomeFrontDTO incomeDTOToIncomeFrontDTO(IncomeDTO incomeDTO){
        IncomeFrontDTO incomeFrontDTO = new IncomeFrontDTO();
        incomeFrontDTO.setIncome(incomeDTO.getIncome());
        incomeFrontDTO.setRentableId(incomeDTO.getRentable().getId());
        incomeFrontDTO.setRentableName(incomeDTO.getRentable().getName());
        return incomeFrontDTO;
    }

}
