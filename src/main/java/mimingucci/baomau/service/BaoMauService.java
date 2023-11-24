package mimingucci.baomau.service;

import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.BaoMau;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.repository.BaoMauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
