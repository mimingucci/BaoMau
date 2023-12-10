package mimingucci.baomau.repository;

import java.util.List;

import mimingucci.baomau.entity.Country;
import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country, Integer>{

	
	public List<Country> findAllByOrderByNameAsc();

	public Country findByCode(String code);
}
