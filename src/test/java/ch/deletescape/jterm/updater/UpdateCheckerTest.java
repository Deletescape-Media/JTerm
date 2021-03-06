package ch.deletescape.jterm.updater;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ch.deletescape.jterm.updater.UpdateChecker.Update;

public class UpdateCheckerTest {

  @Test
  public void remoteVersionIsNewer() {
    assertThat(UpdateChecker.remoteVersionIsNewer("v1.0.0", "v1.0.1"), is(true));
    assertThat(UpdateChecker.remoteVersionIsNewer("v1.0.1", "v1.0.0"), is(false));
    assertThat(UpdateChecker.remoteVersionIsNewer("v0.0.1", "v1.0.0"), is(true));
    assertThat(UpdateChecker.remoteVersionIsNewer("v1.0.0", "v1.0.0"), is(false));
    assertThat(UpdateChecker.remoteVersionIsNewer("v1.0.0", "v1.1.0"), is(true));
  }

  // Test for Update class -> for coverage
  @Test
  public void updateTest() {
    Update update = new UpdateChecker().new Update("v1.0.0", "Stable 1.0.0", true);
    assertThat(update.getVersion(), is("v1.0.0"));
    assertThat(update.getName(), is("Stable 1.0.0"));
    assertThat(update.isNewer(), is(true));
  }
}
