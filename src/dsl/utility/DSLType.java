package dsl.utility;


public enum DSLType {

	SONG, ARTIST, ALBUM, UNDEFINED;

	public static DSLType getType(String s)
	{
		switch (s) {
		case "song": return SONG;
		case "artist": return ARTIST;
		case "album": return ALBUM;
		}
		return UNDEFINED;
	}
}
