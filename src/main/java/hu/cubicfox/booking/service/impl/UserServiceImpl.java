package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.User;
import hu.cubicfox.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
public class UserServiceImpl extends CoreCRUDServiceImpl<User> implements UserService {

    @Autowired
    private EntityManager entityManager;

    @Override
    protected void updateCore(User persistedEntity, User entity) {
        persistedEntity.setAuthorities(entity.getAuthorities());
        persistedEntity.setUserName(entity.getUserName());
        persistedEntity.setUserFirstName(entity.getUserFirstName());
        persistedEntity.setUserLastName(entity.getUserLastName());
    }

    @Override
    protected Class<User> getManagedClass() {
        return User.class;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);
        query.setParameter("userName", username);
        return query.getSingleResult();
    }
}
