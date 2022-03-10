package hu.cubicfox.booking.service;

import hu.cubicfox.booking.entity.Room;

import java.util.List;

public interface RoomService extends CoreCRUDService<Room> {

    List<Room> findByUserId(Long roomId);

}
