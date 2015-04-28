package json.templates;

public class Status {
    public String code;
    public String message;
    public String version;


    public Status(String code, String message, String version) {
        super();
        this.code = code;
        this.version = version;
        this.message = message;
    }
}
