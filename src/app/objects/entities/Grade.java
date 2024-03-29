package app.objects.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Grade extends Entity<String> {

    private LocalDate date;
    private String Teacher;
    private int value;

    public Grade(String s, LocalDate date, String teacher, int value) {
        super(s);
        this.date = date;
        Teacher = teacher;
        this.value = value;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStudent(){
        String []s = getId().split("-");
        return s[0];
    }

    public String getHomework(){
        String []s = getId().split("-");
        return s[1];
    }


    @Override
    public String toString() {
        return getId()+"/"+getDate()+"/"+getTeacher()+"/"+getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return grade.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),date, Teacher, value);
    }
}
