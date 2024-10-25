package co.edu.usco.Security.config;

import co.edu.usco.Security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {

                    // Configurando endpoints públicos
                    request.requestMatchers("/loginPage", "/css/**").permitAll();
                    request.requestMatchers("/").hasAnyRole("ADMIN", "CREATOR", "EDITOR", "USER");

                    // Configurando endpoints privados
                    request.requestMatchers("/new").hasAnyRole("ADMIN", "CREATOR");
                    request.requestMatchers("/edit/**").hasAnyRole("ADMIN", "EDITOR");
                    request.requestMatchers("/delete/**").hasRole("ADMIN");

                    // Configurar el resto de endpoints NO ESPECIFICADOS
                    request.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/loginPage");
                    login.successHandler(successHandler());
                    login.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/loginPage?logout");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .exceptionHandling(ex -> ex.accessDeniedHandler(deniedHandler()));

        return http.build();
    }

    @Bean // Llama a los AuthenticationProviders, encargados de comunicarse y obtener los datos de la BBDD
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // Llama a los Usuarios desde la base de datos
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean // Necsario en el provider para 
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/");
        };
    }

    @Bean
    public AccessDeniedHandler deniedHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/403");
        };
    }

}
