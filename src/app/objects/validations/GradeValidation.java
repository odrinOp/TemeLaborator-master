package app.objects.validations;

import app.exceptions.ValidationException;
import app.interfaces.Validator;
import app.objects.entities.Grade;

public class GradeValidation implements Validator<Grade> {
    @Override
    public void validate(Grade entity) throws ValidationException {
        String errors = "";

        if(entity.getId() == null)
            errors += "Invalid id!\n";
        else{
            String[] ids = entity.getId().split("-");
            if(ids.length != 2)
                errors += "Invalid id!\n";
        }

        if(entity.getDate() == null)
            errors += "Date is null!\n";

        if(entity.getTeacher() == null || entity.getTeacher().equals("") )
            errors += "Teacher's name invalid!\n";

        if(entity.getValue() < 1 || entity.getValue() > 10)
            errors += "The value of the grade is invalid!\n";

        if(!errors.equals(""))
            throw new ValidationException(errors);
    }


}
