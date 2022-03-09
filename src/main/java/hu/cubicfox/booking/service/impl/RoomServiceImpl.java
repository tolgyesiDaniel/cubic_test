package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.Room;
import hu.cubicfox.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class RoomServiceImpl extends CoreCRUDServiceImpl<Room> implements RoomService {

    @Autowired
    private EntityManager entityManager;

    @Override
    protected void updateCore(Room persistedEntity, Room entity){
        persistedEntity.setRoomNumber(entity.getRoomNumber());
        persistedEntity.setRoomPrice(entity.getRoomPrice());
        persistedEntity.setHotelRoom(entity.getHotelRoom());
    }

    @Override
    protected Class<Room> getManagedClass() { return Room.class; }

}
