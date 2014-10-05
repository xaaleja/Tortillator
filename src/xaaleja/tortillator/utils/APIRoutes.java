package xaaleja.tortillator.utils;

public class APIRoutes 
{
	//public static final String IP ="http://192.168.1.105/";
	public static final String IP ="http://tortillatorapi.esy.es/api/v1/";
	public static final String GET_OR_PUT_BAR = IP+"bars/";
	public static final String GET_OR_PUT_USER = IP+"users/";
	public static final String GET_OR_PUT_COMMENT = IP+"comments/";
	public static final String GET_OR_PUT_VOTE = IP+"votes/";
	public static final String GET_OR_PUT_TORTILLA = IP+"tortillas/";

	public static final String GET_OR_POST_BARS = IP+"bars.json";
	public static final String GET_OR_POST_USERS = IP+"users.json";
	public static final String GET_OR_POST_COMMENTS = IP+"comments.json";
	public static final String GET_OR_POST_VOTES = IP+"votes.json";
	public static final String GET_OR_POST_TORTILLAS = IP+"tortillas.json";

	public static final String LOGIN_USER = IP+"users/";
	public static final String USER_EXISTS = IP+"users/";
	public static final String EMAIL_EXISTS = IP+"users/";
	
	//public static final String GET_NEAR_BARS;
	
	//public static final String GET_TORTILLA_BY_BAR_ID;
	public static final String GET_TORTILLAS_USER = IP+"tortillas/";
	public static final String GET_TORTILLAS_RANKING = IP+"tortilla/ranking.json";
	//public static final String GET_TORTILLAS_RECOMMENDATIONS;
	
	//public static final String GET_USER_RATING;
	//public static final String GET_VOTES_NUMBER;
	
	//public static final String GET_COMMENTS_TORTILLA;
	
}
