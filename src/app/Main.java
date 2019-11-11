package app;

import app.objects.entities.UI;
import app.objects.entities.YearStructure;
import app.objects.repositories.GradeRepository;
import app.objects.repositories.HomeworkRepository;
import app.objects.repositories.StudentRepository;
import app.objects.services.ServiceGrades;
import app.objects.services.ServiceHomework;
import app.objects.services.ServiceStudents;
import app.objects.validations.GradeValidation;
import app.objects.validations.HomeworkValidation;
import app.objects.validations.StudentValidation;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        StudentValidation sv = new StudentValidation();
        StudentRepository sr = new StudentRepository(sv);


        YearStructure ys = new YearStructure("2019");

        HomeworkValidation hv = new HomeworkValidation();
        HomeworkRepository hs = new HomeworkRepository(hv);

        GradeValidation gv = new GradeValidation();
        GradeRepository gr = new GradeRepository(gv);

        ServiceStudents ss = new ServiceStudents(sr);
        ServiceHomework sh = new ServiceHomework(hs,ys);
        ServiceGrades sg = new ServiceGrades(gr,sr,hs,ys);


        UI ui = new UI(ss,sh,sg);
        ui.run();

    }


}
