package com.project.scheduler.service;

import java.io.ByteArrayInputStream;

public interface ExcelService {

    ByteArrayInputStream getLessonsForStudent(final long id);

}
