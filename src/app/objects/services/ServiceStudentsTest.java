package app.objects.services;

import app.exceptions.ValidationException;
import app.objects.entities.Student;
import app.objects.repositories.StudentRepository;
import app.objects.validations.StudentValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceStudentsTest {


    private ServiceStudents ss;

    @BeforeEach
    void setUp() {
        StudentValidation sv = new StudentValidation();
        StudentRepository repo = new StudentRepository(sv);
        ss = new ServiceStudents(repo);

        try {
            ss.addStudent("1", "odrin", "traian",111,"a", "aaa");
            ss.addStudent("2", "andrei", "ion",111,"a" ,"caa");
            ss.addStudent("3", "ana", "andreea",111,"a", "daa");
            ss.addStudent("4", "cosmin", "victor",111,"a", "faa");
        }catch (ValidationException e){ }
    }

    @Test
    void addStudent() {

        try {

            assert ss.addStudent("201", "abc", "cca", 111,"a","ggd") == null;
            assert ss.findStudent("201").getId().equals("201");

            Student s = ss.addStudent("1","a","a",111,"a","a");
            assert s!=null;
            assert s.getId().equals("1");
            assert s.getFirstName().equals("a");

            ss.addStudent("", "", "", 111,"a","");

        }catch (ValidationException e){ assert true; }

    }

    @Test
    void removeStudent() {
        assert ss.removeStudent("1").getFirstName().equals("odrin");
        assert ss.removeStudent("1") == null;

    }

    @Test
    void updateStudent() {
        assert ss.updateStudent("1","Oprisanu","Odrin",111,"a","aaa") == null;
        Student s = ss.findStudent("1");

        assert s!=null;
        assert s.getFirstName().equals("Oprisanu");
        assert s.getLastName().equals("Odrin");

        s =  ss.updateStudent("782","abc","ccc",111,"a","ddd");
        assert s != null;
        assert s.getFirstName().equals("abc");




    }

    @Test
    void getAllStudents() {

        int i = 1;
        for(Student s:ss.getAllStudents())
        {
            assert s.getId().equals(String.valueOf(i));
            i++;
        }
    }

    @Test
    void findStudent() {
        assert ss.findStudent("1").getFirstName().equals("odrin");
        assert ss.findStudent("5002") == null;
    }
}