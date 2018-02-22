package lv.tsi.companydb;

import org.apache.derby.client.am.SqlException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "SelectServlet", urlPatterns = "/select")
public class SelectServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SelectServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (
                Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/company");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM POSITION ");
        ) {
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("Name");
                logger.info("ID: "+id + "; Name: "+name);
           }
        }  catch (SQLException e) {
            logger.error("Something wrong!");
        }

    }
}
