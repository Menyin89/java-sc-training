package employees;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity // Ez kell ahhoz, hogy a metódus szintű security annotációk (pl. @PreAuthorize) működjenek.
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // Ez hiba, hogy ezt használjuk, majd visszatérünk rá.
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(6, 256, 4, 65536, 4);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(registry ->
                    // Secure coding best practice: oda kell figyelni a filter chain sorrendjére.
                    // -> Specifikusabb szabályok kerüljenek felülre. Lásd lenti ellenpélda!
                    registry.requestMatchers("/login").permitAll()
                    .requestMatchers("/", "/employees")
                    //.requestMatchers("/**") // EZ ROSSZ ELLENPÉLDA
                            .hasRole("USER") // DB-ben úgy van h ROLE_USER, de a ROLE_ előtag nem kell, mert a hasRole hozzáadja automatikusan
                    .requestMatchers("/create-employee").hasRole("ADMIN")
                    .anyRequest().denyAll()
            )
            .formLogin(Customizer.withDefaults())
            .logout(Customizer.withDefaults());

        return http.build();
    }
}
