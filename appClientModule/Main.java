import java.io.File;
import java.io.RandomAccessFile;

import services.udp.TransferService;

public class Main {
	public static void main(String[] args) {
		TransferService.revice();

		int availProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("avail processors count: " + availProcessors);

		String pathname = "F:\\user.txt";
		// 创建文件实例
		File file = new File(pathname);

		try {
			// 判断文件是否存在
			if (!file.exists()) {
				file.createNewFile();
			} // if

			// 读写方式打开文件
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			System.out.println("当前所处的位置：" + randomAccessFile.getFilePointer());

			// write 从当前指针开始写入，写入一个字节
			randomAccessFile.write('A');
			System.out.println("当前所处的位置：" + randomAccessFile.getFilePointer());

			randomAccessFile.write('B');

			int num = 0x7fffffff;
			// 如果用write方法，每次只能写一个字节，需要写4次
			randomAccessFile.write(num >>> 24);
			randomAccessFile.write(num >>> 16);
			randomAccessFile.write(num >>> 8);
			randomAccessFile.write(num);
			System.out.println("当前所处的位置：" + randomAccessFile.getFilePointer());

			// 或者是用writeInt方法 一次写入
			randomAccessFile.writeInt(num);
			System.out.println("当前所处的位置：" + randomAccessFile.getFilePointer());

			// 文件指针指向文件开头
			randomAccessFile.seek(0);
			// 一次性读取 把文件中内容都读到字节数组中
			byte[] buffer = new byte[(int) randomAccessFile.length()];
			randomAccessFile.read(buffer);

			for (byte b : buffer) {
				// 16进制输出
				System.out.print(Integer.toHexString(b) + " ");
			} // for

			randomAccessFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}