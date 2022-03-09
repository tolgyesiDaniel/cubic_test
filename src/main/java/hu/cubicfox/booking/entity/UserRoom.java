package hu.cubicfox.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "user_x_room")
public class UserRoom extends CoreEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "res_date")
    private LocalDate resDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getResDate() {
        return resDate;
    }

    public void setResDate(LocalDate resDate) {
        this.resDate = resDate;
    }
}
