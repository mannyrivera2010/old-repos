package org.testing.log;

//Import log4j classes.
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

// TODO: Auto-generated Javadoc
/**
 * The Class MyApp.
 */
public class MyApp {

// Define a static logger variable so that it references the
// Logger instance named "MyApp".
/** The logger. */
static Logger logger = Logger.getLogger(MyApp.class);

/**
 * The main method.
 *
 * @param args the arguments
 */
public static void main(String[] args) {

  // Set up a simple configuration that logs on the console.
  BasicConfigurator.configure();

  logger.info("Entering application.");
  Bar bar = new Bar();
  bar.doIt();
  logger.debug("Exiting application.");
}


}