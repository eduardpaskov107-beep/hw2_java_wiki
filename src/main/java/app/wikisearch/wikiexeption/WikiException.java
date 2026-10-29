package app.wikisearch.wikiexeption;

public class WikiException extends Exception {
  public enum Reason {
    NotFound,
    TooManyRequests,
    ServerError,
    Forbidden,
    Unknown
  }

  private Reason reason;

  public WikiException(Reason reason) {
    this.reason = reason;
  }

  @Override
  public String getMessage() {
    switch (reason) {
      case NotFound -> {
        return "WikiExeption: 404 Not Found";
      }
      case TooManyRequests -> {
        return "WikiExeption: 429 Too Many Requests";
      }
      case ServerError -> {
        return "WikiExeption: 5xx Server Error";
      }
      case Forbidden -> {
        return "WikiExeption: 403 Forbidden";
      }
      default -> {
        return "WikiExeption: Unknown Error";
      }
    }
  }
}
