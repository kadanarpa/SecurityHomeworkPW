package co.edu.usco.SecuritySpring;

import co.edu.usco.SecuritySpring.persistence.entity.PermissionEntity;
import co.edu.usco.SecuritySpring.persistence.entity.RoleEntity;
import co.edu.usco.SecuritySpring.persistence.entity.RoleEnum;
import co.edu.usco.SecuritySpring.persistence.entity.UserEntity;
import co.edu.usco.SecuritySpring.persistence.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecuritySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritySpringApplication.class, args);
	}
        
        @Bean
        public CommandLineRunner init(UserRepository userRepo) {
            return args -> {
                // Crear permisos
                PermissionEntity createPermission = PermissionEntity.builder()
                        .name("CREATE")
                        .build();
                
                PermissionEntity readPermission = PermissionEntity.builder()
                        .name("READ")
                        .build();
                
                PermissionEntity updatePermission = PermissionEntity.builder()
                        .name("UPDATE")
                        .build();
                
                PermissionEntity deletePermission = PermissionEntity.builder()
                        .name("DELETE")
                        .build();
                
                // Crear roles
                RoleEntity adminRole = RoleEntity.builder()
                        .name(RoleEnum.ADMIN)
                        .permissions(Set.of(
                                createPermission,
                                readPermission,
                                updatePermission,
                                deletePermission))
                        .build();
                
                RoleEntity creatorRole = RoleEntity.builder()
                        .name(RoleEnum.CREATOR)
                        .permissions(Set.of(
                                createPermission,
                                readPermission))
                        .build();
                
                RoleEntity editorRole = RoleEntity.builder()
                        .name(RoleEnum.EDITOR)
                        .permissions(Set.of(
                                readPermission,
                                updatePermission))
                        .build();
                
                RoleEntity userRole = RoleEntity.builder()
                        .name(RoleEnum.USER)
                        .permissions(Set.of(readPermission))
                        .build();
                
                // Crear usuarios
                UserEntity kalethUser = UserEntity.builder()
                        .username("kadanarpa")
                        .password("2904")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(adminRole))
                        .build();
                
                UserEntity nicolleUser = UserEntity.builder()
                        .username("nicky")
                        .password("0406")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(creatorRole))
                        .build();
                
                UserEntity marlovyUser = UserEntity.builder()
                        .username("marly.pv")
                        .password("3006")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(editorRole))
                        .build();
                
                UserEntity elviraUser = UserEntity.builder()
                        .username("elvira")
                        .password("26616")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(userRole))
                        .build();
                
                // Insertar registros
                userRepo.saveAll(List.of(kalethUser, nicolleUser, marlovyUser, elviraUser));
            };
        }
        

}
