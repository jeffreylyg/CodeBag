
package com.codebag.code.mycode.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.codebag.bag.CaseListView;
import com.codebag.bag.Annotation;

@Annotation("SingleThreadPool: 有唯一线程的线程池，它和 Executors.newFixedThreadPool(1)的区别是：如果任务异常，会重新开启一个线程，继续执行")
public class SingleThreadPool extends CaseListView {

	public SingleThreadPool(Context context) {
		super(context);
	}

	public void run_singleThreadExecutor() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		for(int i = 0; i < 10; i++ ) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						throw new Exception("12345");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

