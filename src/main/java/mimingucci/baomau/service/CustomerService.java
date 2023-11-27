package mimingucci.baomau.service;


import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.exception.AccountExistBeforeException;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerByEmail(String email) throws CustomerNotFoundException {
        Customer customer=customerRepository.findByEmail(email);
        if(customer==null){
           throw new CustomerNotFoundException("Khong tim thay khach hang voi email "+email);
        }
        return customer;
    }

    public Customer findCustomerById(int id) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(id).get();
        if(customer==null){
            throw new CustomerNotFoundException("Khong tim thay khach hang voi id "+id);
        }
        return customer;
    }

    public void updatePassword(int id, String password){
        customerRepository.updatePassword(id, password);
    }

    public void updateEnabledStatus(int id, boolean enabled){
        customerRepository.updateEnabledStatus(id, enabled);
    }

    public boolean createCustomer(String email, String password, String firstname, String lastname) throws AccountExistBeforeException {
        if(haveEmailBefore(email)){
            throw new AccountExistBeforeException("Email da duoc su dung "+email);
        }
        Customer customer=new Customer();
        customer.setCreatedtime(new Date());
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customerRepository.save(customer);
        return true;
    }

    public boolean haveEmailBefore(String email){
        Customer  customer=customerRepository.findByEmail(email);
        if(customer==null){
            return false;
        }
        return true;
    }

    public void updateCustomer(Customer customer){

    }
}
