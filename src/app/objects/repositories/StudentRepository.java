package app.objects.repositories;

import app.interfaces.Validator;
import app.objects.entities.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentRepository extends AbstractRepository<String, Student> {
    public StudentRepository(Validator validator) {
        super(validator);
    }

    @Override
    public void readFromFile(String file) throws IOException {
        list.clear();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String data = reader.readLine();
        while(data!=null){

            String[] values = data.split("/");
            if(values.length == 6)
            {
                Student s = new Student(values[0],values[1],values[2],Integer.parseInt(values[3]),values[4],values[5]);
                list.add(s);
            }

            data = reader.readLine();
        }

        reader.close();
    }


}
