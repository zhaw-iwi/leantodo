package ch.zhaw.sml.iwi.meng.leantodo.boundary;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.sml.iwi.meng.leantodo.controller.ToDoController;
import ch.zhaw.sml.iwi.meng.leantodo.entity.ToDo;
import ch.zhaw.sml.iwi.meng.leantodo.entity.ToDoRepository;

@RestController
@CrossOrigin
public class ToDoEndpoint {

    @Autowired
    private ToDoController toDoController;

    @Autowired
    private ToDoRepository toDoRepository;


    @RequestMapping(path = "/api/todo/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ToDo> getToDo(@PathVariable(name = "id") Long toDoId, Principal principal) {
        Optional<ToDo> resOpt = toDoRepository.findById(toDoId);

        if(resOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {        
            return  new ResponseEntity<>(resOpt.get(), HttpStatus.OK);        
        }
    }

    @RequestMapping(path = "/api/todo", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<ToDo> toDo(Principal principal) {
        return  toDoController.listAllToDos(principal.getName());        
    }

    @RequestMapping(path = "/api/todo", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public void addToDo(@RequestBody ToDo newToDo, Principal principal) {
        toDoController.persistToDo(newToDo, principal.getName());
    }
    
    @RequestMapping(path = "/api/todo", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('USER')")
    public void updateToDo(@RequestBody ToDo toDo, Principal principal) {
        toDoController.updateToDo(toDo, principal.getName());
    }
}