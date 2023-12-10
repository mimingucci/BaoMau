package mimingucci.baomau.controller;

import java.util.ArrayList;
import java.util.List;

import mimingucci.baomau.entity.Country;
import mimingucci.baomau.entity.State;
import mimingucci.baomau.entity.StateDTO;
import mimingucci.baomau.repository.CountryRepository;
import mimingucci.baomau.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/state")
public class StateController {

	@Autowired
	private StateRepository repo;

	@Autowired
	private CountryRepository countryRepository;
	
	@GetMapping("get/list_by_country/{id}")
	public ResponseEntity<?> listByCountry(@PathVariable("id") Integer countryId) {
		List<State> listStates = repo.findByCountryOrderByNameAsc(new Country(countryId));
		List<StateDTO> result = new ArrayList<>();
		
		for (State state : listStates) {
			result.add(new StateDTO(state.getId(), state.getName()));
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/create/{id}")
	public ResponseEntity<?> save(@RequestBody State state, @PathVariable(name = "id") Integer id) {
		Country country=countryRepository.findById(id).get();
		state.setCountry(country);
		State savedState = repo.save(state);
		return new ResponseEntity<>(savedState, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
}
