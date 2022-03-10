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
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT hotel_name, hotel_geo_lat, hotel_geo_long, SQRT(POW(hotel_geo_lat - "+currentLat+",2) + POW(hotel_geo_long - "+currentLong+",2)) as distance");
        sb.append("FROM hotels");
        sb.append("ORDER BY distance DESC");

        TypedQuery<Hotel> query = entityManager.createNamedQuery(sb.toString(), Hotel.class);
        return query.getResultList();
    }
}
