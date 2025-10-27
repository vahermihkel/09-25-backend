package ee.mihkel.backend.repository;

import ee.mihkel.backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

//    @Query("SELECT p from Person ")
//    Person customPäring(String email);

}
