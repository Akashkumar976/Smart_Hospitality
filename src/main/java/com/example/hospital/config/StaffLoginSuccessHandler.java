package com.example.hospital.config;

import com.example.hospital.model.Staff;
import com.example.hospital.service.ArogyaStaffService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StaffLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ArogyaStaffService staffService;

    public StaffLoginSuccessHandler(ArogyaStaffService staffService) {
        this.staffService = staffService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Get the staffId used to login (like STF002)
        String staffId = authentication.getName();

        // Find Staff entity by staffId
        Staff staff = staffService.getByStaffId(staffId);
        if (staff != null) {
            // Redirect to personal dashboard using numeric id
            response.sendRedirect(request.getContextPath() + "/admin/staff/dashboard/staff/" + staff.getId());
        } else {
            // fallback
            response.sendRedirect(request.getContextPath() + "/admin/staff/login");
        }
    }
}
