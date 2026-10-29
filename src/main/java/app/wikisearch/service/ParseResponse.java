package app.wikisearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ParseResponse {
  private String response;

  public ParseResponse(String response)  {
    this.response = response;
  }

  public List<String> getArticles() throws JsonProcessingException {
    List<String> articles = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode jsonNode = objectMapper.readTree(response);
    jsonNode = jsonNode.get("query").get("search");

    for (JsonNode article : jsonNode) {
      String title = article.get("title").asText();
      articles.add(title);
    }

    return articles;
  }
}
