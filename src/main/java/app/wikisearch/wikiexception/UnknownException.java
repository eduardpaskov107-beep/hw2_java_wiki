package app.wikisearch.wikiexception;

public class UnknownException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: Unknown Error";
  }
}
