package interfaces;

import java.net.DatagramPacket;

/***
 * 队列的获取与添加的业务接口
 * 
 * @author wan
 *
 */
public interface QueueObject {

	// void creanQueue();// 创建队列

	void put(DatagramPacket qb);// 添加任务

}
