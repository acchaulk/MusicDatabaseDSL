package dsl.utility;


public enum DSLType {

	SONG, ARTIST, ALBUM, GENRE, UNDEFINED;

	public static DSLType getType(String s)
	{
		switch (s) {
		case "song": return SONG;
		case "artist": return ARTIST;
		case "album": return ALBUM;
		case "genre": return GENRE;
		}
		return UNDEFINED;
	}
}
