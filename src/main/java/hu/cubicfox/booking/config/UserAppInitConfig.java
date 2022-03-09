package hu.cubicfox.booking.config;

import hu.cubicfox.booking.entity.Role;
import hu.cubicfox.booking.entity.User;
import hu.cubicfox.booking.service.RoleService;
import hu.cubicfox.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserAppInitConfig {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostConstruct
    private void init(){
        List<Role> roles = roleService.getAll();
        Role admin = new Role();
        if (roles.isEmpty()){
            admin.setAuthority("ROLE_ADMIN");
            roleService.add(admin);
        }

        List<User> users = userService.getAll();
        if (users.isEmpty()){
            User user = new User();
            user.setUserPassword(new BCryptPasswordEncoder().encode("password"));
            user.setUserName("user");
            user.setUserFirstName("user");
            user.setUserLastName("user");
            user.setAuthorities(new ArrayList<>());
            user.getAuthorities().add(admin);
            userService.add(user);
        }
    }

}
