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
        
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("login");
        if(name==null){
            response.sendRedirect("index.jsp");
        }
        else{
            response.sendRedirect("page.xhtml");
        }

        PrintWriter out = response.getWriter();
    
        try{
            out.println("<html><p align=center>You are logged as "+name+"</p>");
			
            List<String> usersList = Persistence.getRegisteredUsers();
            String usersString = " - ";
            for(String nick : usersList){
                    usersString+=nick+" - ";
            }			
            out.println("<p align=center>All registered users: "+usersString+"</p>");
            out.println("<form action=Details method=post><p align=center><input type=submit value='Account Info' /></form>");
            out.println("<form action=Logout method=post><p align=center><input type=submit value=Logout /></form>");
            out.println("<form action=CheckDetails method=post><p align=center><input type=text name=info size=10 /><input type=submit value='Search User' /></form>");
            out.println("<form action=InsertEntry method=post><p align=center><input type=text name=message size=50 /><input type=submit value='Submit message' /></p></form>");
           
            String content = "";


            out.println(table(content));
            out.println("</html>");
        }
        catch(ClassNotFoundException | SQLException e){
            out.println("<html><p align=center>"+e.toString()+"</p><p align=center><a href=index.jsp>back</a></p></html>");
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
        return "<tr><td><p align=center>"+author+"<br><img src='http://kritsit.ddns.net:81/"+author+".jpg' width=40 height=40</p></td><td><p align=center>"+date+"</p></td><td>"+message+"</td></tr>";
    }
    
    protected String table(String content){
        return "<table border=0.1 style=\"width:100%\"><col width=\"150\"><col width=\"150\">"+ content +"</table>";
    }
	
}
