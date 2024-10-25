
package co.edu.usco.Security.service;

import co.edu.usco.Security.persistence.entity.RoleEntity;
import co.edu.usco.Security.persistence.entity.UserEntity;
import co.edu.usco.Security.persistence.repository.UserRepository;
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

        return new User(user.getUsername(),
                user.getPassword(),
                authorityList);
    }

}
