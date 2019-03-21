package cn.likegirl.rt;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @author LikeGirl
 * @version v1.0
 * @title: ThreadTest
 * @description: TODO
 * @date 2019/1/10 9:24
 */
public class ThreadTest {
    TransmittableThreadLocal<String> local = new TransmittableThreadLocal<String>();
    //    ThreadLocal<String> local = new InheritableThreadLocal<String>();
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    CountDownLatch countDownLatch1 = new CountDownLatch(1);
    CountDownLatch countDownLatch2 = new CountDownLatch(1);

    @Test
    public void test01() throws InterruptedException {
        local.set("我是主线程1");
        Runnable runnable1 = TtlRunnable.get(() -> {
            System.out.println(String.format("Thread-%s", local.get()) + " ThreadName：" + Thread.currentThread().getName());
            countDownLatch1.countDown();
        });
        executorService.submit(runnable1);
        countDownLatch1.await();
        System.out.println(String.format("Main-Thread-%s", local.get()) + " ThreadName：" + Thread.currentThread().getName());
        System.out.println("============================================");
        System.out.println("============================================");
        local.set("我是主线程2" + "ThreadName：" + Thread.currentThread().getName());
        Runnable runnable2 = TtlRunnable.get(() -> {
            System.out.println(String.format("Thread-%s", local.get()) + " ThreadName：" + Thread.currentThread().getName());
            countDownLatch2.countDown();
        });
        executorService.submit(runnable2);
        countDownLatch2.await();
        System.out.println(String.format("Main-Thread-%s", local.get()) + " ThreadName：" + Thread.currentThread().getName());

        executorService.shutdown();

    }

    @Test
    public void test02() throws InterruptedException {
        final TransmittableThreadLocal<Span> inheritableThreadLocal = new TransmittableThreadLocal<Span>();
//        final InheritableThreadLocal<Span> inheritableThreadLocal = new InheritableThreadLocal<Span>();
        inheritableThreadLocal.set(new Span("xiexiexie"));
        //输出 xiexiexie
        System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("========");
                System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());
//                inheritableThreadLocal.set(new Span("zhangzhangzhang"));
                System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());
            }
        };
        Runnable ttlRunnable1 = TtlRunnable.get(runnable1);


        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(ttlRunnable1);
        TimeUnit.SECONDS.sleep(1);
        inheritableThreadLocal.set(new Span("zhangzhangzhang"));
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("========");
                System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());
//                inheritableThreadLocal.set(new Span("zhangzhangzhang"));
                System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());
            }
        };
        Runnable ttlRunnable2 = TtlRunnable.get(runnable2);
        executorService.submit(ttlRunnable2);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("========");
        System.out.println(inheritableThreadLocal.get().name + " ThreadName：" + Thread.currentThread().getName());

    }

    static class Span {
        public String name;
        public int age;

        public Span(String name) {
            this.name = name;
        }
    }

}
