import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.DateFormatter;


public class DateP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//字符串处理->日期
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
        String str=  "1473403487000";//create_time
        String str2= "1473609599000";//expired
        //            1473411227011
        
        Object aObject = str;
        String format2 = sdf.format(aObject);
        System.out.println("format2:   "+format2);
        Date dt = null;
        Date dt2 = null;
		try {
			dt = sdf.parse(str);
			dt2 = sdf.parse(str2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        
        cal1.setTime(dt);
        cal2.setTime(dt2);
        
        //日期比较
        int compareTo = cal2.compareTo(cal1);
        
        int i = cal1.get(Calendar.DAY_OF_MONTH);
        int i2 = cal2.get(Calendar.DAY_OF_MONTH);
        
        System.out.println("i:   "+i);
        System.out.println("i2:   "+i2);
        System.out.println("compareTo:   "+compareTo);
        
        //Long类型转换
        SimpleDateFormat a =new SimpleDateFormat("yyyy-MM-dd"); 
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis +"");
        Date date = new Date(currentTimeMillis);
        String format = a.format(date);
        System.out.println(format);
        
        Calendar cal3 = Calendar.getInstance();
        cal3.set(2016,9,9);
        Calendar cal4 = Calendar.getInstance();
        cal4.set(2016,9,11);
        //日期比较
        int compareTo2 = cal4.compareTo(cal3);
        System.out.println("compareTo2:  "+compareTo2);
        
        int d3Y = cal3.get(Calendar.DAY_OF_YEAR);
        int d4Y = cal4.get(Calendar.DAY_OF_YEAR);
        
        System.out.println("d3Y:  "+d3Y);
        System.out.println("d4Y:  "+d4Y);
	}

}
