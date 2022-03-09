package hu.cubicfox.booking.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room extends CoreEntity {

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_price")
    private Integer roomPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hotel hotelRoom;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Hotel getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(Hotel hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

}
