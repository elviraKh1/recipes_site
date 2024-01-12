package recipes.casestudy.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSizeValidation, MultipartFile> {

    private int fileSize;


    @Override
    public void initialize(MultipartFileSizeValidation inputFile) {
        this.fileSize = inputFile.fileSize();
    }

    @Override
    public boolean isValid(MultipartFile inputFile, ConstraintValidatorContext context) {
        return inputFile.getSize() < fileSize;
    }
}