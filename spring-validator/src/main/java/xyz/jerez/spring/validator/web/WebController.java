package xyz.jerez.spring.validator.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;

/**
 * @author liqilin
 * @since 2021/3/3 9:14
 */
@RestController
public class WebController {

    /**
     * spring会提供
     */
    @Autowired
    private Validator validator;

}
