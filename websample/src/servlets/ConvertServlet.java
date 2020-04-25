package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/convert")
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doPost(request, response);
		extracted(request, response);
	}


	private void extracted(HttpServletRequest request, HttpServletResponse response) throws IOException {
		double inches = Double.parseDouble(request.getParameter("inches"));
		double cm = inches * 2.5;
		PrintWriter pw = response.getWriter();
		pw.printf("<h1>%.2f inches = %.2f CM </h1>", inches, cm);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		extracted(request, response);
	}
}
