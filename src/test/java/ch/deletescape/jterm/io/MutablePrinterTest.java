package ch.deletescape.jterm.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MutablePrinterTest {
  private static final String LINESEPERATOR = System.lineSeparator();

  @Test
  public void printTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.isMuted(), is(false));
    assertThat(printer.print("Test"), is("Test"));
    assertThat(out.sb.toString(), is("Test"));
  }

  @Test
  public void printNullTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.print(null), is((String) null));
    assertThat(out.sb.toString(), is("null"));
  }

  @Test
  public void toggleMuteTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.isMuted(), is(false));
    assertThat(printer.toggleMute(), is(true));
    assertThat(printer.isMuted(), is(true));
    assertThat(printer.toggleMute(), is(false));
    assertThat(printer.isMuted(), is(false));
  }

  @Test
  public void printlnTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.println("Test"), is("Test"));
    assertThat(out.sb.toString(), is("Test" + LINESEPERATOR));
  }

  @Test
  public void printlnNoParamTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.println(), is(""));
    assertThat(out.sb.toString(), is(LINESEPERATOR));
  }

  @Test
  public void printMutedTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.mute(true), is(false));
    assertThat(printer.isMuted(), is(true));
    assertThat(printer.print("Test"), is("Test"));
    assertThat(out.sb.toString(), is(""));
  }

  @Test
  public void printForcedTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.mute(true), is(false));
    assertThat(printer.forced().print("Test"), is("Test"));
    assertThat(out.sb.toString(), is("Test"));
  }

  public void printForcedNotMutedTest() {
    MockOutputstream out = new MockOutputstream();
    MutablePrinter printer = new MutablePrinter(new PrintStream(out));
    assertThat(printer.forced().print("Test"), is("Test"));
    assertThat(out.sb.toString(), is("Test"));
  }

  class MockOutputstream extends OutputStream {
    StringBuilder sb = new StringBuilder();

    @Override
    public void write(int b) throws IOException {
      sb.append((char) b);
    }

  }
}
