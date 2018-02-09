import java.io.File;
import java.io.RandomAccessFile;

import services.udp.TransferService;

public class Main {
	public static void main(String[] args) {
		TransferService.revice();

		int availProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("avail processors count: " + availProcessors);

		String pathname = "F:\\user.txt";
		// �����ļ�ʵ��
		File file = new File(pathname);

		try {
			// �ж��ļ��Ƿ����
			if (!file.exists()) {
				file.createNewFile();
			} // if

			// ��д��ʽ���ļ�
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			System.out.println("��ǰ������λ�ã�" + randomAccessFile.getFilePointer());

			// write �ӵ�ǰָ�뿪ʼд�룬д��һ���ֽ�
			randomAccessFile.write('A');
			System.out.println("��ǰ������λ�ã�" + randomAccessFile.getFilePointer());

			randomAccessFile.write('B');

			int num = 0x7fffffff;
			// �����write������ÿ��ֻ��дһ���ֽڣ���Ҫд4��
			randomAccessFile.write(num >>> 24);
			randomAccessFile.write(num >>> 16);
			randomAccessFile.write(num >>> 8);
			randomAccessFile.write(num);
			System.out.println("��ǰ������λ�ã�" + randomAccessFile.getFilePointer());

			// ��������writeInt���� һ��д��
			randomAccessFile.writeInt(num);
			System.out.println("��ǰ������λ�ã�" + randomAccessFile.getFilePointer());

			// �ļ�ָ��ָ���ļ���ͷ
			randomAccessFile.seek(0);
			// һ���Զ�ȡ ���ļ������ݶ������ֽ�������
			byte[] buffer = new byte[(int) randomAccessFile.length()];
			randomAccessFile.read(buffer);

			for (byte b : buffer) {
				// 16�������
				System.out.print(Integer.toHexString(b) + " ");
			} // for

			randomAccessFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}