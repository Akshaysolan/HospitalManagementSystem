package com.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AppointmentDAO;
import com.db.DBConnect;
import com.entity.Appointment;

@WebServlet("/appAppointment")
public class AppointmentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    try {
	        int userId = Integer.parseInt(req.getParameter("userid"));
	        int doctorId = Integer.parseInt(req.getParameter("doct"));
	        
	        String fullname = req.getParameter("fullname");
	        String gender = req.getParameter("gender");
	        String age = req.getParameter("age");
	        String appointmentDate = req.getParameter("appoint_date");
	        String email = req.getParameter("email");
	        String phno = req.getParameter("phno");
	        String diseases = req.getParameter("diseases");
	        String address = req.getParameter("address");

	        Appointment ap = new Appointment(userId, fullname, gender, age, appointmentDate, email, phno, diseases, doctorId,
	                                         address, "Pending");

	        AppointmentDAO dao = new AppointmentDAO(DBConnect.getConn());

	        if (dao.addAppointment(ap)) {
	            session.setAttribute("succMsg", "Appointment successfully created.");
	        } else {
	            session.setAttribute("errorMsg", "Something went wrong on the server.");
	        }
	    } catch (NumberFormatException e) {
	        session.setAttribute("errorMsg", "Invalid input for user ID or doctor ID.");
	    } catch (Exception e) {
	        session.setAttribute("errorMsg", "An unexpected error occurred.");
	        e.printStackTrace(); // Consider logging this instead of printing
	    }
	    resp.sendRedirect("user_appointment.jsp");
	}

}
