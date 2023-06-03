package org.hisp.dhis.pbf.docx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VelocityFormatHelper {
	
	private String formatDate;

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}
	
	public String formatDouble(Double val){
		return String.format("%.2f\n", val);
	}
	
	public String formatDate(Date date) {
	    try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.setTime(date);
		
		int year       = calendar.get(Calendar.YEAR);
		int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
		
		return "« "+dayOfMonth+" » "+monthName(month)+" "+year;
	    }catch(NullPointerException npe){
	    	return "";
	    }
	}

	public int getAge(Date dateOfBirth) {
	    try{
		int age = 0;
	    Calendar born = Calendar.getInstance();
	    Calendar now = Calendar.getInstance();
	    if(dateOfBirth!= null) {
	        now.setTime(new Date());
	        born.setTime(dateOfBirth);  
	        if(born.after(now)) {
	            throw new IllegalArgumentException("Can't be born in the future");
	        }
	        age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);             
	        if(now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))  {
	            age-=1;
	        }
	    }  
	    return age;
	    }catch(NullPointerException npe){
	    	return 0;
	    }
	}
	
	public String monthName(int monthNum){
		String name ="undef";
		
		if(monthNum==0){
			return "\u042F\u043D\u0432\u0430\u0440\u0438";
		}else if(monthNum==1){
			return "\u0424\u0435\u0432\u0440\u0430\u043B\u0438";
		}else if(monthNum==2){
			return "\u041C\u0430\u0440\u0442\u0438";
		}else if(monthNum==3){
			return "\u0410\u043F\u0440\u0435\u043B\u0438";
		}else if(monthNum==4){
			return "\u041C\u0430\u0438";
		}else if(monthNum==5){
			return "\u0418\u044E\u043D\u0438";
		}else if(monthNum==6){
			return "\u0418\u044E\u043B\u0438";
		}else if(monthNum==7){
			return "\u0410\u0432\u0433\u0443\u0441\u0442\u0438";
		}else if(monthNum==8){
			return "\u0421\u0435\u043D\u0442\u044F\u0431\u0440\u0438";
		}else if(monthNum==9){
			return "\u041E\u043A\u0442\u044F\u0431\u0440\u0438";
		}else if(monthNum==10){
			return "\u041D\u043E\u044F\u0431\u0440\u0438";
		}else if(monthNum==11){
			return "\u0414\u0435\u043A\u0430\u0431\u0440\u0438";
		}else{
			return name;
		}
		
	}

}
