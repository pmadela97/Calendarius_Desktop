package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CEvent implements Serializable {
        int id;
        private String name;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private String localization;
        private String description;
        private Priority priority;
        private static String dateDisplayPattern;
        private String startDateTimeDisplayFormat;
        private String endDateTimeDisplayFormat;
        private boolean status;

    CEvent(String name, LocalDateTime startDateTime){
    this.name = name;
    this.startDateTime = startDateTime;
    dateDisplayPattern = "dd-MM-yyyy HH:mm";
        startDateTimeDisplayFormat = startDateTime.format(DateTimeFormatter.ofPattern(dateDisplayPattern));
    }


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setName(String name) throws NameNullPointerException {
        if(name ==null || name.equals("")) throw new NameNullPointerException("Nazwa wydarzenia nie może być pusta");
        this.name = name;
    }

    public String getStartDateTimeDisplayFormat() {
        return startDateTimeDisplayFormat;
    }

    public String getEndDateTimeDisplayFormat() {
        return endDateTimeDisplayFormat;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) throws StartDateTimeNullPointerException {
        if(startDateTime == null) throw new StartDateTimeNullPointerException("Początek wydarzenia nie może być pusty");
        this.startDateTime = startDateTime;
        startDateTimeDisplayFormat = startDateTime.format(DateTimeFormatter.ofPattern(dateDisplayPattern));

    }



    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime){
        this.endDateTime = endDateTime;
        endDateTimeDisplayFormat = endDateTime.format(DateTimeFormatter.ofPattern(dateDisplayPattern));

    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization){

        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){

        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void choosePriority(Priority p){
    priority = p;
    }

    public void choosePriority(){
        long duration = ChronoUnit.DAYS.between(LocalDateTime.now(),getStartDateTime());
        System.out.println(duration);
        if(duration>14){ choosePriority(Priority.NORMAL); return;}
        if(duration<=14 && duration > 7) {choosePriority(Priority.MEDIUM); return;}
        if(duration<= 7 && duration> 3) {choosePriority(Priority.HIGH);return;}
        if(duration<= 3 && duration>=0) {choosePriority(Priority.CRITIC);return;}
        choosePriority(Priority.NORMAL);
    }



    public static void setDateDisplayPattern(String pattern){
        if(pattern == null) return;
        dateDisplayPattern = pattern;
    }

public static String getDateDisplayPattern(){
        return dateDisplayPattern;
}
    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        sB.append(getName() != null? getName() + " " :"BRAK ").append(getStartDateTime() != null? getStartDateTime() + " " :"BRAK ")
          .append(getEndDateTime() != null? getEndDateTime() + " " :"BRAK ").append(getLocalization() != null? getLocalization() + " " :"BRAK ")
          .append(getDescription() != null? getDescription():"BRAK");
        return   sB.toString();
    }

    public void setExecStatus(boolean execStatus){
    this.status = execStatus;
    }
    public boolean getExecStatus(){
        return status;
    }
}
