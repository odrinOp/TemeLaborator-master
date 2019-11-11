package app.objects.services;

import app.exceptions.ValidationException;
import app.objects.entities.Grade;
import app.objects.entities.Homework;
import app.objects.entities.Student;
import app.objects.entities.YearStructure;
import app.objects.repositories.GradeRepository;
import app.objects.repositories.HomeworkRepository;
import app.objects.repositories.StudentRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ServiceGrades {
    private GradeRepository repo;
    private StudentRepository studentRepository;
    private HomeworkRepository homeworkRepository;
    private YearStructure structure;


    public ServiceGrades(GradeRepository repo, StudentRepository studentRepository, HomeworkRepository homeworkRepository,YearStructure structure) {
        this.repo = repo;
        this.studentRepository = studentRepository;
        this.homeworkRepository = homeworkRepository;
        this.structure = structure;
    }

    /**
     *
     * @param week -> the week in which the homework has been completed.
     * @param homeworkID
     * @return the maximum grade for a specific homework(integer).
     * @throws RuntimeException -> the id of homework doesn't exists.
     */
    public int getMaximumGrade(int week, String homeworkID){
        Homework h = homeworkRepository.findOne(homeworkID);
        if(h==null)
            throw new RuntimeException("This id doesn't exists!");

        if(week == -1){
            week = structure.getWeekByDate(LocalDate.now());
        }

        if(week == -1){
            throw new RuntimeException("We are on holiday");
        }

        if(h.getDeadlineWeek() > week)
            return 10;


        int diff = week -h.getDeadlineWeek();

        return 10-diff;
    }

    /**
     * Create the text for the file with the feedback.
     * @param grade
     * @param feedback
     * @return data -> the text to put in the feedback text.
     */
    private String createFeedback(Grade grade, String[] feedback){
        String data = "";

        data += "Tema: "+grade.getHomework()+"\n";
        data += "Nota: "+grade.getValue()+"\n";
        data += "Predata in saptamana: "+ structure.getWeekByDate(grade.getDate())+"\n";
        data += "Deadline: "+homeworkRepository.findOne(grade.getHomework()).getDeadlineWeek()+"\n";
        data += "Feedback: \n";
        for(String s:feedback){
            if(s!=null)
                data += s + "\n";
        }


        data += "\n";
        return data;
    }

    /**
     *Create and append to the repo the grade.Also create the feedback note and append to an existent one(or creates a new one).
     * @param homeworkID
     * @param studentID
     * @param week --> if week = -1, the week will be decided automatically
     * @param semester --> if semester = -1, the week will be decided automatically
     * @param teacher
     * @param value
     * @param feedback -> can be null
     * @return null if the grade has been added, otherwise the grade(if the grade already exists)
     * @throws ValidationException - if the grade is not valid
     * @throws IOException
     */
    public Grade addGrade(String homeworkID, String studentID,int week,int semester,String teacher,int value,String[] feedback) throws ValidationException, IOException {
        LocalDate date;
        if(week == -1 && semester == -1)
            date = LocalDate.now();
        else
            date = structure.getDateByWeek(week,semester);

        if(date == null)
            throw new ValidationException("The date is not valid!");

        Student s = studentRepository.findOne(studentID);
        if(s==null)
            throw new ValidationException("The student id doesn't exists");

        Homework h = homeworkRepository.findOne(homeworkID);
        if(h == null)
            throw new ValidationException("The homework id doesn't exists");

        String gradeID = studentID + "-" + homeworkID;

        Grade g = new Grade(gradeID,date,teacher,value);


        Grade result = repo.save(g);

        if(result != null){
            return result;
        }

        String data = createFeedback(g,feedback);
        createInfoData(s,data);
        return null;
    }

    /**
     *
     * @param studentID
     * @param homeworkID
     * @return the removed entity or null(the entity doesn't exists);
     */
    public Grade removeGrade(String studentID,String homeworkID){

        return repo.delete(studentID + "-" + homeworkID);

    }

    /**
     * Read the data for grades from a file
     * @throws IOException
     */
    public void readFromFile() throws IOException {
        String file = "src/app/files/note.csv";
        repo.readFromFile(file);
    }

    /**
     * Save the data from memory to a file
     * @throws IOException
     */
    public void writeToFile() throws IOException {
        repo.writeToFile("src/app/files/note.csv");
    }

    /**
     *Create the file for every student(if this doesn't exists) and append the feedback for a specific grade.
     * @param s-Student(must be not null)
     * @param feedback - String(the feedback created on the function addGrade(...))
     * @throws IOException
     */
    private void createInfoData(Student s, String feedback) throws IOException {
        String file ="src/app/infoData/"+ s.getFirstName()+"_"+s.getLastName()+".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));

        writer.write(feedback);
        writer.close();
    }

}
