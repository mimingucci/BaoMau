package mimingucci.baomau.entity;

public class StateDTO {

	private Integer id;
	private String name;
	
	public StateDTO() {
		// TODO Auto-generated constructor stub
	}

	public StateDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
