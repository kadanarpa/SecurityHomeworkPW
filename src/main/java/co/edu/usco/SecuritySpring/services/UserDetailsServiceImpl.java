package co.edu.usco.SecuritySpring.services;

import co.edu.usco.SecuritySpring.persistence.entity.RoleEntity;
import co.edu.usco.SecuritySpring.persistence.entity.UserEntity;
import co.edu.usco.SecuritySpring.persistence.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userRepo.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        for (RoleEntity role : user.getRoles()) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())));
        }

        // Ya que cada usuario tiene una lista de roles, almacenamos los permisos de cada
        // Rol en un flatMap, por lo que por cada rol tendremos un flatMap (debido a stream)
        user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                // por cada flatMap necesitamos recorrerlo para obtener los permisos
                .forEach(permission -> authorityList.add(
                        new SimpleGrantedAuthority(permission.getName())));
        
        return new User(user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorityList);
    }

}
