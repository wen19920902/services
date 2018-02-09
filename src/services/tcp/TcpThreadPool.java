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
 * TCP的线程池管理
 * 
 * @author wan
 *
 */
public class TcpThreadPool {

	private static ThreadPoolExecutor pool;// 线程池管理 对象
	private static LinkedBlockingQueue<Runnable> queue;// 线程缓存队列
	private static FactoryUtils fUtils;// 线程工厂对象

	private static final int COREPOLLSIZE = 20;// 线程池基本线程数量
	private static final int MAXPOLLSIZE = 1000;// 线程池中最大线程数量
	private static final int LATENCYTIME = 10;// 空闲线程等待时间 (分)

	private TcpThreadPool() {
	}

	static {
		fUtils = new FactoryUtils();
		queue = new LinkedBlockingQueue<>();
		pool = new ThreadPoolExecutor(COREPOLLSIZE, MAXPOLLSIZE, LATENCYTIME, TimeUnit.MINUTES, queue, fUtils,
				new ThreadPoolExecutor.DiscardPolicy());
	}

	/***
	 * 添加任务到线程池中
	 * 
	 * @param run
	 *            添加目标
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
	 * @return 创建本类对象
	 */
	public static TcpThreadPool creation() {
		return Budile.tp;
	}

	/***
	 * 
	 * @return 线程池的大小
	 */
	public int pollSize() {
		return pool.getPoolSize();
	}

	/***
	 * 
	 * @return 队列的大小
	 */
	public int queueSize() {
		return queue.size();
	}

	/***
	 * 
	 * @return 启动的线程数量
	 */
	public int threadSize() {
		return fUtils.threadSize();
	}

	/***
	 * 关闭线程池
	 */
	public void destory() {
		if (pool != null)
			pool.shutdown();
	}

	/***
	 * 
	 * @param hashCode
	 *            中断目标hash值
	 * @return 中断当前线程
	 */
	public boolean exitRun(int hashCode) {
		return fUtils.quit(hashCode);
	}

	/***
	 * 线程工厂对象
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
		 *            关闭对象的hashCode值
		 * @return 关闭线程池中的线程结果
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
		 * @return 获取启动的线程数量
		 */
		public int threadSize() {
			return threadMap.size();
		}
	}

	private static class Budile {
		static TcpThreadPool tp = new TcpThreadPool();
	}

}
