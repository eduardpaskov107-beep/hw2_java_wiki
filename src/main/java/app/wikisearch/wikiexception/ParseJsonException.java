package app.wikisearch.wikiexception;

public class ParseJsonException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: Invalid Json response";
  }
}
