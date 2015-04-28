package json.templates;

//import dsl.EchoNestBoonTest.Response;

public class ResponseHolder {
    public Response response;


    public ResponseHolder(Response response) {
        super();
        this.response = response;
    }


    @Override
    public String toString() {
        return "ResponseHolder [response=" + response + "]";
    }

}