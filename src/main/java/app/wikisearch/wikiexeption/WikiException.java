package app.wikisearch.wikiexeption;

public class WikiException extends Exception {
  public enum Reason {
    NotFound,
    TooManyRequests,
    ServerError,
    Forbidden,
    JsonError,
    ApiError,
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
        return "WikiExсeption: 404 Not Found";
      }
      case TooManyRequests -> {
        return "WikiExсeption: 429 Too Many Requests";
      }
      case ServerError -> {
        return "WikiExсeption: 5xx Server Error";
      }
      case Forbidden -> {
        return "WikiExсeption: 403 Forbidden";
      }
      case JsonError -> {
        return "WikiExсeption: Invalid Json response";
      }
      case ApiError -> {
        return "WikiExсeption: Api Error in Json response";
      }
      default -> {
        return "WikiExсeption: Unknown Error";
      }
    }
  }
}
