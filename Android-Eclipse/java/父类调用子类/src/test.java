
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//先实例化父类，在实例化子类
		SuperClass superClass = new SubClass();
		SubClass subClass = new SubClass();
		superClass.yOverLoad();
		superClass.nOverLoad();
		//superClass.mY();//该方法是子类自己的，并没有重写父类的方法
		subClass.mY();
	}

}
