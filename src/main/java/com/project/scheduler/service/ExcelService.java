package com.project.scheduler.service;

import java.io.ByteArrayInputStream;

public interface ExcelService {

    ByteArrayInputStream getScheduleForStudentForSpecifiedWeek(final long id, final int week);
    ByteArrayInputStream getScheduleForStudent(final long id);

}
