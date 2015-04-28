package json.templates;

public class Artist {
    public String id;
    public String name;


    public Artist(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {
        return "Artist{" + "id: " + id + " name: " + name + '}';
    }
}