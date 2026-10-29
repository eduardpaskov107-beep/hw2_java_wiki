package app.wikisearch.service;

import app.wikisearch.wikiexeption.WikiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ParseResponse {
  private String response;
  private static ObjectMapper mapper = new ObjectMapper();

  public ParseResponse(String response) {
    this.response = response;
  }

  // разница между path и get в том что path возвращает объект MissingNode
  // (если к нему опять применить path то он вернет то же самое) если не смог найти
  // а get возвращае null и потом если применить get к null то будет плоха
  public List<String> getArticles() throws JsonProcessingException, WikiException {
    List<String> articles = new ArrayList<>();

    JsonNode jsonNode = mapper.readTree(response);
    JsonNode error = jsonNode.path("error");
    if (!error.isMissingNode()) {
      throw new WikiException(WikiException.Reason.ApiError);
    }

    jsonNode = jsonNode.path("query").path("search");

    if (!jsonNode.isArray()) {
      return List.of();
    }
    for (JsonNode article : jsonNode) {
      String title = article.get("title").asText();
      articles.add(title);
    }

    return articles;
  }
}
