package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;

public class SocketServer {

	// 客户端的容器

	private static LinkedHashMap clients = new LinkedHashMap();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int port = 12222;

		try {

			// 1.创建服务器
			ServerSocket server = new ServerSocket(port);
			System.out.println("创建服务器");

			while (true) {// 死循环

				// 2.获取客户端的链接
				System.out.println("准备accept");
				// 3.保存客户端的实例
				final Socket client = server.accept();// 阻塞式
				System.out.println("accept完成");

				new Thread(

				new Runnable() {

					public void run() {

						try {
							// 4.获取输入流
							InputStream is = client.getInputStream();

							// 5.读取数据
							byte[] buffer = new byte[1024];// 字节缓冲
							int len = -1;
							// read阻塞式方法
							System.out.println("准备读取");
							while ((len = is.read(buffer)) != -1) {
								System.out.println("开始读取");
								// 读数据
								String data = new String(buffer, 0, len);
								System.out.println("输入的内容：" + data);

								// 6.判断认证
								if (data.startsWith("#")) {
									// 认证信息，将client放到池子中
									System.out.println("认证" + data);
									clients.put(data, client);
								} else {

									// 7.获取文本消息
									String[] split = data.split("#");
									Socket c = (Socket) clients.get("#"
											+ split[0]);
									if (c != null) {
										// 8.将消息发给对应的客户端
										System.out.println("给" + split[0]
												+ "发送" + split[1]);
										OutputStream os = c.getOutputStream();
										os.write(split[1].getBytes());
									}
								}
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

				).start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
