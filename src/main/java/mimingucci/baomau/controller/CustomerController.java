package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.exception.AccountExistBeforeException;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.repository.CustomerRepository;
import mimingucci.baomau.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository  customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(name = "id") int id){
        try {
            return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.FOUND);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
        try {
            return new ResponseEntity<>(customerService.createCustomer(customer.getEmail(), customer.getPassword(), customer.getFirstname(), customer.getLastname()), HttpStatus.CREATED);
        } catch (AccountExistBeforeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable(name = "id") int id, @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "id") int id){
        customerRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa customer voi id: "+id, HttpStatus.OK);
    }
}
