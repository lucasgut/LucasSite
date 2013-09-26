package org.lgg.lucassite.validator.upload;
 
import org.lgg.lucassite.model.upload.Upload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
 
@Component
public class UploadValidator implements Validator{
 
	@Override
	public boolean supports(Class<?> clazz) {
		return Upload.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "required.subject", "Please specify subject.");
		Object image = errors.getFieldValue("image");
		if(image != null && ((MultipartFile) image).getSize() > 1048576) {
			errors.rejectValue("image", "image.size.exceeded", "Image size should not exceed 1MB.");
		}
		Object file = errors.getFieldValue("file");
		if(file != null && ((MultipartFile) file).getSize() > 16777215) {
			errors.rejectValue("file", "file.size.exceeded", "File size should not exceed 16MB.");
		}
	}
 
}