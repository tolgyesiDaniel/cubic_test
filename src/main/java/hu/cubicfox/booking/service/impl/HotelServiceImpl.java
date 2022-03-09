package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.Hotel;
import hu.cubicfox.booking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

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

}
