package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.Hotel;
import hu.cubicfox.booking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class HotelServiceImpl extends CoreCRUDServiceImpl<Hotel> implements HotelService {

    @Autowired
    private EntityManager entityManager;

    @Override
    protected void updateCore(Hotel persistedEntity, Hotel entity){
        persistedEntity.setHotelName(entity.getHotelName());
        persistedEntity.setHotelGeoLat(entity.getHotelGeoLat());
        persistedEntity.setHotelGeoLong(entity.getHotelGeoLong());
    }

    @Override
    protected Class<Hotel> getManagedClass() { return Hotel.class; }

    @Override
    public List<Hotel> locateByProximity(Long currentLat, Long currentLong){
        TypedQuery<Hotel> query = entityManager.createNamedQuery(Hotel.LOCATE_BY_PROXIMITY, Hotel.class);
        query.setParameter("geoLat", currentLat);
        query.setParameter("geoLong", currentLong);
        return query.getResultList();
    }

    @Override
    public String findByRoomId(Long hotelId){
        TypedQuery<String> query = entityManager.createNamedQuery(Hotel.FIND_BY_ROOM_ID, String.class);
        query.setParameter("id", hotelId);
        return query.getSingleResult();
    }
}
