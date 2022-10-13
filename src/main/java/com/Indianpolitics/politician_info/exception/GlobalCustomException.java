package com.Indianpolitics.politician_info.exception;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalCustomException {
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public HashMap<String, Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		HashMap<String, Object> hashmap = new HashMap<>();
		hashmap.put("Time", new Date());
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		fieldErrors.forEach((error) -> hashmap.put(error.getField(), error.getDefaultMessage()));

		return hashmap;
	}

	@ExceptionHandler(PoliticiansNotFoundException.class)
	public ResponseEntity<String> PoliticiansNotFoundException(PoliticiansNotFoundException ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);

	}

	@ExceptionHandler(PoliticianAlreadyExistException.class)
	public ResponseEntity<String> PoliticianAlreadyExistException(PoliticianAlreadyExistException ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);

	}

	@ExceptionHandler(MoreThanOneVoteAtATimeNotAllowed.class)
	public ResponseEntity<String> MoreThanOneVoteAtATimeNotAllowed(MoreThanOneVoteAtATimeNotAllowed ex) {

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
	}
}
