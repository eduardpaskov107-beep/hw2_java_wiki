package app.wikisearch.wikiexception;

public class TooManyRequestsException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: Too Many Requests";
  }
}
