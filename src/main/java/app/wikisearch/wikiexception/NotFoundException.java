package app.wikisearch.wikiexception;

public class NotFoundException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: 404 Not Found";
  }
}
