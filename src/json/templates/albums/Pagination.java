package json.templates.albums;

public class Pagination {
	public int count;
    public int offset;
	public Pagination(int count, int offset) {
		super();
		this.count = count;
		this.offset = offset;
	}
}
