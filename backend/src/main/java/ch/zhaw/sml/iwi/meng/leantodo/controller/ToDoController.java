package ch.zhaw.sml.iwi.meng.leantodo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.sml.iwi.meng.leantodo.entity.ToDo;
import ch.zhaw.sml.iwi.meng.leantodo.entity.ToDoRepository;

@Component
public class ToDoController {


    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> listAllToDos(String loginName) {
        return toDoRepository.findAllButArchivedByOwner(loginName);
    }

    public void persistToDo(ToDo newToDo, String owner) {
        newToDo.setOwner(owner);
        newToDo.setId(null);
        toDoRepository.save(newToDo);
    }

    public void updateToDo(ToDo toDo, String owner) {
        Long toDoId = toDo.getId();
        if(toDoId == null) {
            return;
        }
        
        Optional<ToDo> origOpt = toDoRepository.findById(toDoId);
        // Check if the original ToDo was present and that it belonged to the same owner
        if(origOpt.isEmpty() || !origOpt.get().getOwner().equals(owner)) {
            return;
        }
        // Ok, let's overwrite the existing toDo.
        toDo.setOwner(owner); // Set the owner because this property is ignored in the Rest API
        toDoRepository.save(toDo);
    }
    
}