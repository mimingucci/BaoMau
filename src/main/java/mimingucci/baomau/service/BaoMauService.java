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

    public BaoMau updateBaoMau(int id, BaoMau baoMau) throws BaoMauNotFoundException {
        BaoMau bm=baoMauRepository.findById(id).get();
        if(bm==null){
            throw new BaoMauNotFoundException("Khong tim thay bao mau voi id: "+id);
        }
        if(baoMau.getFirstname()!=null && baoMau.getFirstname().length()>0)bm.setFirstname(baoMau.getFirstname());
        if(baoMau.getLastname()!=null && baoMau.getLastname().length()>0)bm.setLastname(baoMau.getLastname());
        if(baoMau.getPassword()!=null && baoMau.getPassword().length()>0)bm.setPassword(baoMau.getPassword());
        if(baoMau.getDescription()!=null && baoMau.getDescription().length()>0)bm.setDescription(baoMau.getDescription());
        return baoMauRepository.save(bm);
    }

    public BaoMau createBaoMau(String email, String password, String firstname, String lastname, String description){
        if(haveEmailBefore(email)){
            return null;
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
        return baoMauRepository.save(baoMau);
    }
}
