package com.example.demo;

import lombok.NonNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Demo {

    @NonNull
    private String id;
}
