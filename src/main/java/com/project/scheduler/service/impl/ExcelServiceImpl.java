package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.ExcelService;
import com.project.scheduler.service.StudentService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    CourseService courseService;


    @Override
    public ByteArrayInputStream getLessonsForStudent(final long id) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet();

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Week");
            headerRow.createCell(2).setCellValue("Day of the week");
            headerRow.createCell(3).setCellValue("Lesson order");
            headerRow.createCell(4).setCellValue("Type");

            List<Lesson> lessons = courseService.findLessonsByEducationUserId(id);

            int rowIdx = 1;
            for (Lesson lesson : lessons) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(lesson.getGroupCourse().getCourse().getName());
                row.createCell(1).setCellValue(lesson.getDate().getWeek());
                row.createCell(2).setCellValue(lesson.getDate().getDayOfTheWeek().getDay());
                row.createCell(3).setCellValue(lesson.getDate().getLessonOrder().getOrder());
                row.createCell(4).setCellValue(lesson.getType().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}
