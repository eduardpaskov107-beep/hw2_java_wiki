package app.wikisearch.service;

import app.wikisearch.wikiexeption.WikiException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WikiService {

  public List<String> search(String query) throws IOException, InterruptedException, WikiException {
    HttpResponse<String> response;

    response = getResponse(query);
    checkStatus(response);

    ParseResponse parseResponse = new ParseResponse(response.body());

    List<String> articles;

    try {
      articles = parseResponse.getArticles();
    } catch (JsonProcessingException e) {
      throw new WikiException(WikiException.Reason.JsonError);
    }

    return articles;
  }

  private HttpResponse<String> getResponse(String query) throws IOException, InterruptedException {
    String url =
        "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch="
            + URLEncoder.encode(query, StandardCharsets.UTF_8);
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .header("User-Agent", "WIKISearch")
            .build();

    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  public void checkStatus(HttpResponse<String> response) throws WikiException {
    switch (response.statusCode()) {
      case 200 -> {
        return;
      }
      case 403 -> {
        throw new WikiException(WikiException.Reason.Forbidden);
      }
      case 404 -> {
        throw new WikiException(WikiException.Reason.NotFound);
      }
      case 429 -> {
        throw new WikiException(WikiException.Reason.TooManyRequests);
      }
      default -> {
        if (response.statusCode() >= 500) {
          throw new WikiException(WikiException.Reason.ServerError);
        }
        throw new WikiException(WikiException.Reason.Unknown);
      }
    }
  }
}
