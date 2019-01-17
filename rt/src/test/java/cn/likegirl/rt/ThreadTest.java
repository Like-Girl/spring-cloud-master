package cn.likegirl.rt;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    @Test
    public void test01() throws InterruptedException {
        final TransmittableThreadLocal<Span> inheritableThreadLocal = new TransmittableThreadLocal<Span>();
        inheritableThreadLocal.set(new Span("xiexiexie"));
        //输出 xiexiexie
        System.out.println(inheritableThreadLocal.get().name + " ThreadName: " + Thread.currentThread().getName());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("========");
                System.out.println(inheritableThreadLocal.get().name + " ThreadName: " + Thread.currentThread().getName());
                inheritableThreadLocal.set(new Span("zhangzhangzhang"));
                System.out.println(inheritableThreadLocal.get().name + " ThreadName: " + Thread.currentThread().getName());
//                inheritableThreadLocal.remove();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        inheritableThreadLocal.set(new Span("xiexiexie111"));
        Runnable r1 = TtlRunnable.get(runnable);
        executorService.submit(r1);
        TimeUnit.SECONDS.sleep(1);
        inheritableThreadLocal.set(new Span("xiexiexie222"));
        executorService.submit(r1);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("========");
        System.out.println(inheritableThreadLocal.get().name + " ThreadName: " + Thread.currentThread().getName());
    }

    static class Span {
        public String name;
        public int age;

        public Span(String name) {
            this.name = name;
        }
    }

}
