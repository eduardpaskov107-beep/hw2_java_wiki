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
import java.util.ArrayList;
import java.util.List;

public class WikiService {
  private ParseResponse parseResponse;

  public List<String> search(String query) {
    HttpResponse<String> response;

    try {
      response = getResponse(query);
    } catch (IOException e) {
      System.err.println("IOException: Network error");
      return null;
    } catch (InterruptedException e) {
      System.err.println("InterruptedException: App was interrupted");
      return null;
    }

    try {
      checkStatus(response);
    } catch (WikiException e) {
      System.out.println(e.getMessage());
      return null;
    }

    parseResponse = new ParseResponse(response.body());

    List<String> articles = new ArrayList<>();
    try {
      articles = parseResponse.getArticles();
    } catch (JsonProcessingException e) {
      System.err.println("JsonProcessingException: " + e.getMessage());
      return null;
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
