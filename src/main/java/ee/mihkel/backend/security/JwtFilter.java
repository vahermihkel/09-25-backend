package ee.mihkel.backend.security;

import ee.mihkel.backend.entity.Person;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String token = authenticationHeader.replace("Bearer ", "");
            Person person = jwtService.parseJwtToken(token);
            List<GrantedAuthority> authorities = new ArrayList<>();
            switch (person.getRole()) {
                case MANAGER -> {
                    authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                }
                case ADMIN -> authorities.add(new SimpleGrantedAuthority("ADMIN"));
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(person.getEmail(), person.getId(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);
    }
}

