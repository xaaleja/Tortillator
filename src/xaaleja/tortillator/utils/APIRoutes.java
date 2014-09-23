package xaaleja.tortillator.utils;

public class APIRoutes 
{
	public static final String IP ="http://192.168.1.105/";
	
	public static final String GET_OR_PUT_BAR = IP+"TortillatorAPI/web/api/v1/bars/";
	public static final String GET_OR_PUT_USER = IP+"TortillatorAPI/web/api/v1/users/";
	public static final String GET_OR_PUT_COMMENT = IP+"TortillatorAPI/web/api/v1/comments/";
	public static final String GET_OR_PUT_VOTE = IP+"TortillatorAPI/web/api/v1/votes/";
	public static final String GET_OR_PUT_TORTILLA = IP+"TortillatorAPI/web/api/v1/tortillas/";

	public static final String GET_OR_POST_BARS = IP+"TortillatorAPI/web/api/v1/bars.json";
	public static final String GET_OR_POST_USERS = IP+"TortillatorAPI/web/api/v1/users.json";
	public static final String GET_OR_POST_COMMENTS = IP+"TortillatorAPI/web/api/v1/comments.json";
	public static final String GET_OR_POST_VOTES = IP+"TortillatorAPI/web/api/v1/votes.json";
	public static final String GET_OR_POST_TORTILLAS = IP+"TortillatorAPI/web/api/v1/tortillas.json";

	public static final String LOGIN_USER = IP+"TortillatorAPI/web/api/v1/users/";
	public static final String USER_EXISTS = IP+"TortillatorAPI/web/api/v1/users/";
	public static final String EMAIL_EXISTS = IP+"TortillatorAPI/web/api/v1/users/";
	
	//public static final String GET_NEAR_BARS;
	
	//public static final String GET_TORTILLA_BY_BAR_ID;
	public static final String GET_TORTILLAS_USER = IP+"TortillatorAPI/web/api/v1/tortillas/";
	public static final String GET_TORTILLAS_RANKING = IP+"TortillatorAPI/web/api/v1/tortilla/ranking.json";
	//public static final String GET_TORTILLAS_RECOMMENDATIONS;
	
	//public static final String GET_USER_RATING;
	//public static final String GET_VOTES_NUMBER;
	
	//public static final String GET_COMMENTS_TORTILLA;
	
}
