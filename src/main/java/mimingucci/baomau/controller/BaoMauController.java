package mimingucci.baomau.controller;

import mimingucci.baomau.entity.BaoMau;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.repository.BaoMauRepository;
import mimingucci.baomau.service.BaoMauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBaoMau(@RequestBody BaoMau baoMau){
         BaoMau bm=baoMauService.createBaoMau(baoMau.getEmail(), baoMau.getPassword(), baoMau.getFirstname(), baoMau.getLastname(), baoMau.getDescription());
         if(bm==null){
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(bm, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateBaoMau(@PathVariable(name = "id") int id, @RequestBody BaoMau baoMau){
        try {
            return new ResponseEntity<>(baoMauService.updateBaoMau(id, baoMau), HttpStatus.OK);
        } catch (BaoMauNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteBaoMau(@PathVariable(name = "id") int id){
        baoMauRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa bao mau voi id: "+id, HttpStatus.ACCEPTED);
    }
}
