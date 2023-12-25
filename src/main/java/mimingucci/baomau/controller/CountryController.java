package mimingucci.baomau.controller;

import java.util.List;

import mimingucci.baomau.entity.Country;
import mimingucci.baomau.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/country")
public class CountryController {

	@Autowired
	private CountryRepository repo;

	@GetMapping("get/all")
	public ResponseEntity<?> listAll() {
		List<Country> alls= repo.findAllByOrderByNameAsc();
		return new ResponseEntity<>(alls, HttpStatus.OK);
	}

	@GetMapping("get/{code}")
	public ResponseEntity<?> getByCode(@PathVariable(name = "code") String code) {
		Country country=repo.findByCode(code);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> save(@RequestBody Country country) {
		Country savedCountry = repo.save(country);
		return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
}
