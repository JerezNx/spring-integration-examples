package xyz.jerez.spring.mvc.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 通过 Java VisualVM 查看线程情况，了解spring mvc 异步执行逻辑
 *
 * @author LQL
 * @since Create in 2021/2/16 19:15
 */
@EnableAsync
@RestController
public class DemoController {

    /**
     * 普通请求方式
     * 会将tomcat的nio worker 线程睡眠，如果同时来200个请求，后面的请求就没法处理了
     */
    @GetMapping("/test")
    public String test() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(20);
        return "success";
    }

    /**
     * 首先 worker 线程会立刻返回空 （所以idea会警告，提示方法返回值应该为void 或者 Future），下面返回的字符串不会生效
     * 然后会使用新的线程去执行方法中的逻辑（springboot 默认设置的异步任务线程池，看包名猜应该和定时任务用的同一个线程池）
     */
    @Async
    @GetMapping("/testAsync")
    public String testAsync() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(20);
        return "success";
    }

    /**
     * 不需要加 {@link Async} 注解
     * 返回值是 {@link Callable} spring mvc会将其丢给其默认维护的线程池去执行
     * 且此时不会立刻响应，直到执行完成
     */
    @GetMapping("/testAsyncWithReturn")
    public Callable<String> testAsyncWithReturn() {
        return () -> {
            System.out.println(Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(20);
            return "success";
        };
    }

    /**
     * 不需要加 {@link Async} 注解
     * 返回值是 {@link DeferredResult} 需要我们自己维护其异步执行逻辑
     * 且此时不会立刻响应，直到执行完成
     */
    @GetMapping("/testAsyncWithReturnBySelf")
    public DeferredResult<String> testAsyncWithReturnBySelf() {
        final DeferredResult<String> result = new DeferredResult<>();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult("success");
        }).start();
        return result;
    }

}
