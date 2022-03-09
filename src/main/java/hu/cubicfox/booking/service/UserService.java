package hu.cubicfox.booking.service;

import hu.cubicfox.booking.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CoreCRUDService<User>, UserDetailsService {

}
