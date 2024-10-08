package org.api;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.model.Response;

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
        String tweetText = "Intentando twittear desde IntelIJ 2";
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
}

/*
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;

public class OAuthPostRequest {

    public static void main(String[] args) {
        // Claves y tokens necesarios para la autenticación OAuth 1.0
        String consumerKey = "TU_CONSUMER_KEY";
        String consumerSecret = "TU_CONSUMER_SECRET";
        String token = "TU_TOKEN";
        String tokenSecret = "TU_TOKEN_SECRET";

        // Endpoint al que enviar la solicitud
        String url = "https://api.ejemplo.com/endpoint";

        // Body de ejemplo para la solicitud POST
        String tweetText = "Intentando twittear desde IntelIJ";
        String jsonBody = "{ \"text\": \"" + tweetText + "\" }";

        // Crear el consumidor OAuth
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(token, tokenSecret);

        // Crear el cliente HTTP
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Crear la solicitud POST
            HttpPost request = new HttpPost(url);

            // Establecer el tipo de contenido
            request.setHeader("Content-Type", "application/json");

            // Añadir el cuerpo JSON a la solicitud
            StringEntity entity = new StringEntity(jsonBody);
            request.setEntity(entity);

            // Firmar la solicitud con OAuth
            consumer.sign(request);

            // Ejecutar la solicitud
            HttpResponse response = httpClient.execute(request);

            // Imprimir la respuesta
            System.out.println("Código de respuesta: " + response.getStatusLine().getStatusCode());
            System.out.println("Respuesta: " + response.getEntity().getContent().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

*/