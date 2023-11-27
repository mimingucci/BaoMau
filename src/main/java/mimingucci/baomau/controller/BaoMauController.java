package mimingucci.baomau.controller;

import mimingucci.baomau.entity.BaoMau;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.repository.BaoMauRepository;
import mimingucci.baomau.service.BaoMauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/baomau", produces = "application/json")
@Validated
public class BaoMauController {
    @Autowired
    private BaoMauService baoMauService;

    @Autowired
    private BaoMauRepository baoMauRepository;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        List<BaoMau> baoMaus=baoMauRepository.findAll();
        return ResponseEntity.ok(baoMaus);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getBaoMauById(@PathVariable(name = "id") int id) {
        try {
            BaoMau baoMau=baoMauService.findById(id);
            return ResponseEntity.ok(baoMau);
        }catch (BaoMauNotFoundException ex){
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }


}
