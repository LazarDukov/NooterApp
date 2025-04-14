package apps.nooterapp.services;

import apps.nooterapp.model.entities.User;
import apps.nooterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).map(this::mapUser).orElseThrow(() -> new UsernameNotFoundException("User with this username does not exist!"));
    }

    private UserDetails mapUser(User user) {
        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).build();
    }
}
