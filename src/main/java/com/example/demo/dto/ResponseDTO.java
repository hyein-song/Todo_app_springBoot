package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
