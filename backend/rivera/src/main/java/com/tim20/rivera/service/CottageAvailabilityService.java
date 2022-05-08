package com.tim20.rivera.service;

import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.AvailabilityPattern;
import com.tim20.rivera.model.Calendar;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.repository.AdventureRepository;
import com.tim20.rivera.repository.AvailabilityRepository;
import com.tim20.rivera.repository.CalendarRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.util.Availability;
import com.tim20.rivera.util.AvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CottageAvailabilityService {

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;


    public List<Availability> getAvailabilities(Integer id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Cottage cottage = cottageRepository.getById(id);
        return getAvailabilities(cottage, fromDateTime, toDateTime);
    }


    public List<Availability> getAvailabilities(Cottage cottage, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<Availability> availabilities = new ArrayList<>();
        HashMap<LocalDate, List<Availability>> availabilityPerDay = new HashMap<>();
        var start = fromDateTime;
        var end = toDateTime;
        while (start.isBefore(end) || start.isEqual(end)) {
            availabilityPerDay.put(LocalDate.from(start), new ArrayList<>());
            if(cottage.getCalendars().size() == 0) return availabilities;
            for (var pattern : cottage.getCalendars().get(0).getPatternsOfAvailability()) {
                if (pattern.getAddition()) {
                    addAndMergePatterns(pattern, availabilityPerDay, start, end);
                } else {
                    subtractAndSplitPatterns(pattern, availabilityPerDay, start, end);
                }
            }
            start = start.plusDays(1);
        }
        for (var a : availabilityPerDay.values()) {
            availabilities.addAll(a);
        }
        return availabilities;
    }

    private void addAndMergePatterns(AvailabilityPattern pattern, HashMap<LocalDate, List<Availability>> availabilitiesPerDay, LocalDateTime start, LocalDateTime end) {
        List<Availability> availabilities = availabilitiesPerDay.get(LocalDate.from(start));
        Pair<LocalDateTime, LocalDateTime> pair = getStampsForDate(start, pattern);
        LocalDateTime patStart = pair.getFirst();
        LocalDateTime patEnd = pair.getSecond();
        if(patEnd.isAfter(end) || !LocalDate.from(patStart).isEqual(LocalDate.from(start))) {
            return;
        }
        if (LocalDate.from(start).isBefore(LocalDate.from(pattern.getStartDateTime()))) {
            //System.out.println("returned " + start + " " + pattern.getStartDateTime());
            return;
        }
        Availability availability = new Availability();
        availability.setStartDateTime(patStart);
        availability.setEndDateTime(patEnd);
        boolean toAdd = true;
        for (Availability av : availabilities) {
            var s = av.getStartDateTime();
            var e = av.getEndDateTime();
            // [ ( ] ) && [ ( ) ]
            if (s.isBefore(patStart)) {
                if (isBeforeOrEquals(e, patEnd) && isAfterOrEquals(e, patStart)) {
                    av.setEndDateTime(patEnd);
                    toAdd = false;
                    break;
                }
                if (e.isAfter(patEnd)) {
                    toAdd = false;
                    break;
                }
            }
            // (  [  )  ]
            if(s.isAfter(patStart) && isBeforeOrEquals(s, patEnd) && isAfterOrEquals(e, patEnd)) {
                av.setStartDateTime(patStart);
                toAdd = false;
                break;
            }
            // ( [ ] )
            if (isAfterOrEquals(s, patStart) && isBeforeOrEquals(e, patEnd)) {
                av.setStartDateTime(patStart);
                av.setEndDateTime(patEnd);
                toAdd = false;
                break;
            }
        }

        if(toAdd) {
            availabilitiesPerDay.get(LocalDate.from(patStart)).add(availability);
        } else {
            fixOverlaps(availabilities);
        }
    }

    private void fixOverlaps(List<Availability> availabilities) { // does not fix extremely large availabilities
        Set<Integer> to_remove = new HashSet<>();
        for (int i = 1; i < availabilities.size(); i++) {
            var prev = availabilities.get(i - 1);
            var curr = availabilities.get(i);
            if(isAfterOrEquals(prev.getEndDateTime(), curr.getStartDateTime()) && prev.getStartDateTime().isBefore(curr.getStartDateTime())) {
                curr.setStartDateTime(prev.getStartDateTime());
                if(prev.getEndDateTime().isAfter(curr.getEndDateTime())) {
                    curr.setEndDateTime(prev.getEndDateTime());
                }
                to_remove.add(i - 1);
            }
        }
        for (int i = availabilities.size() - 1; i >= 0; i--)
        {
            if(to_remove.contains(i)) {
                availabilities.remove(i);
            }
        }
    }

    private void subtractAndSplitPatterns(AvailabilityPattern pattern, HashMap<LocalDate, List<Availability>> availabilitiesPerDay, LocalDateTime start, LocalDateTime end) {
        List<Availability> availabilities = availabilitiesPerDay.get(LocalDate.from(start));
        Pair<LocalDateTime, LocalDateTime> pair = getStampsForDate(start, pattern);
        LocalDateTime patStart = pair.getFirst();
        LocalDateTime patEnd = pair.getSecond();
        if(patEnd.isAfter(end) || !LocalDate.from(patStart).isEqual(LocalDate.from(start))) {
            return;
        }
        if (start.isBefore(pattern.getStartDateTime())) {
            return;
        }
        Availability availability = new Availability();
        availability.setStartDateTime(patStart);
        availability.setEndDateTime(patEnd);
        ArrayList<Availability> to_remove = new ArrayList<>();
        ArrayList<Availability> to_add = new ArrayList<>();

        for (Availability av : availabilities) {
            var s = av.getStartDateTime();
            var e = av.getEndDateTime();
            // ( [  ] )
            if (isAfterOrEquals(s, patStart) && isBeforeOrEquals(e, patEnd)) {
                to_remove.add(av);
                continue;
            }
            // ( [ ) ]
            if (isAfterOrEquals(s, patStart) && isAfterOrEquals(e, patEnd) && s.isBefore(patEnd)) {
                av.setStartDateTime(patEnd);
                continue;
            }
            // [ ( ] )
            if(s.isBefore(patStart) && isBeforeOrEquals(e, patEnd) && e.isAfter(patStart)) {
                av.setEndDateTime(patStart);
                continue;
            }
            // [ ( ) ]
            if(s.isBefore(patStart) && e.isAfter(patEnd)) {
                av.setEndDateTime(patStart);
                Availability added = new Availability();
                added.setStartDateTime(patEnd);
                added.setEndDateTime(e);
                to_add.add(added);
            }

        }

        availabilities.removeAll(to_remove);
        availabilities.addAll(to_add);
        availabilities = availabilities.stream().sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime())).collect(Collectors.toList());
        availabilitiesPerDay.put(LocalDate.from(start), availabilities);
    }


    private Pair<LocalDateTime, LocalDateTime> getStampsForDate(LocalDateTime date, AvailabilityPattern pattern) {
        date = date.minusSeconds(1);
        CronExpression cronStart = CronExpression.parse(pattern.getPatternStart());
        CronExpression cronEnd = CronExpression.parse(pattern.getPatternEnd());
        System.out.println(date);
        System.out.println(pattern.getPatternStart());
        System.out.println(pattern.getPatternEnd());
        LocalDateTime start = cronStart.next(date);
        LocalDateTime end = cronEnd.next(date);
        System.out.println(start);
        System.out.println(end);
        System.out.println("-----");
        return Pair.of(start, end);
    }

    private Boolean isBeforeOrEquals(LocalDateTime t1, LocalDateTime t2) {
        return (t1.isBefore(t2) || t1.isEqual(t2));
    }

    private Boolean isAfterOrEquals(LocalDateTime t1, LocalDateTime t2) {
        return (t1.isAfter(t2) || t1.isEqual(t2));
    }


}
