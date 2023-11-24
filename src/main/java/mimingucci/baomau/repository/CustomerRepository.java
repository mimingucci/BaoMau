package mimingucci.baomau.repository;

import mimingucci.baomau.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
    List<Customer> findByFirstnameOrLastname(String firstname, String lastname);

    @Query("update Customer i set i.password=?2 where i.id=?1")
    @Modifying
    void updatePassword(int id, String password);

    @Query("update Customer i set i.enabled=?2 where i.id=?1")
    @Modifying
    void updateEnabledStatus(int id, boolean enabled);
}
