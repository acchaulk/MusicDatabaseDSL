package json.templates;

import java.util.List;

import json.templates.albums.Data;
import json.templates.albums.Pagination;
import json.templates.albums.Status;

//import dsl.EchoNestBoonTest.Response;

public class ResponseHolder {
	public Response response;


	public Status status;
	public Pagination pagination;
	public List<Data> data;


	public ResponseHolder(Response response) {
		super();
		this.response = response;
	}


	public ResponseHolder(Status status, Pagination pagination, List<Data> data) {
		super();
		this.status = status;
		this.pagination = pagination;
		this.data = data;
	}


	@Override
	public String toString() {
		return "ResponseHolder [response=" + response + "]";
	}

}