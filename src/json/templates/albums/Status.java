package json.templates.albums;

public class Status {
	public String code;
	public String message;
	public String api;


	public Status(String code, String message, String api) {
		super();
		this.code = code;
		this.api = api;
		this.message = message;
	}
}
