package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.Role;
import hu.cubicfox.booking.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends CoreCRUDServiceImpl<Role> implements RoleService {

    @Override
    protected void updateCore(Role persistedEntity, Role entity) {
        persistedEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<Role> getManagedClass() {
        return Role.class;
    }

}