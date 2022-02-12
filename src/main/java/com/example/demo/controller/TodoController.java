package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import com.sun.tools.javac.comp.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

//    @GetMapping("test")
//    public ResponseEntity<?> testTodo(){
//        String str = service.testService();
//        List<String> list = new ArrayList<>();
//        list.add(str);
//        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//        return ResponseEntity.ok().body(response);
//    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {
            TodoEntity todoEntity = TodoDTO.toEntity(dto);
            todoEntity.setId(null);
            todoEntity.setUserId(userId);

            List<TodoEntity> entities = service.crate(todoEntity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        List<TodoEntity> entities = service.retrieve(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo( @AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(userId);

        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(responseDTO);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);

            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }


    }





}
