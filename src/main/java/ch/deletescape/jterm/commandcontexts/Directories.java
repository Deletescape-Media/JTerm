package ch.deletescape.jterm.commandcontexts;

import ch.deletescape.jterm.CommandUtils;
import ch.deletescape.jterm.JTerm;
import ch.deletescape.jterm.Util;
import ch.deletescape.jterm.config.Resources;
import ch.deletescape.jterm.io.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class Directories extends CommandContext {
  private static final Logger LOGGER = LogManager.getLogger();

  @Override
  protected void init() {
    CommandUtils.addListener("cd", this::cd);
    CommandUtils.addListener("pwd", o -> pwd());
    CommandUtils.addListener("ls", this::ls);
    CommandUtils.addListener("mkdir", this::mkdir);
  }

  private String pwd() {
    return Printer.out.println(JTerm.getCurrPath());
  }

  private boolean cd(String cmd) throws IOException {
    String command = CommandUtils.parseInlineCommands(cmd);
    Path path = JTerm.getCurrPath().resolve(Util.makePathString(command)).toRealPath();
    if (Files.isDirectory(path)) {
      JTerm.setCurrPath(path);
      pwd();
      return true;
    }
    Printer.err.println(Resources.getString("Directories.PathIsNotDirectory"), path);
    return false;
  }

  private String ls(String cmd) throws IOException {
    String command = CommandUtils.parseInlineCommands(cmd);
    StringBuilder out = new StringBuilder();
    Path path = JTerm.getCurrPath().resolve(Util.makePathString(command));
    try (Stream<Path> stream = Files.list(path)) {
      stream.forEach(p -> out.append(p.getFileName().toString() + "\n"));
    } catch (NoSuchFileException e) {
      Printer.err.println(Resources.getString("PathNotFound"), path);
      LOGGER.error(e.toString(), e);
    } catch (NotDirectoryException e) {
      out.append(path);
      out.append('\n');
    }
    return Printer.out.println(out);
  }

  private boolean mkdir(String cmd) throws IOException {
    String command = CommandUtils.parseInlineCommands(cmd);
    Path path = JTerm.getCurrPath().resolve(Util.makePathString(command));
    try {
      Files.createDirectory(path);
      return true;
    } catch (FileAlreadyExistsException e) {
      Printer.err.println(Resources.getString("Directories.DirectoryAlreadyExists"), path);
      LOGGER.error(e.toString(), e);
    }
    return false;
  }
}
