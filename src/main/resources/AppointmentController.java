@Controller
public class AppointmentController {

    @GetMapping("/appointments")
    public String viewAppointments() {
        return "appointments"; // matches appointments.html
    }
}
