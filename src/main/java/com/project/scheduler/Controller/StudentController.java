package com.project.scheduler.Controller;

import org.slf4j.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);
    Marker myMarker = MarkerFactory.getMarker("StudentClassMarker");


    @GetMapping("/student/{id}")
    public String clientMCDRequest(@PathVariable String id) throws InterruptedException {
        MDC.put("studentId", id);

        logger.info(myMarker,"students {} has made a request", id);
        logger.info(myMarker,"Starting request");
        Thread.sleep(5000);
        logger.info(myMarker,"Finished request");
        MDC.clear();
        return "finished";
    }
}