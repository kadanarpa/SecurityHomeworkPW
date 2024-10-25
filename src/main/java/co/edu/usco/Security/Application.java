package co.edu.usco.Security;

import co.edu.usco.Security.persistence.entity.RoleEntity;
import co.edu.usco.Security.persistence.entity.RoleEnum;
import co.edu.usco.Security.persistence.entity.UserEntity;
import co.edu.usco.Security.persistence.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // TESTING, SE PUEDEN HACER LAS CONSULTAS DESDE LA BASE DE DATOS
    // ESTO ES PARA AHORRAR TRABAJO
    @Bean
    public CommandLineRunner init(UserRepository userRepo) {
        return args -> {

            // Crear roles
            RoleEntity adminRole = RoleEntity.builder()
                    .name(RoleEnum.ADMIN)
                    .build();

            RoleEntity creatorRole = RoleEntity.builder()
                    .name(RoleEnum.CREATOR)
                    .build();

            RoleEntity editorRole = RoleEntity.builder()
                    .name(RoleEnum.EDITOR)
                    .build();

            RoleEntity userRole = RoleEntity.builder()
                    .name(RoleEnum.USER)
                    .build();

            // Crear usuarios
            UserEntity kalethUser = UserEntity.builder()
                    .username("kadanarpa")
                    .password(passwordEncoder.encode("2904"))
                    .isEnabled(true)
                    .roles(Set.of(adminRole))
                    .build();

            UserEntity nicolleUser = UserEntity.builder()
                    .username("nicky")
                    .password(passwordEncoder.encode("0406"))
                    .isEnabled(true)
                    .roles(Set.of(creatorRole))
                    .build();

            UserEntity marlovyUser = UserEntity.builder()
                    .username("marly.pv")
                    .password(passwordEncoder.encode("3006"))
                    .isEnabled(true)
                    .roles(Set.of(editorRole))
                    .build();

            UserEntity elviraUser = UserEntity.builder()
                    .username("elvira")
                    .password(passwordEncoder.encode("26616"))
                    .isEnabled(true)
                    .roles(Set.of(userRole))
                    .build();

            // Insertar registros
            userRepo.saveAll(List.of(kalethUser, nicolleUser, marlovyUser, elviraUser));
        };
    }
}
