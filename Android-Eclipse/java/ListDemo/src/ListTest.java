
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList arrayList = new ArrayList();
		for (int i = 0; i < 10; i++) {
			ListBean lb = new ListBean();
			lb.setI(i);
			arrayList.add(lb);
		}

		ListBean listBean = new ListBean();
		listBean.setI(1);
		arrayList.remove(listBean);

		/*
		 * for(int i=arrayList.size()-1 ;i>=0;i--){ ListBean lbi = (ListBean)
		 * arrayList.get(i); if(lbi.getI()==listBean.getI()){
		 * arrayList.remove(i); } }
		 */

		for (int i = 0; i < arrayList.size(); i++) {
			ListBean lbi = (ListBean) arrayList.get(i);
			System.out.println(lbi.getI() + "");
		}

		/*
		 * 
		 * 
		 * int parseInt = (int) Float.parseFloat(moneyString);
		 * System.out.println(parseInt+" 转换");
		 * 
		 * String str = "connect";
		 * 
		 * int parseInt2 = Integer.parseInt(str); System.out.println(parseInt2);
		 */

		DecimalFormat formatKeepTwoZero = new DecimalFormat("#");
		String obj = "123.000000000000";
		
		Object a = obj;
		
		String format = formatKeepTwoZero.format(a);
		System.out.println("format:    "+format);
		

	}

}
