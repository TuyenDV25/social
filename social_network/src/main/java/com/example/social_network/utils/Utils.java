package com.example.social_network.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public final class Utils {

	public static <T> T getFirst(List<T> list) {
		return list != null && !list.isEmpty() ? list.get(list.size() - 1) : null;
	}

	public static <T> T getLast(List<T> list) {
		return list != null && !list.isEmpty() ? list.get(list.size() - 1) : null;
	}

	/**
	 * check input is long
	 * 
	 * @param strNum
	 * @return
	 */
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Long d = Long.parseLong(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * convert string YYYYmmDD to localdate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate convertStringToLocalDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(date, format);
	}

	public static Date StringToDate(String s) {

		Date result = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = dateFormat.parse(s);
		}

		catch (ParseException e) {
			e.printStackTrace();

		}
		return result;
	}
}
