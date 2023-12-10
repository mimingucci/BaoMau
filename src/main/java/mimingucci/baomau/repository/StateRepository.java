package mimingucci.baomau.repository;

import java.util.List;

import mimingucci.baomau.entity.Country;
import mimingucci.baomau.entity.State;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Integer>{
	public List<State> findByCountryOrderByNameAsc(Country country);

	public State findByName(String name);
}
