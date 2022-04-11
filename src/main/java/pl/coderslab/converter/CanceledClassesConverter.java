//package pl.coderslab.converter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//import pl.coderslab.entity.CanceledClasses;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class CanceledClassesConverter implements Converter<String, CanceledClasses> {
//
//    @Override
//    public CanceledClasses convert(String source) {
//        CanceledClasses canceledClasses = new CanceledClasses();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        LocalDate localDate = (LocalDate.parse(source,formatter));
//        canceledClasses.setLocalDate(localDate);
//        return canceledClasses;
//    }
//}
