package com.yuga.yuga.controller;

import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.util.CommonUtils;
import com.yuga.yuga.util.MessageKeyConstants;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/yuga")
public class YugaController {

    private final YugaService yugaService;

    private final MessageSource messageSource;

    public YugaController(YugaService yugaService, MessageSource messageSource) {
        this.yugaService = yugaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/countries")
    public Mono<ResponseEntity<Response>> getCountriesList() {
        return yugaService.getCountriesList().flatMap(response -> {
            Response response1 = CommonUtils
                    .buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource, HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(response1));
        });
    }
}
