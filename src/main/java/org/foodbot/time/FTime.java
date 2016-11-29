package org.foodbot.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FTime {

	private static long start;
	private static long end;
	
	public static void startTime() {
		start = System.currentTimeMillis();
	}
	public static void endTime() {
		end =  System.currentTimeMillis();
	}
	public static long getStart2EndTime() {
		return ((end-start)/1000);
	}
	
}
