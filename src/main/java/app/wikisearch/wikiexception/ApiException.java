package app.wikisearch.wikiexception;

public class ApiException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: Api Error in Json response";
  }
}
