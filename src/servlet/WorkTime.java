package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import startandstop.Start;
import startandstop.Stop;

/**
 * Servlet implementation class WorkTime
 */
public class WorkTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//set the date format;
	final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//get the parameter from jsp page worktime;
		String start = request.getParameter("start2");
		String end = request.getParameter("end2");

		// get the format string dateNowStr;
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);

		// get the format date+input starttime and endtime;
		start = dateNowStr + ' ' + start;
		end = dateNowStr + ' ' + end;

		//get the start time and stop time;
		Date dateStart = null;
		Date dateEnd =null;
		try {
			dateStart = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(start);
			dateEnd = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get the current time;
		Date now = new Date();
		//System.out.println(now);

		// stop timer and start timer;
		Timer t = new Timer();
			t.schedule(new TimerTask() {
				public void run() {
					System.out.println("stop....");	
					try {
						Stop.stop();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, dateEnd);
			t.schedule(new TimerTask(){
				public void run() {
					System.out.println("start....");	
					try {
						Start.start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			},dateStart);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
