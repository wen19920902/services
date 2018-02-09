package services.tcp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import services.udp.UdpThreadPool;

/***
 * TCP���̳߳ع���
 * 
 * @author wan
 *
 */
public class TcpThreadPool {

	private static ThreadPoolExecutor pool;// �̳߳ع��� ����
	private static LinkedBlockingQueue<Runnable> queue;// �̻߳������
	private static FactoryUtils fUtils;// �̹߳�������

	private static final int COREPOLLSIZE = 20;// �̳߳ػ����߳�����
	private static final int MAXPOLLSIZE = 1000;// �̳߳�������߳�����
	private static final int LATENCYTIME = 10;// �����̵߳ȴ�ʱ�� (��)

	private TcpThreadPool() {
	}

	static {
		fUtils = new FactoryUtils();
		queue = new LinkedBlockingQueue<>();
		pool = new ThreadPoolExecutor(COREPOLLSIZE, MAXPOLLSIZE, LATENCYTIME, TimeUnit.MINUTES, queue, fUtils,
				new ThreadPoolExecutor.DiscardPolicy());
	}

	/***
	 * ��������̳߳���
	 * 
	 * @param run
	 *            ���Ŀ��
	 */
	public static void excute(Runnable run) {
		creation();
		synchronized (UdpThreadPool.class) {
			if (pool != null)
				pool.execute(run);
		}
	}

	/***
	 * 
	 * @return �����������
	 */
	public static TcpThreadPool creation() {
		return Budile.tp;
	}

	/***
	 * 
	 * @return �̳߳صĴ�С
	 */
	public int pollSize() {
		return pool.getPoolSize();
	}

	/***
	 * 
	 * @return ���еĴ�С
	 */
	public int queueSize() {
		return queue.size();
	}

	/***
	 * 
	 * @return �������߳�����
	 */
	public int threadSize() {
		return fUtils.threadSize();
	}

	/***
	 * �ر��̳߳�
	 */
	public void destory() {
		if (pool != null)
			pool.shutdown();
	}

	/***
	 * 
	 * @param hashCode
	 *            �ж�Ŀ��hashֵ
	 * @return �жϵ�ǰ�߳�
	 */
	public boolean exitRun(int hashCode) {
		return fUtils.quit(hashCode);
	}

	/***
	 * �̹߳�������
	 *
	 */
	private static class FactoryUtils implements ThreadFactory {

		private Map<Integer, Thread> threadMap = new ConcurrentHashMap<>();
		private final AtomicInteger integer = new AtomicInteger();

		FactoryUtils() {
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r, "UDPThread" + integer.getAndIncrement());
			threadMap.put(r.hashCode(), thread);
			return thread;
		}

		/***
		 * 
		 * @param hashs
		 *            �رն����hashCodeֵ
		 * @return �ر��̳߳��е��߳̽��
		 */
		public boolean quit(int hashCode) {
			boolean isStop = false;
			for (Integer hash : threadMap.keySet()) {
				if (hash == hashCode) {
					isStop = true;
					threadMap.get(hash).interrupt();
				}
			}
			return isStop;
		}

		/***
		 * 
		 * @return ��ȡ�������߳�����
		 */
		public int threadSize() {
			return threadMap.size();
		}
	}

	private static class Budile {
		static TcpThreadPool tp = new TcpThreadPool();
	}

}
