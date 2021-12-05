package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.LessonOrder;
import com.project.scheduler.entity.User;
import com.project.scheduler.entity.WeekDay;
import com.project.scheduler.repository.UserRepository;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    CourseService courseService;

    @Autowired
    UserRepository userRepository;

    @Override
    public ByteArrayInputStream getScheduleForStudent(long id) {
        return null;
    }

    @Override
    public ByteArrayInputStream getScheduleForStudentForSpecifiedWeek(final long id, final int week) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setWrapText(true);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                Sheet sheet = workbook.createSheet("Week " + week);
                sheet.setHorizontallyCenter(true);
                sheet.setVerticallyCenter(true);
                setHeaders(sheet);

                int rowIndex = 1;
                Map<WeekDay, List<Lesson>> schedule = courseService.findScheduleForWeek(1, id);
                for (Map.Entry<WeekDay, List<Lesson>> day : schedule.entrySet()) {
                    if (day.getValue().isEmpty())
                        continue;
                    int startingIndex = rowIndex;
                    Row dayRow = sheet.createRow(rowIndex++);
                    Cell dayCell = dayRow.createCell(0);
                    dayCell.setCellStyle(cellStyle);
                    dayCell.setCellValue(day.getKey().getDay());

                    Map<LessonOrder, List<Lesson>> lessonsByOrder = new TreeMap<>();
                    Stream.of(LessonOrder.values()).forEach(lessonOrder -> lessonsByOrder.put(lessonOrder, day.getValue().stream().filter(l -> l.getDate().getLessonOrder() == lessonOrder).collect(Collectors.toList())));
                    Row row = dayRow;
                    for (Map.Entry<LessonOrder, List<Lesson>> lessonsOnTheSameTime : lessonsByOrder.entrySet()) {
                        if (lessonsOnTheSameTime.getValue().isEmpty())
                            continue;
                        Cell timeCell = row.createCell(1);
                        timeCell.setCellStyle(cellStyle);
                        timeCell.setCellValue(lessonsOnTheSameTime.getKey().getOrder());
                        int sameTimeStartingIndex = rowIndex;
                        for (Lesson lesson : lessonsOnTheSameTime.getValue()) {
                            writeLesson(row, lesson, cellStyle);
                            row = sheet.createRow(rowIndex++);
                        }
                        if (rowIndex - sameTimeStartingIndex > 1)
                            sheet.addMergedRegion(new CellRangeAddress(sameTimeStartingIndex - 1, rowIndex - 2, 1, 1));
                    }
                    if (rowIndex - startingIndex > 1)
                        sheet.addMergedRegion(new CellRangeAddress(startingIndex, rowIndex - 1, 0, 0));
                }
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("Invalid user");
    }



    private void setHeaders(final Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        sheet.setColumnWidth(0, 5000);
        //First column is reserved for week day
        headerRow.createCell(1).setCellValue("Time");
        sheet.setColumnWidth(1, 3000);
        headerRow.createCell(2).setCellValue("Name");
        sheet.setColumnWidth(2, 3000);
        headerRow.createCell(3).setCellValue("Type");
        sheet.setColumnWidth(3, 3000);
        headerRow.createCell(4).setCellValue("Place");
        sheet.setColumnWidth(4, 3000);

    }

    private void writeLesson(final Row row, final Lesson lesson, final CellStyle style) {
        Cell cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue(lesson.getGroupCourse().getCourse().getName());

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue(lesson.getType().toString());

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue(lesson.getPlace());
    }

}
