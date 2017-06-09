
public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String str1="1";
		String str2 = new String("2");
		
		System.out.println("str1:"+str1);
		System.out.println("str2:"+str2);
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("3123131223");
		int length = sb1.length();
		sb1.delete(0, length+1);
		System.out.println("sb1:"+sb1);

	}

}
