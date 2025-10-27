package ee.mihkel.backend.controller;

import ee.mihkel.backend.entity.Person;
import ee.mihkel.backend.entity.PersonRole;
import ee.mihkel.backend.model.AuthToken;
import ee.mihkel.backend.model.LoginData;
import ee.mihkel.backend.repository.PersonRepository;
import ee.mihkel.backend.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        if (person.getEmail() == null || person.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (person.getPassword() == null || person.getPassword().length() < 6) {
            throw new RuntimeException("Password is required");
        }
//        person.setRole(PersonRole.CUSTOMER); LIVEs kindlasti mitte!!!
        return personRepository.save(person);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody LoginData loginData) {
        if (loginData.getEmail() == null || loginData.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (loginData.getPassword() == null || loginData.getPassword().length() < 6) {
            throw new RuntimeException("Password is required");
        }
        Person person = personRepository.findByEmail(loginData.getEmail());
        if (person == null) {
            throw new RuntimeException("Email is not registered");
        }
        if (!person.getPassword().equals(loginData.getPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        return jwtTokenService.generateJwtToken(person.getId());
    }

    @GetMapping("person")
    public Person getPerson() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        return personRepository.findById(id).orElseThrow();
    }

    @PutMapping("person")
    public Person updatePerson(@RequestBody Person person) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        person.setId(id);
        return personRepository.save(person);
    }
}
