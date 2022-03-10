package hu.cubicfox.booking.service;

import hu.cubicfox.booking.entity.Hotel;

import java.util.List;

public interface HotelService extends CoreCRUDService<Hotel> {

    List<Hotel> locateByProximity(Long currentLat, Long currentLong);

    String findByRoomId(Long hotelId);

}
