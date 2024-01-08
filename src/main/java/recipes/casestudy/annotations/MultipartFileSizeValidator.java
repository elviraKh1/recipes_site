package recipes.casestudy.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSizeValidation, MultipartFile> {

    private static final String ERROR_MESSAGE = "File too Large. The field imageFile exceeds its maximum permitted size of 1048576 bytes";

    private static final int FILE_SIZE = 1048576;

    @Override
    public void initialize(MultipartFileSizeValidation inputFile) {
    }

    @Override
    public boolean isValid(MultipartFile inputFile, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(ERROR_MESSAGE).addConstraintViolation();
        return inputFile.getSize() < FILE_SIZE;
    }
}