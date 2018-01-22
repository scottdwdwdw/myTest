package Servlet;


import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register.do")
public class Register extends HttpServlet {
    private final String USER = "D:\\java_eclipse\\DoW";
    private final String SUCCESS_VIEW = "success.view";
    private final String ERROR_VIEW = "error.view";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           String email = request.getParameter(UserInfo.EMAIL);
           String username  = request.getParameter(UserInfo.USER_NAME);
           String password = request.getParameter(UserInfo.PASSWD);
           String confirmedPasswd = request.getParameter(UserInfo.CONFIRMED_PWD);


           List<String> errors = new ArrayList<String>();
           if(isInvalidEmail(email)){
               errors.add("邮件格式不正确");
           }

           if(isInvalidUsername(username)){
               errors.add("用户名为空或已存在");
           }

           if(isInvalidPassword(password,confirmedPasswd)){
               errors.add("密码格式不对或两次输入密码不一致");
           }

           String resultPage = ERROR_VIEW;


           if(!errors.isEmpty()){
               request.setAttribute("errors",errors);
           }
           else{
               resultPage = SUCCESS_VIEW;
               createUserData(email,username,password);
           }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           System.out.print("in get method");
    }
  
    private boolean isInvalidEmail(String email){
        return false;
    }

    private boolean isInvalidUsername(String username){
        for(String file:new File(USER).list()){
            if(file.equals(username)){
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidPassword(String password,String confirmedPasswd){
        return password==null||password.length()<6||password.length()>16||!password.equals(confirmedPasswd);
    }

    private void createUserData(String email,String username,String pwd) throws IOException{
        File userhome = new File(USER+"\\"+username);
        userhome.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userhome+"/profile"));
        writer.write(email+"\t"+ pwd);
        writer.close();
    }
}

class UserInfo{
    static final String EMAIL = "email";
    static final String USER_NAME = "username";
    static final String PASSWD = "password";
    static final String CONFIRMED_PWD = "confirmedPasswd";
}
