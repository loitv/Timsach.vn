package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Order() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String libID = request.getParameter("libID");
		String isbn = request.getParameter("isbn");

		request.setAttribute("libID", libID);
		request.setAttribute("isbn", isbn);
		request.setAttribute("libID", libID);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/order.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
