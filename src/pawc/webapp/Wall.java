package pawc.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
    
import java.sql.SQLException;

import pawc.webapp.persistence.Persistence;
import pawc.webapp.model.EntryModel;

@WebServlet("/Wall")
public class Wall extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PrintWriter out = null;
    
    public Wall() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
    
        try{
            String name = request.getParameter("name");
            out.println("<html><p align=center>Jestes zalogowany jako "+name+"</p></html>");

            List<EntryModel> list = Persistence.getAllEntries();
            String content = "";

            for(EntryModel entry : list){
                content+=printRow(entry.getAuthor(), entry.getDate(), entry.getMessage());
            }

            out.println(table(content));

        }
        catch(ClassNotFoundException | SQLException e){
            out.println("<html><p align=center>BŁĄD: "+e.toString()+"</p><p align=center><a href=index.jsp>powrót</a></p></html>");
        }
        finally{
            out.close();
        }       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
	}

    protected String printRow(String author, String date, String message){
        return "<tr><td>"+author+"</td><td>"+date+"</td><td>"+message+"</td></tr>";
    }
    
    protected String table(String content){
        return "<table border=1 style=\"width:100%\">"+ content +"</table>";
    }
	
}
