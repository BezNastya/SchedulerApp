//package com.project.scheduler.entity;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.util.stream.Stream;
//
//@Converter(autoApply = true)
//public class WeekDayConverter implements AttributeConverter<WeekDay, String> {
//
//    @Override
//    public String convertToDatabaseColumn(WeekDay weekDay) {
//        if (weekDay == null)
//            return null;
//        return weekDay.getDay();
//    }
//
//    @Override
//    public WeekDay convertToEntityAttribute(String s) {
//        if (s == null)
//            return null;
//        return Stream.of(WeekDay.values())
//                .filter(c -> c.getDay().equals(s))
//                .findFirst().orElseThrow(IllegalArgumentException::new);
//    }
//}
