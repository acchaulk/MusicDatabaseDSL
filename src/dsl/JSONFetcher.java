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




	public static ResponseHolder artistSimilarTo(String artist) {

		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		artist = convertStringToUTF(artist);
		System.out.println(artist);
		String URL = "http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&name=" + artist;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		return responseHolder;

	}
	
	public static ResponseHolder artistSameGenre(String genre) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);
		genre = genre.toLowerCase();
		System.out.println(genre);
		
		String URL = "http://developer.echonest.com/api/v4/artist/search?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&genre=" + genre;
		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		
		return responseHolder;
	}
	
	public static ResponseHolder artistSameDecade(String decade) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		decade = decade.replaceAll("^\"|\"$", "");
//		decade = decade.toLowerCase();
		System.out.println(decade);
		
		int year = Integer.parseInt(decade);
		
		int mod = year % 10;
		String startYear = Integer.toString(year - mod - 1);
		String endYear = Integer.toString(year - mod + 10);
		startYear = convertStringToUTF(startYear);
		endYear = convertStringToUTF(endYear);
		
		
		String URL = "http://developer.echonest.com/api/v4/artist/search?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&artist_start_year_before=" + endYear + "&artist_start_year_after=" + startYear;
		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		
		return responseHolder;
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

	public static ResponseHolder sameGenre(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			response = artistSameGenre(queryString);
			//			System.out.println("Calling ALBUM sameGenre");
			break;
		case "ALBUM":
			//			System.out.println("Calling ALBUM sameGenre");
			break;
		case "GENRE":
			//			System.out.println("Calling GENRE sameGenre");
			break;
		}
		return response;
	}

	public static ResponseHolder sameDecade(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			response = artistSameDecade(queryString);
			//			System.out.println("Calling ARTIST sameDecade");
			break;
		case "ALBUM":
			//			System.out.println("Calling ALBUM sameDecade");
			break;
		case "GENRE":
			//			System.out.println("Calling GENRE sameDecade");
			break;
		}
		return response;
	}
	
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
	
	public static String convertStringToUTF(String s) {
		String result = null;
		try {
			result = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
