package app.wikisearch.wikiexception;

public class ServerException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: 5xx Server Error";
  }
}
