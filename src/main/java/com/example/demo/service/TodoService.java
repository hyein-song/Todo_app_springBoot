package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

//    public String testService(){
//        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
//        todoRepository.save(entity);
//        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();
//
//        return savedEntity.getTitle() ;
//    }

    public List<TodoEntity> crate(final TodoEntity todoEntity){

        validate(todoEntity);

        todoRepository.save(todoEntity);
        log.info("Entity ID : {} is saved.",todoEntity.getId());

        return todoRepository.findByUserId(todoEntity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId){
        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity todoEntity){

        validate(todoEntity);

        final Optional<TodoEntity> original = todoRepository.findById(todoEntity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(todoEntity.getTitle());
            todo.setDone(todoEntity.isDone());

            todoRepository.save(todo);
        });

        return retrieve(todoEntity.getUserId());

    }

    public List<TodoEntity> delete(final TodoEntity entity){

        validate(entity);

        try{
            todoRepository.delete(entity);

        } catch (Exception e){
            log.error("error deleting entity ", entity.getId(),e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());


    }


    private void validate(final TodoEntity todoEntity){
        // validation
        if (todoEntity ==null){
            log.warn("entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (todoEntity.getUserId()==null){
            log.warn("Unknown User");
            throw new RuntimeException("Unknown User");
        }
    }


}
