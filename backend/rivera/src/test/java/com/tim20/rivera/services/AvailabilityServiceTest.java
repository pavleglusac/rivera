package com.tim20.rivera.services;

import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.AvailabilityService;
import com.tim20.rivera.util.Availability;
import com.tim20.rivera.util.AvailabilityRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AvailabilityServiceTest {


    @Autowired
    RentableRepository rentableRepository;

    @Autowired
    AvailabilityService availabilityService;

    @Test
    @Transactional
    public void testAvailabilities() {
        int rentableId = 5;

        Rentable rentable = rentableRepository.getById(rentableId);
        AvailabilityRequest request1 = new AvailabilityRequest();
        request1.setAddition(true);
        request1.setPatterns(Arrays.asList(Arrays.asList("0 0 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 15 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request1.setRepeat("week");
        request1.setRentableId(rentableId);
        request1.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request1.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request1);

        List<Availability> avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));


        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 14, 9, 0));

        AvailabilityRequest request2 = new AvailabilityRequest();
        request2.setAddition(false);
        request2.setPatterns(Arrays.asList(Arrays.asList("0 0 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 12 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request2.setRepeat("week");
        request2.setRentableId(rentableId);
        request2.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request2.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request2);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 14, 12, 0));


        AvailabilityRequest request3 = new AvailabilityRequest();
        request3.setAddition(true);
        request3.setPatterns(Arrays.asList(Arrays.asList("0 0 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 17 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request3.setRepeat("week");
        request3.setRentableId(rentableId);
        request3.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request3.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request3);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 21, 12, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 14, 12, 0));

        AvailabilityRequest request4 = new AvailabilityRequest();
        request4.setAddition(true);
        request4.setPatterns(Arrays.asList(Arrays.asList("0 0 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 30 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request4.setRepeat("week");
        request4.setRentableId(rentableId);
        request4.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request4.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request4);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 8, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 12, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 8, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 14, 12, 0));


        AvailabilityRequest request5 = new AvailabilityRequest();
        request5.setAddition(false);
        request5.setPatterns(Arrays.asList(Arrays.asList("0 30 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 25 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request5.setRepeat("week");
        request5.setRentableId(rentableId);
        request5.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request5.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request5);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 29, 8, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 29, 12, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 29, 9, 25));
    }

    private boolean checkIfAvailable(List<Availability> avs, LocalDateTime dateTime) {
        return avs.stream()
               .filter(x-> x.getStartDateTime().getMonth() == dateTime.getMonth() && x.getStartDateTime().getDayOfMonth() == dateTime.getDayOfMonth())
               .filter(x -> x.getStartDateTime().getHour() == dateTime.getHour() && x.getStartDateTime().getMinute() == dateTime.getMinute())
               .count() > 0;
    }


    @Test
    @Transactional
    public void testBigAvailability() {
        int rentableId = 5;

        Rentable rentable = rentableRepository.getById(rentableId);
        AvailabilityRequest request1 = new AvailabilityRequest();
        request1.setAddition(true);
        request1.setPatterns(Arrays.asList(Arrays.asList("0 0 9 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 10 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request1.setRepeat("week");
        request1.setRentableId(rentableId);
        request1.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request1.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request1);

        List<Availability> avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 9, 0));

        AvailabilityRequest request2 = new AvailabilityRequest();
        request2.setAddition(true);
        request2.setPatterns(Arrays.asList(Arrays.asList("0 0 11 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 12 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request2.setRepeat("week");
        request2.setRentableId(rentableId);
        request2.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request2.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request2);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 21, 11, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 14, 11, 0));


        AvailabilityRequest request3 = new AvailabilityRequest();
        request3.setAddition(true);
        request3.setPatterns(Arrays.asList(Arrays.asList("0 30 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 0 14 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request3.setRepeat("week");
        request3.setRentableId(rentableId);
        request3.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request3.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request3);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 21, 11, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 13, 30));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 20, 9, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 11, 11, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 5, 13, 30));

        AvailabilityRequest request4 = new AvailabilityRequest();
        request4.setAddition(true);
        request4.setPatterns(Arrays.asList(Arrays.asList("0 0 8 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI", "0 35 13 * APR,MAY,JUN,JUL MON,TUE,WED,THU,FRI")));
        request4.setRepeat("week");
        request4.setRentableId(rentableId);
        request4.setSelectedStartDate(LocalDateTime.of(2022, 4, 4, 0, 0));
        request4.setSelectedEndDate(LocalDateTime.of(2022, 4, 9, 0, 0));
        availabilityService.defineAvailability(request4);

        avs = availabilityService.getAvailabilities(rentable, LocalDateTime.of(2022, 4, 1, 0, 0), LocalDateTime.of(2022, 4, 30, 0, 0));

        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 9, 0));
        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 21, 11, 0));
        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 15, 13, 30));
        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 20, 9, 0));
        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 11, 11, 0));
        assert !checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 5, 13, 30));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 19, 8, 0));
        assert checkIfAvailable(avs, LocalDateTime.of(2022, Month.APRIL, 21, 8, 0));
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
}
