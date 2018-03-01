package lv.tsi.companydb;

import org.apache.derby.client.am.SqlException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet(name = "SelectServlet", urlPatterns = "/select")
public class SelectServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SelectServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Post method");
        String fullName = request.getParameter("fullName");
        logger.info(fullName);
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/company");
             //Statement stmt = connection.createStatement();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PERSON WHERE FULLNAME = ?");

             PrintWriter out = response.getWriter()
        ) {
            stmt.setString(1,fullName);
            ResultSet rs = stmt.executeQuery();
            out.format("| %5s | %-30s | %15s | %10s | %10s |\n", "ID", "NAME", "SALARY", "POSITION", "DEPARTMENT");
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("FULLNAME");
                long positionId = rs.getLong("POSITION_ID");
                long departmentId = rs.getLong("DEPARTMENT_ID");
                BigDecimal salary = rs.getBigDecimal("SALARY");
                out.format("| %5d | %-30s | %15.2f | %10d | %10d |\n", id, name, salary, positionId, departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
