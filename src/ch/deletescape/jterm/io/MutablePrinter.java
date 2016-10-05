package ch.deletescape.jterm.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MutablePrinter {
  private final PrintStream stream;
  private boolean muted = false;
  private boolean forced = false;
  private static final PrintStream EMPTY_STREAM = new PrintStream(new OutputStream() {

    @Override
    public void write(int b) throws IOException {
    }
  });

  MutablePrinter(PrintStream stream) {
    this.stream = stream;
  }

  public String println(Object x) {
    if (forced || !muted) {
      stream.println(x);
      forced = false;
    }
    return x.toString();
  }

  public String println() {
    return println("");
  }

  public String print(Object x) {
    if (forced || !muted) {
      stream.print(x);
      forced = false;
    }
    return x.toString();
  }

  public PrintStream getPrintStream() {
    if (forced || !muted) {
      forced = false;
      return stream;
    }
    return EMPTY_STREAM;
  }

  public boolean mute(boolean mute) {
    boolean currState = muted;
    muted = mute;
    return currState;
  }

  public boolean toggleMute() {
    muted = !muted;
    return muted;
  }

  public boolean isMuted() {
    return muted;
  }

  public MutablePrinter forced() {
    forced = true;
    return this;
  }
}