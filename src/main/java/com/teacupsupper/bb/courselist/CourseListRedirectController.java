package com.teacupsupper.bb.courselist;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple course list example that redirects from course list to specified location.
 * @author Ian McGilloway
 */
public class CourseListRedirectController extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(response.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", getRedirectLocation(request));
    }

    private String getRedirectLocation(HttpServletRequest request) {

        Properties props = new Properties();
        try{
            props.load(getServletContext().getResourceAsStream("/WEB-INF/properties/location.properties"));
        }
        catch (IOException ioe){
            /**
             * goes to the Tomcat log. Continue as to allow Blackboard's error handling to
             * generate message in the bb-services-log for later null pointer exceptions
             */
            ioe.printStackTrace();
        }

        StringBuilder location = new StringBuilder();

        location.append(props.getProperty("protocol"));
        location.append(request.getServerName());
        location.append(props.getProperty("path"));
        location.append(request.getParameter(props.getProperty("course_param")));

        return location.toString();
    }
}
