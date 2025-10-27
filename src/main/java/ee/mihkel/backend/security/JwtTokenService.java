package ee.mihkel.backend.security;

import ee.mihkel.backend.entity.Person;
import ee.mihkel.backend.model.AuthToken;
import ee.mihkel.backend.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenService {

    @Autowired
    PersonRepository personRepository;

//    @Value("${authentication.secret-key}")
//    private String superSecretKey;

    private final String superSecretKey = "Tqnm4U-ysd3Qa2GO0_Fo5qUpvAty0jKWZ3LTucebHsI";

    public AuthToken generateJwtToken(Long id) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));


        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 20);

        String token = Jwts.builder()
                .subject(id.toString())
                .expiration(expiration.getTime())
                .signWith(secretKey)
                .compact();

        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setExpiration(expiration.getTime().getTime());
        return authToken;
    }

    public Person parseJwtToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

        Long id = Long.valueOf(Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject());
        return personRepository.findById(id).orElseThrow();
    }
}
