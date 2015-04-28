package dsl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.boon.HTTP;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import json.templates.*;

public class JSONFetcher {


	public static List<String> artistsToString(List<Artist> artists) {
		List<String> results = new ArrayList<String>();
		for(Artist s : artists) {
			results.add(s.name);
		}
		return results;
	}

	public static List<Artist> stringsToArtists(List<String> strings) {
		List<Artist> results = new ArrayList<Artist>();
		Artist a;
		for(String s : strings) {
			a = new Artist("recreated", s);
			results.add(a);
		}
		return results;
	}

	public static ResponseHolder artistSimilarTo(String artist) {

		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		try {
			artist = URLEncoder.encode(artist, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String URL = "http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&format=json&name=" + artist;
		//		System.out.println(URL);


		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		//		System.out.println(responseHolder.response.status.message);

		for(Artist s : responseHolder.response.artists) {
			//			System.out.println(s.name.toString());
		}

		//		Response a = mapper.readValue(HTTP.getJSON(URL, headers), Response.class);
		//		puts("Artists:", a);
		//		System.out.println(HTTP.getJSON(URL, headers));

		return responseHolder;

	}


	public static <T> List<T> andListOp(List<T> list1, List<T> list2) {
		List<T> results = new ArrayList<T>();

		for (T t : list1) {
			if(list2.contains(t)) {
				results.add(t);
			}
		}

		return results;
	}

	public static <T> List<T>orListOp(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);
		List<T> list = new ArrayList<T>(set);

		return list;
		//		return new ArrayList<T>(set);	
	}




	/* Methods called directly by DSLCodeGenerator.java */
	public static ResponseHolder similarTo(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			//			System.out.println("Calling ARTIST similarTo");
			response = artistSimilarTo(queryString);
			break;
		case "ALBUM":
			//			System.out.println("Calling ALBUM similar to");
			break;
		case "GENRE":
			//			System.out.println("Calling GENRE similar to");
			break;
		}
		return response;
	}

	public static void sameGenre(String queryString, String queryType) {
		switch(queryType) {
		case "ARTIST":
			//			System.out.println("Calling ALBUM sameGenre");
			break;
		case "ALBUM":
			//			System.out.println("Calling ALBUM sameGenre");
			break;
		case "GENRE":
			//			System.out.println("Calling GENRE sameGenre");
			break;
		}
	}

	public static void sameDecade(String queryString, String queryType) {
		switch(queryType) {
		case "ARTIST":
			//			System.out.println("Calling ARTIST sameDecade");
			break;
		case "ALBUM":
			//			System.out.println("Calling ALBUM sameDecade");
			break;
		case "GENRE":
			//			System.out.println("Calling GENRE sameDecade");
			break;
		}
	}

}
