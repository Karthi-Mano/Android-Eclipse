import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class secret {


	public static String myCode(String str, byte seed) {

        try {
            byte[] bytes = str.getBytes("UTF-8");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= seed;
            }
            return new String(bytes, "UTF-8");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String encode = Base64.encode(("19920725c").getBytes());
		System.out.println(encode);
		
		String myCode = myCode(encode,(byte)110);
		System.out.println(myCode);
		
		String myCode1 = myCode(myCode,(byte)110);
		System.out.println(myCode1);
		
		byte[] decode = Base64.decode(myCode1);
		System.out.println(new String(decode));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
