
package co.edu.usco.SecuritySpring.config;


import co.edu.usco.SecuritySpring.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf  -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    
                    // Configurando endpoints públicos
                    request.requestMatchers(HttpMethod.GET, "/").hasAnyRole("ADMIN", "CREATOR", "EDITOR", "USER");
                    
                    // Configurando endpoints privados
                    request.requestMatchers(HttpMethod.GET, "/new").hasAnyRole("ADMIN", "CREATOR");
                    request.requestMatchers(HttpMethod.GET, "/edit/**").hasAnyRole("ADMIN", "EDITOR");
                    request.requestMatchers(HttpMethod.GET, "/delete/**").hasRole("ADMIN");
                    
                    // Configurar el resto de endpoints NO ESPECIFICADOS
                    request.anyRequest().authenticated();
                });
                //.formLogin(Customizer.withDefaults());
        
        return http.build();
    }
    
    @Bean // Llama a los AuthenticationProviders, encargados de comunicarse y obtener los datos de la BBDD
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean // Llama a los Usuarios desde la base de datos
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider  provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    
    @Bean // Necsario en el provider para 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //No codifica las contraseñas
    }
    
    /*
    //Simular usuarios
    @Bean
    public UserDetailsService userDetailsService() {
        
        List<UserDetails> userDetails = new ArrayList<>();
        
        userDetails.add(User.withUsername("Nicolle")
                .password("0406")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());
        
        userDetails.add(User.withUsername("Kaleth")
                .password("2904")
                .roles("ADMIN")
                .authorities("READ")
                .build());
        
        return new InMemoryUserDetailsManager(userDetails);
        
    }
    */
}
