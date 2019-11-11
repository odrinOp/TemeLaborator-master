package app.objects.repositories;

import app.interfaces.Validator;
import app.objects.entities.Grade;
import app.objects.repositories.AbstractRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class GradeRepository extends AbstractRepository<String, Grade> {


    public GradeRepository(Validator validator) {
        super(validator);
    }

    @Override
    public void readFromFile(String file) throws IOException {
        list.clear();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line!=null){
            String[] values = line.split("/");
            LocalDate date = null;
            if(values.length == 4)
            {
                String[] data = values[1].split("-");
                if(data.length == 3)
                    date = LocalDate.of(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));

                if(date != null)
                {
                    Grade g = new Grade(values[0],date,values[2],Integer.parseInt(values[3]));
                    list.add(g);
                }
            }

            line= reader.readLine();
        }


        reader.close();

    }
}
