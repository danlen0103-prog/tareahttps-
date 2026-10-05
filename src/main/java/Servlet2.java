

import java.io.IOException;
import java.io.PrintWriter;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 

@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	public Servlet2() {
		super();
	}
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 

		Servlet1.noCache(response);
 

		HttpSession session = request.getSession(false);
 

		if (session == null || session.getAttribute("uname") == null) {
			response.sendRedirect("index.html");
			return;
		}
 
		String n = (String) session.getAttribute("uname");
 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("Hello " + Servlet1.escapar(n));
		out.print("<br><a href='Logout'>logout</a>");
 
		out.print(Servlet1.scriptRecarga());
		out.close();
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
