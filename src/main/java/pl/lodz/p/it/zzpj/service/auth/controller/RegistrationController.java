package pl.lodz.p.it.zzpj.service.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.exception.RegistrationException;
import pl.lodz.p.it.zzpj.service.auth.dto.RegisterAccountDto;
import pl.lodz.p.it.zzpj.service.auth.manager.RegistrationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
@Log
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid RegisterAccountDto request) throws RegistrationException {
            String confirmationToken = registrationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body(confirmationToken);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalStateException.class, RegistrationException.class})
    public Map<String, String> handleIllegalStateExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        String cause = ex.getClass().getName();
        String errorMessage = ex.getMessage();
        errors.put(cause, errorMessage);
        return errors;
    }
}
