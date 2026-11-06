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
        return "WikiException: 404 Not Found";
      }
      case TooManyRequests -> {
        return "WikiException: 429 Too Many Requests";
      }
      case ServerError -> {
        return "WikiException: 5xx Server Error";
      }
      case Forbidden -> {
        return "WikiException: 403 Forbidden";
      }
      case JsonError -> {
        return "WikiException: Invalid Json response";
      }
      case ApiError -> {
        return "WikiException: Api Error in Json response";
      }
      default -> {
        return "WikiException: Unknown Error";
      }
    }
  }
}
