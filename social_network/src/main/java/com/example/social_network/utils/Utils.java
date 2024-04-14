package com.example.social_network.utils;

import java.util.List;

public final class Utils {
	
	public static String createMessage(String errorType, String... errors) {
		return String.format(errorType, (Object[]) errors);
	}
	
	public static <T> T getFirst(List<T> list){
		return list != null && !list.isEmpty() ? list.get(list.size()-1) : null;
    }

    public static <T> T getLast(List<T> list){
        return list != null && !list.isEmpty() ? list.get(list.size()-1) : null;
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
}
