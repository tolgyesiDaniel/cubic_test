package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.UserRoom;
import hu.cubicfox.booking.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserRoomServiceImpl extends CoreCRUDServiceImpl<UserRoom> implements UserRoomService {

    @Autowired
    private EntityManager entityManager;

    @Override
    protected void updateCore(UserRoom persistedEntity, UserRoom entity){
        persistedEntity.setUserId(entity.getUserId());
        persistedEntity.setRoomId(entity.getRoomId());
        persistedEntity.setResDate(entity.getResDate());
    }

    @Override
    protected Class<UserRoom> getManagedClass() { return UserRoom.class; }

}
