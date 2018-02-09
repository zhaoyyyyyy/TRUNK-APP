package com.asiainfo.biapp.si.loc.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * FileName:	ThreadPool.java
 * Description:	线程池(控制线程执行，保证系统性能)
 * 支持多地市不同线程数的多个线程池
 * @author zhaiwj
 * 
 * Modification History:
 * Date			Author		Version		Description
 * =================================================
 * 2013-05-11	zhaiwj		 1.0		初始版本
 * 2014-05-31	kongly		 2.0		支持多线程池
 */
public class ThreadPool {
	private Logger log = Logger.getLogger(ThreadPool.class);
	/** The service. */
	private final ExecutorService service;

	private final ExecutorService singleThreadService;
	
	private final Map<String,ExecutorService> threadMap;

	/**
	 * The Class ThreadPoolHolder.
	 */
	static class ThreadPoolHolder {
		/** The instance. */
		static final ThreadPool instance = new ThreadPool();
	}

	/**
	 * 单例构造方法.
	 *
	 * @return single instance of ThreadPool
	 */
	public static ThreadPool getInstance() {
		return ThreadPoolHolder.instance;
	}

	/**
	 * 私有构造方法
	 */
	private ThreadPool() {
		// 根据处理器数量创建线程池。虽然多线程并不保证能够提升性能，但适量地开线程一般可以从系统骗取更多资源。
		int threadNum = Runtime.getRuntime().availableProcessors() * 2;
		Map<String,ExecutorService> threadPoolMap = new HashMap<String,ExecutorService>();
		this.threadMap = threadPoolMap;
		this.singleThreadService = Executors.newFixedThreadPool(1);
		this.service = Executors.newFixedThreadPool(threadNum);
	}

	/**
	 * 获得现在队列大小.
	 *
	 * @return int
	 */
	public int getSize() {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) service;
		return pool.getQueue().size();
	}

	/*
	 * 
	 *执行任务 
	 */
	/**
	 * Execute.
	 *
	 * @param r the r
	 */
	public void execute(Runnable r) {
		try {
			service.execute(r);
		} catch (Exception ex) {
			//        	System.out.println("------KMV-----内部线程池异常-----------");
			ex.printStackTrace();
			//        	service.shutdown();
		}
		log.info("add task [" + r.toString() + "] ThreadPool status:" + showStatus());
	}

	/**
	 * 
	 * @param r
	 * @param isSingleThread 是否以单线程的方式执行
	 */
	public void execute(Runnable r, boolean isSingleThread) {
		if (!isSingleThread) {
			execute(r);
		} else {
			try {
				singleThreadService.execute(r);
			} catch (Exception ex) {
				//        	System.out.println("------KMV-----内部线程池异常-----------");
				ex.printStackTrace();
				//        	service.shutdown();
			}
		}
		log.info("add task [" + r.toString() + "] to SingleThread ,ThreadPool status:" + showStatus());
	}
	
	/**
	 * 
	 * @param r
	 * @param isSingleThread 是否以单线程的方式执行
	 */
	public void execute(Runnable r, boolean isSingleThread,String cityId) {
		if (!isSingleThread) {
			try {
				if(StringUtil.isNotEmpty(cityId) && threadMap!=null && threadMap.size()>0){
					ExecutorService eachCityPool = threadMap.get(cityId);
					if(eachCityPool!=null){
						eachCityPool.execute(r);
						log.debug("使用的线程的cityId为："+cityId+",该线程池的线程数为"+((ThreadPoolExecutor)eachCityPool).getMaximumPoolSize());
					}else{
						execute(r,true);
					}
				}else{
					execute(r,true);
				}
			} catch (Exception ex) {
				//        	System.out.println("------KMV-----内部线程池异常-----------");
				ex.printStackTrace();
				//        	service.shutdown();
			}
		} else {
			try {
				singleThreadService.execute(r);
			} catch (Exception ex) {
				//        	System.out.println("------KMV-----内部线程池异常-----------");
				ex.printStackTrace();
				//        	service.shutdown();
			}
		}
		log.info("add task [" + r.toString() + "] to SingleThread ,ThreadPool status:" + showStatus());
	}

	/**
	 * 打印线程池当前状态
	 */
	public String showStatus() {
		StringBuffer info = new StringBuffer();
		info.append("FixedThreadPool:");
		ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
		info.append("ActiveCount:").append(executor.getActiveCount()).append(",");
		info.append("CompletedTaskCount:").append(executor.getCompletedTaskCount()).append(",");
		info.append("TaskCount:").append(executor.getTaskCount()).append(",");
		info.append("PoolSize:").append(executor.getPoolSize()).append(",");
		info.append("CorePoolSize:").append(executor.getCorePoolSize()).append(",");
		info.append("LargestPoolSize:").append(executor.getLargestPoolSize()).append(",");
		BlockingQueue<Runnable> queue = executor.getQueue();
		info.append("QueueSize:").append(queue.size()).append(",");
		info.append("BlockingQueue:");
		if (queue != null) {
			info.append("[");
			for (Runnable r : queue) {
				info.append(r);
			}
			info.append("]");
		}
		info.append("\nSingleThreadPool:");
		ThreadPoolExecutor singleExecutor = (ThreadPoolExecutor) singleThreadService;
		info.append("ActiveCount:").append(singleExecutor.getActiveCount()).append(",");
		info.append("CompletedTaskCount:").append(singleExecutor.getCompletedTaskCount()).append(",");
		info.append("TaskCount:").append(singleExecutor.getTaskCount()).append(",");
		info.append("PoolSize:").append(singleExecutor.getPoolSize()).append(",");
		info.append("CorePoolSize:").append(singleExecutor.getCorePoolSize()).append(",");
		info.append("LargestPoolSize:").append(singleExecutor.getLargestPoolSize()).append(",");
		queue = singleExecutor.getQueue();
		info.append("QueueSize:").append(queue.size()).append(",");
		info.append("BlockingQueue:");
		if (queue != null) {
			info.append("[");
			for (Runnable r : queue) {
				info.append(r);
			}
			info.append("]");
		}
		return info.toString();
	}

	/*
	 * 
	 *执行任务 支持执行Callable类型的任务
	 */
	/**
	 * Execute.
	 *
	 * @param r the r
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Callable r) throws Exception {
		try {
			service.submit(r);
		} catch (Exception ex) {
			//        	System.out.println("------KMV-----内部线程池异常-----------");
			ex.printStackTrace();
			throw ex;
			//        	service.shutdown();
		}
		log.info("add task [" + r.toString() + "] to ThreadPool status:" + showStatus());
	}

	/**
	 * 
	 * @param r
	 * @param isSingleThread  是否以单线程的方式执行
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Callable r, boolean isSingleThread) throws Exception {
		if (!isSingleThread) {
			execute(r);
		} else {
			try {
				singleThreadService.submit(r);
			} catch (Exception ex) {
				//        	System.out.println("------KMV-----内部线程池异常-----------");
				ex.printStackTrace();
				throw ex;
				//        	service.shutdown();
			}
			log.info("add task [" + r.toString() + "] to SingleThread ,ThreadPool status:" + showStatus());
		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Callable r, boolean isSingleThread ,String cityId) throws Exception {
		if (!isSingleThread) {
			try {
				if(StringUtil.isNotEmpty(cityId)  && threadMap!=null && threadMap.size()>0){
					ExecutorService eachCityPool = threadMap.get(cityId);
					if(eachCityPool!=null){
						eachCityPool.submit(r);
						log.info("使用的线程的cityId为："+cityId+",该线程池的线程数为"+((ThreadPoolExecutor)eachCityPool).getMaximumPoolSize());
					}else{
						execute(r,true);
					}
				}else{
					execute(r,true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				singleThreadService.submit(r);
			} catch (Exception ex) {
				//        	System.out.println("------KMV-----内部线程池异常-----------");
				ex.printStackTrace();
				throw ex;
				//        	service.shutdown();
			}
			log.info("add task [" + r.toString() + "] to SingleThread ,ThreadPool status:" + showStatus());
		}

	}

	/**
	 * Shutdown now.
	 *
	 * @return the list
	 */
	public List<Runnable> shutdownNow() {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) service;
		return pool.shutdownNow();
	}
}
