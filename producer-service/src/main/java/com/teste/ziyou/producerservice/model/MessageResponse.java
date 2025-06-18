package com.teste.ziyou.producerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageResponse {
    private boolean success;
    private String timestamp;
    private String message;
}
