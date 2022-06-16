package com.tim20.rivera.service;

import com.tim20.rivera.model.AvailabilityPattern;
import com.tim20.rivera.model.Calendar;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.repository.AvailabilityRepository;
import com.tim20.rivera.repository.CalendarRepository;
import com.tim20.rivera.repository.RentableRepository;
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
public class AvailabilityService {


    @Autowired
    RentableRepository rentableRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    public void defineAvailability(AvailabilityRequest availabilityRequest) {
        Rentable rentable = rentableRepository.getById(availabilityRequest.getRentableId());
        List<Calendar> cals = rentable.getCalendars();
        if(cals == null) {
            rentable.setCalendars(new ArrayList<>());
            cals = rentable.getCalendars();
        }

        if(cals.isEmpty()) {
            Calendar calendar = new Calendar();
            calendar.setPatternsOfAvailability(new ArrayList<>());
            calendar.setRentable(rentable);
            calendarRepository.save(calendar);
            cals.add(calendar);
        }

        Calendar cal = cals.get(0);
        for (int i = 0; i < availabilityRequest.getPatterns().size(); i++) {
            AvailabilityPattern pattern = new AvailabilityPattern();
            pattern.setPatternStart(availabilityRequest.getPatterns().get(i).get(0));
            pattern.setPatternEnd(availabilityRequest.getPatterns().get(i).get(1));
            pattern.setAddition(availabilityRequest.getAddition());
            pattern.setStartDateTime(availabilityRequest.getSelectedStartDate());
            pattern.setEndDateTime(availabilityRequest.getSelectedEndDate());
            availabilityRepository.save(pattern);
            cal.getPatternsOfAvailability().add(pattern);
        }

        calendarRepository.save(cal);
        rentableRepository.save(rentable);
    }

    public void testAvailability() {
        Rentable rentable = rentableRepository.getById(1);
        AvailabilityRequest request1 = new AvailabilityRequest();
        request1.setAddition(true);
        request1.setPatterns(Arrays.asList(Arrays.asList("0 0 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 15 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request1.setRepeat("week");
        request1.setRentableId(1);
        request1.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request1.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request1);

        List<Availability> avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

        AvailabilityRequest request2 = new AvailabilityRequest();
        request2.setAddition(false);
        request2.setPatterns(Arrays.asList(Arrays.asList("0 0 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 12 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request2.setRepeat("week");
        request2.setRentableId(1);
        request2.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request2.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request2);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);


        AvailabilityRequest request3 = new AvailabilityRequest();
        request3.setAddition(true);
        request3.setPatterns(Arrays.asList(Arrays.asList("0 0 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 17 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request3.setRepeat("week");
        request3.setRentableId(1);
        request3.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request3.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request3);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

        AvailabilityRequest request4 = new AvailabilityRequest();
        request4.setAddition(true);
        request4.setPatterns(Arrays.asList(Arrays.asList("0 0 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 30 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request4.setRepeat("week");
        request4.setRentableId(1);
        request4.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request4.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request4);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);


        AvailabilityRequest request5 = new AvailabilityRequest();
        request5.setAddition(false);
        request5.setPatterns(Arrays.asList(Arrays.asList("0 30 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 25 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request5.setRepeat("week");
        request5.setRentableId(1);
        request5.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request5.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request5);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

        //"{"patterns":["0 0,0 9,15 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI"],"selectedStartDate":"2022-04-04T00:00:00","selectedEndDate":"2022-04-09T00:00:00","repeat":"week","addition":true}"

    }

    private void prettyPrintAvailabilities(List<Availability> avs) {
        System.out.println("\n\n\n-----------------------------\n\n\n");
        for (int i = 0; i < avs.size(); i++) {
            var currStart = avs.get(i).getStartDateTime();
            var currEnd = avs.get(i).getEndDateTime();
            if(i != 0) {
                var prevStart = avs.get(i - 1).getStartDateTime();
                if(!LocalDate.from(prevStart).isEqual(LocalDate.from(currStart))) {
                    System.out.println("============");
                    System.out.println(currStart.getDayOfMonth() + "." + currStart.getMonth() + ".");
                }
            }
            System.out.println(currStart.getHour() + ":" + currStart.getMinute() + " -- " + currEnd.getHour() + ":" + currEnd.getMinute());
        }
    }

    private void defineAvailabilityWeek(AvailabilityRequest availabilityRequest) {
//        CronExpression cron = CronExpression.parse(availabilityRequest.getPatterns().get(0));
//        LocalDateTime next = cron.next(availabilityRequest.getSelectedStartDate().minusHours(1));
//        LocalDateTime end = availabilityRequest.getSelectedEndDate();
//        end = end.plusWeeks(2);
//        while(next.isBefore(end)) {
//            System.out.println(next);
//            next = cron.next(next);
//        }
    }

    public List<Availability> getAvailabilities(Integer id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Rentable rentable = rentableRepository.getById(id);
        return getAvailabilities(rentable, fromDateTime, toDateTime);
    }

    public List<Availability> getAvailabilities(Rentable rentable, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<Availability> availabilities = new ArrayList<>();
        HashMap<LocalDate, List<Availability>> availabilityPerDay = new HashMap<>();
        var start = fromDateTime;
        var end = toDateTime;
        while (start.isBefore(end) || start.isEqual(end)) {
            availabilityPerDay.put(LocalDate.from(start), new ArrayList<>());
            if(rentable.getCalendars().size() == 0) return availabilities;
            for (var pattern : rentable.getCalendars().get(0).getPatternsOfAvailability()) {
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
        availabilities.sort(Comparator.comparing(Availability::getStartDateTime));
        fixOverlaps(availabilities);
        //
        rentable.getReservations().forEach(res -> {
            Availability av = new Availability();
            av.setStartDateTime(res.getStartDateTime());
            av.setEndDateTime(res.getEndDateTime());
            subtractAvailability(av, availabilities);
        });

        rentable.getDiscounts().forEach(
                res -> {
                    Availability av = new Availability();
                    av.setStartDateTime(res.getStartDateTime());
                    av.setEndDateTime(res.getEndDateTime());
                    subtractAvailability(av, availabilities);
                }
        );
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
            if(midnightOverlap(prev, curr)) {
                curr.setStartDateTime(prev.getStartDateTime());
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

    private boolean midnightOverlap(Availability prev, Availability curr) {
        var prevEnd = prev.getEndDateTime();
        var currStart = curr.getStartDateTime();
        if(!prevEnd.plusMinutes(2).isAfter(currStart)) {
            return false;
        }
        if(prevEnd.getHour() == 23 && prevEnd.getMinute() == 59) {
            return currStart.getHour() == 0 && currStart.getMinute() == 0;
        }
        return false;
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
        subtractAvailability(availability, availabilities);
        availabilities = availabilities.stream().sorted(Comparator.comparing(Availability::getStartDateTime)).collect(Collectors.toList());
        availabilitiesPerDay.put(LocalDate.from(start), availabilities);
    }

    private void subtractAvailability(Availability availability, List<Availability> availabilities) {
        var patStart = availability.getStartDateTime();
        var patEnd = availability.getEndDateTime();
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
        availabilities = availabilities.stream().sorted(Comparator.comparing(Availability::getStartDateTime)).collect(Collectors.toList());
    }


    private Pair<LocalDateTime, LocalDateTime> getStampsForDate(LocalDateTime date, AvailabilityPattern pattern) {
        date = date.minusSeconds(1);
        CronExpression cronStart = CronExpression.parse(pattern.getPatternStart());
        CronExpression cronEnd = CronExpression.parse(pattern.getPatternEnd());
        LocalDateTime start = cronStart.next(date);
        LocalDateTime end = cronEnd.next(date);
        return Pair.of(start, end);
    }

    private Boolean isBeforeOrEquals(LocalDateTime t1, LocalDateTime t2) {
        return (t1.isBefore(t2) || t1.isEqual(t2));
    }

    private Boolean isAfterOrEquals(LocalDateTime t1, LocalDateTime t2) {
        return (t1.isAfter(t2) || t1.isEqual(t2));
    }


    public void testBigAvailability() {
        Rentable rentable = rentableRepository.getById(1);
        AvailabilityRequest request1 = new AvailabilityRequest();
        request1.setAddition(true);
        request1.setPatterns(Arrays.asList(Arrays.asList("0 0 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 10 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request1.setRepeat("week");
        request1.setRentableId(1);
        request1.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request1.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request1);

        List<Availability> avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

        AvailabilityRequest request2 = new AvailabilityRequest();
        request2.setAddition(true);
        request2.setPatterns(Arrays.asList(Arrays.asList("0 0 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 12 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request2.setRepeat("week");
        request2.setRentableId(1);
        request2.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request2.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request2);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);


        AvailabilityRequest request3 = new AvailabilityRequest();
        request3.setAddition(true);
        request3.setPatterns(Arrays.asList(Arrays.asList("0 30 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 14 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request3.setRepeat("week");
        request3.setRentableId(1);
        request3.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request3.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request3);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

        AvailabilityRequest request4 = new AvailabilityRequest();
        request4.setAddition(true);
        request4.setPatterns(Arrays.asList(Arrays.asList("0 0 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 35 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request4.setRepeat("week");
        request4.setRentableId(1);
        request4.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request4.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        defineAvailability(request4);

        avs = getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        prettyPrintAvailabilities(avs);

    }

    public void removeAvailabilities(Integer rentableId) {
        Rentable rentable = rentableRepository.getById(rentableId);
        List<Calendar> cals = rentable.getCalendars();
        if(cals == null) {
            rentable.setCalendars(new ArrayList<>());
            cals = rentable.getCalendars();
        }
        if(!cals.isEmpty()) {
            cals.get(0).setPatternsOfAvailability(new ArrayList<>());
            calendarRepository.save(cals.get(0));
        }
        rentableRepository.save(rentable);
    }
}