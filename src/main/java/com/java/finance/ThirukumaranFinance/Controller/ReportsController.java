package com.java.finance.ThirukumaranFinance.Controller;

import com.java.finance.ThirukumaranFinance.Repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/restservices/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final LineRepository lineRepository;

    @GetMapping("/reports")
    public void dummy(@RequestParam String dummyDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(dummyDate, formatter);

        LocalDate belowTwoDaysDate = parsedDate.minusDays(2);
        LocalDate belowFourDaysDate = parsedDate.minusDays(4);

        System.out.println("2 days: "+belowTwoDaysDate+":::::::"+belowFourDaysDate);

        //lineRepository.findByTwoDate(belowTwoDaysDate, belowFourDaysDate);


//        YourEntity entity = new YourEntity();
//        entity.setDateColumn(parsedDate);
//
//        yourEntityRepository.save(entity);

    }

}
