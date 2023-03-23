package com.example.demo.handler;

import com.example.demo.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class Handler {

    private Validator validator;
    private Date nowTime;
    private Date nextDayTime;
    private Date lastMinute;
    private Date dateTime;
    private String dateTimeFormat = "yyyy-MM-dd hh:mm:ss";
    private String nextDayTimeStr = "2023-03-24 00:00:00";
    private String lastMinuteStr = "2023-03-23 23:59:00";
    private String dateTimeStr = "2023-03-24 18:00:00";
    private String schedulePath = "C:\\dev\\ck\\java\\spring-app\\src\\main\\java\\com\\example\\demo\\model\\sample.pdf";
    private String instructionsPath = "C:\\dev\\ck\\java\\spring-app\\src\\main\\java\\com\\example\\demo\\model\\sample.pdf";

    @Autowired
    public Handler(Validator validator) throws ParseException {
        this.validator = validator;
        nextDayTime = new SimpleDateFormat(dateTimeFormat).parse(nextDayTimeStr);
        lastMinute =new SimpleDateFormat(dateTimeFormat).parse(lastMinuteStr);
        dateTime = new SimpleDateFormat(dateTimeFormat).parse(dateTimeStr);
    }

    private ResponseEntity<InputStreamResource> getPdfToServe(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=sample.pdf");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .body(resource);
    }
    public ResponseEntity<InputStreamResource> getInstructionsFile() throws FileNotFoundException {
        return getPdfToServe(instructionsPath);
    }

    public ResponseEntity<InputStreamResource> getScheduleFile() throws FileNotFoundException {
        return getPdfToServe(schedulePath);
    }

    public String getTime() throws ParseException {
//        nowTime = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHoursMinutes(5, 30)));
        nowTime = new SimpleDateFormat(dateTimeFormat).parse("2023-03-24 01:59:01");

        Long duration;
        String response = "TIME REMAINING\n %s hours, %s minutes\n for %s!";

        if(nowTime.before(nextDayTime)){
            duration = nextDayTime.getTime() - nowTime.getTime();
            if(nowTime.before(lastMinute)) {
                return response.formatted(TimeUnit.MILLISECONDS.toHours(duration), TimeUnit.MILLISECONDS.toMinutes(duration) % 60, "anniversary");
            }
            else return TimeUnit.MILLISECONDS.toSeconds(duration) + " seconds remaining\nfor anniversary!";
        }

        else if(nowTime.before(dateTime)){
            duration = dateTime.getTime() - nowTime.getTime();
            return "HAPPY ANNIVERSARY ASHY!!!\n\n"+
                    response.formatted(TimeUnit.MILLISECONDS.toHours(duration), TimeUnit.MILLISECONDS.toMinutes(duration) % 60, "date") +
                    " See schedule at: " + "http://localhost:8080/schedule";
        }

        else{
            return "HAPPY ANNIVERSARY ASHY!!!\n See schedule at: "
                    +"http://localhost:8080/schedule  "
                    +"\n\n See instructions at: "
                    +"http://localhost:8080/instructions";
        }
    }
}
