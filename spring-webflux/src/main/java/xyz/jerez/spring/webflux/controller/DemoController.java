package xyz.jerez.spring.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author liqilin
 * @since 2020/11/5 16:42
 */
@RestController
public class DemoController {

    @GetMapping("/info")
    public String info() {
        return "success";
    }

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("success");
    }

    @GetMapping("/test1")
    public Mono<String> test1() {
        return Mono.empty();
    }

    @GetMapping("/test2")
    public Mono<String> test2() {
        return Mono.justOrEmpty(Optional.ofNullable("233"));
    }

    @GetMapping("/test3")
    public Mono<String> test3() {
        return Mono.error(new Exception("test"));
    }

    @GetMapping("/test4")
    public Mono<String> test4() {
        return Mono.from(Mono.just("2"));
    }
}
