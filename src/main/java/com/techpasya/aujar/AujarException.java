package com.techpasya.aujar;

public class AujarException extends Exception {


  public AujarException() {
    super();
  }

  public AujarException(String errorMessage) {
    super(errorMessage);
  }

  public AujarException(Throwable cause) {
    super(cause);
  }

  public AujarException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }

  public Throwable getUnderlyingException() {
    return super.getCause();
  }

  @Override
  public String toString() {
    Throwable cause = getUnderlyingException();
    if (cause == null || cause == this) {
      return super.toString();
    } else {
      return super.toString() + " [See nested exception: " + cause + "]";
    }
  }


}
