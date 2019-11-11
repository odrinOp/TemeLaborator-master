package app.objects.services;

import app.exceptions.ValidationException;
import app.objects.entities.Homework;
import app.objects.entities.YearStructure;
import app.objects.repositories.HomeworkRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ServiceHomework {
    private HomeworkRepository repo;
    private YearStructure structure;

    public ServiceHomework(HomeworkRepository repo, YearStructure structure) {
        this.repo = repo;
        this.structure = structure;
    }


    /**
     *
     * @param id - the id of the homework
     * @param description - the description of the homework
     * @param deadlineWeek - the deadline week for the homework
     *
     * @return null- the homework will be saved
     *          otherwise will return the homework(id already exists)
     *
     *
     * @throws ValidationException - the homework is not valid
     */
    public Homework addHomework(String id,String description, int deadlineWeek) throws ValidationException {

        int startWeek = structure.getWeekByDate(LocalDate.now());

        Homework hw = new Homework(id,description,startWeek,deadlineWeek);
        return repo.save(hw);
    }

    /**
     *
     * @param id
     * @return null - the id doesn't exists.
     *          otherwise return the deleted homework.
     */
    public Homework deleteHomework(String id){
        return repo.delete(id);
    }

    /**
     *
     * @param id
     * @param new_description
     * @param new_deadlineWeek
     *
     * @return null - the Homework was updated
     *          otherwise return the homework( id doesn't exists)
     *
     * @throws ValidationException -- the new homework is not valid.
     */
    public Homework updateHomework(String id,String new_description, int new_deadlineWeek) throws ValidationException {
        LocalDate now = LocalDate.now();
        //int startWeek = structure.getWeekByDate(now);
        if(new_deadlineWeek < structure.getWeekByDate(now))
            throw new ValidationException("Invalid deadline week!");

        Homework h = repo.findOne(id);


        Homework hw = new Homework(id,new_description,h.getStartWeek(),new_deadlineWeek);
        return repo.update(hw);
    }

    /**
     *
     * @return all the homework
     */
    public Iterable<Homework> getAllHomework(){
        return repo.findAll();
    }

    /**
     *
     * @param id
     * @return null- the homework doesn't exists
     *          otherwise the homework with specific id.
     */
    public Homework findHomework(String id){
        return repo.findOne(id);
    }


    public void readFromFile() throws IOException {
        String file = "src/app/files/teme.csv";
        repo.readFromFile(file);
    }

    public void writeToFile() throws IOException {
        repo.writeToFile("src/app/files/teme.csv");
    }

    public YearStructure getStructure(){return structure;}
}
