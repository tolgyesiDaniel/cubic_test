package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.Room;
import hu.cubicfox.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    public List<Room> findByUserId(Long id){
        Query query = entityManager.createQuery(
                "SELECT sum(u.roomPrice) as price, u.roomNumber " +
                "FROM Room u " +
                "LEFT JOIN UserRoom r ON r.roomId = u.id "+
                "WHERE u.id = " + id +
                "GROUP BY u.id"
        );
        return query.getResultList();
    }

    @Override
    protected Class<Room> getManagedClass() { return Room.class; }

}
