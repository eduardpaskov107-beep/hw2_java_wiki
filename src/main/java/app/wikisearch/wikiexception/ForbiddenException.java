package app.wikisearch.wikiexception;

public class ForbiddenException extends WikiException {
  @Override
  public String getMessage() {
    return "WikiException: 403 Forbidden";
  }
}
