package hu.cubicfox.booking.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hu.cubicfox.booking.entity.Hotel;
import hu.cubicfox.booking.entity.Room;
import hu.cubicfox.booking.entity.User;
import hu.cubicfox.booking.entity.UserRoom;
import hu.cubicfox.booking.service.HotelService;
import hu.cubicfox.booking.service.RoomService;
import hu.cubicfox.booking.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard")
public class MainView extends HorizontalLayout {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRoomService userRoomService;

    private HorizontalLayout container = new HorizontalLayout();
    private Grid<Room> grid = new Grid<>();
    private Button closeRoomBtn = new Button("Close");
    private Button reserveBtn = new Button("Resign");

    @PostConstruct
    public void init(){
        add(new H3("Dashboard"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        List<UserRoom> userRoomList = userRoomService.findByKey(user.getId(), "user_id");
        List<Room> roomList = new ArrayList<>();
        List<String> hotelList = new ArrayList<>();

        for (UserRoom userRoom : userRoomList){
            roomList.add(roomService.findById(userRoom.getRoomId()));
        }
        for (Room room : roomList){
            hotelList.add(hotelService.findByRoomId(room.getId()));
        }
    }

}
