package mimingucci.baomau.service;

import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.BaoMau;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.repository.BaoMauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class BaoMauService {
    @Autowired
    private BaoMauRepository baoMauRepository;

    public BaoMauService(BaoMauRepository baoMauRepository) {
        this.baoMauRepository = baoMauRepository;
    }

    public BaoMau findById(int id) throws BaoMauNotFoundException {
        BaoMau baomau = baoMauRepository.findById(id).get();
        if(baomau==null){
            throw new BaoMauNotFoundException("Khong the tim duoc bao mau voi id "+id);
        }else {
            return baomau;
        }
    }

    public Boolean haveEmailBefore(String email){
        BaoMau baomau=baoMauRepository.findByEmail(email);
        if(baomau==null){
            return false;
        }
        return true;
    }

    public void updateEnabledStatus(int id, boolean enabled) {
        baoMauRepository.updateEnabledStatus(id, enabled);
    }

    public void updatePassword(int id, String password){
        baoMauRepository.updatePassword(id, password);
    }

    public void updateDescription(int id, String description){
        baoMauRepository.updateDescription(id, description);
    }

    public void updateBaoMau(BaoMau baoMau){
        baoMauRepository.save(baoMau);
    }

    public boolean createBaoMau(String email, String password, String firstname, String lastname, String description){
        if(haveEmailBefore(email)){
            return false;
        }
        BaoMau baoMau=new BaoMau();
        baoMau.setEmail(email);
        baoMau.setPassword(password);
        baoMau.setFirstname(firstname);
        baoMau.setLastname(lastname);
        baoMau.setDescription(description);
        baoMau.setCreatedtime(new Date());
        baoMau.setEnabled(false);
        baoMau.setReviews(new ArrayList<>());
        baoMauRepository.save(baoMau);
        return true;
    }
}
