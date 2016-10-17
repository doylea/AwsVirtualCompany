package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.amazonaws.services.ec2.model.Instance;

import rest.GetStatus;

public class RealTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Date date = new Date();
			Set<Instance> instance = GetStatus.status();
			List<String> buckets = GetStatus.bucket();
			String instancestate = "";
			for (Instance Instance : instance) {
				instancestate = instancestate +"Instance id:"+Instance.getInstanceId() + " instance state:"+ Instance.getState()+"<br>";
			}
			
			instancestate = instancestate+"<br>";
			for(String bucketname:buckets){
				instancestate = instancestate+"bucket:"+bucketname+"<br>";
			}
			
			instancestate = instancestate+"<br>"+date.toString();
			response.getWriter().write(instancestate);
			response.flushBuffer();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
