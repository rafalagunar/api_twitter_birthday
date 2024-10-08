package org.api;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.model.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TwitterBirthdayBot {

    private static final String CONSUMER_KEY = System.getenv("TWITTER_CONSUMER_KEY"); // Tu Consumer Key
    private static final String CONSUMER_SECRET = System.getenv("TWITTER_CONSUMER_SECRET"); // Tu Consumer Secret
    private static final String ACCESS_TOKEN = System.getenv("TWITTER_ACCESS_TOKEN"); // Tu Access Token
    private static final String ACCESS_TOKEN_SECRET = System.getenv("TWITTER_ACCESS_TOKEN_SECRET"); // Tu Access Token Secret

    private static final String TWITTER_API_URL = "https://api.twitter.com/2/tweets";

    public static void main(String[] args) throws Exception {
        sendBirthdaymessage();
    }

    private static void sendBirthdaymessage() throws Exception {
        // Crear servicio OAuth
        OAuth10aService service = new ServiceBuilder(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .build(TwitterApi.instance());

        // Crear el token de acceso
        OAuth1AccessToken accessToken = new OAuth1AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        // Crear la solicitud OAuth
        OAuthRequest request = new OAuthRequest(Verb.POST, TWITTER_API_URL);

        // Cuerpo del tweet
        String tweetText = "Intentando twittear desde IntelIJ utilizando Github Actions" + getCurrentTimestamp();;
        request.addHeader("Content-Type", "application/json");
        request.setPayload("{ \"text\": \"" + tweetText + "\" }");


        // Firmar la solicitud con OAuth 1.0a
        service.signRequest(accessToken, request);

        // Ejecutar la solicitud
        try (Response response = service.execute(request)) {
            if (response.isSuccessful()) {
                System.out.println("Tweet publicado: " + tweetText);
            } else {
                System.err.println("Error al publicar tweet: " + response.getBody());
            }
        }
    }

    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}

