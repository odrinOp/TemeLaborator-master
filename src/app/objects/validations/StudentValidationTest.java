package app.objects.validations;

import app.exceptions.ValidationException;
import app.objects.entities.Student;
import org.junit.jupiter.api.Test;

class StudentValidationTest {

    @Test
    void validate() {
        StudentValidation sv = new StudentValidation();
        Student s = new Student("1","odrin","traian",225,"ooir2582@scs.ubbcluj.ro","aaa");

        try {
            sv.validate(s);
            assert true;
        } catch (ValidationException ignored) {}

        s.setId(null);
        try {
            sv.validate(s);
        } catch (ValidationException e) {
           assert e.getMessage().equals("Invalid ID!\n");
        }

        s.setId("");
        try {
            sv.validate(s);
        } catch (ValidationException e) {
            assert e.getMessage().equals("Invalid ID!\n");
        }

        s.setFirstName("");
        try {
            sv.validate(s);
        } catch (ValidationException e) {
            assert e.getMessage().equals("Invalid ID!\nInvalid first name!\n");
        }


        s.setLastName(null);
        s.setGuidingTeacher("");
        try {
            sv.validate(s);
        } catch (ValidationException e) {
            assert e.getMessage().equals("Invalid ID!\nInvalid first name!\nInvalid last name!\nInvalid guiding teacher's name!\n");
        }

    }
}