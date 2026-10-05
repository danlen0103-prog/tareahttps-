
import java.io.IOException;
import java.io.PrintWriter;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	public Servlet1() {
		super();
	}
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		noCache(response);
 
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("uname") == null) {
			response.sendRedirect("index.html");
			return;
		}
 
		String n = (String) session.getAttribute("uname");
 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("Welcome " + escapar(n));
		out.print("<br><a href='Servlet2'>visit</a>");
		out.print(" | <a href='Logout'>logout</a>");
		out.print(scriptRecarga());
		out.close();
	}
 

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		response.setContentType("text/html;charset=UTF-8");
		noCache(response);
		PrintWriter out = response.getWriter();
 
				String n = request.getParameter("userName");
 
		
		if (n == null || n.trim().isEmpty()) {
			error(out, "Debes escribir un nombre.");
			return;
		}
		n = n.trim();
 
		
		if (n.length() < 3) {
			error(out, "El nombre debe tener al menos 3 caracteres.");
			return;
		}
		if (n.length() > 49) {
			error(out, "El nombre no puede tener mas de 49 caracteres.");
			return;
		}
 
		
		if (!n.matches("[\\p{L} ]+")) {
			error(out, "El nombre solo puede tener letras y espacios (sin numeros ni guiones).");
			return;
		}
 

		HttpSession vieja = request.getSession(false);
		if (vieja != null) {
			vieja.invalidate();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("uname", n);
 

		response.sendRedirect("Servlet1");
	}
 

	private void error(PrintWriter out, String mensaje) {
		out.print("<p style='color:red'>" + mensaje + "</p>");
		out.print("<a href='index.html'>Volver</a>");
		out.close();
	}
 

	static void noCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}
 

	static String escapar(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
				.replace("\"", "&quot;").replace("'", "&#39;");
	}
 

	static String scriptRecarga() {
		return "<script>"
				+ "window.addEventListener('pageshow', function (e) {"
				+ "  var nav = performance.getEntriesByType('navigation')[0];"
				+ "  if (e.persisted || (nav && nav.type === 'back_forward')) {"
				+ "    location.reload();"
				+ "  }"
				+ "});"
				+ "</script>";
	}
}