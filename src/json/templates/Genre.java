package json.templates;

public class Genre {
	public String description;
	public String name;
	public String similarity;


	public Genre(String description, String name, String similarity) {
		super();
		this.description = description;
		this.name = name;
		this.similarity = similarity;
	}


	@Override
	public String toString() {
		return "Genre{" + "description: " + description + " name: " + name + '}';
	}


}
