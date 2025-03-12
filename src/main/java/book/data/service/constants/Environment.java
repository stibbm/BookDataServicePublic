package book.data.service.constants;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTubeScopes;

import java.util.Arrays;
import java.util.Collection;

public class Environment {
    public static final String STRIPE_SECRET_API_KEY = "<key>";
    // secret for google cloud desktop app
    public static final String CLIENT_SECRETS = "<desktopappsecretpath>.json";
    public static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.upload", YouTubeScopes.YOUTUBE_READONLY);
    public static final String APPLICATION_NAME = "<google cloud application name>";
    public static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), "<path to save credentials from ~>");
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final String YOUTUBE_GOOGLE_API_KEY = "<key>";
}
