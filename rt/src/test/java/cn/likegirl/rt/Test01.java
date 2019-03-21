package cn.likegirl.rt;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: Test01
 * @description: TODO
 * @date 2019/2/13 14:26
 */
public class Test01 {

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    @Test
    public void test01(){
        System.out.println(EMPTY_ELEMENTDATA == DEFAULTCAPACITY_EMPTY_ELEMENTDATA);
    }

    @Test
    public void test02(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);

       /* executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });*/

        Future<String> future = executorService.submit(() -> {
            System.out.println("value = " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });

        System.out.println(future);
        System.out.println(future.isDone());
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(future.isDone());
    }
}
