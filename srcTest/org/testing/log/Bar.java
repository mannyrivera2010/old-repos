package org.testing.log;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Bar.
 */
public class Bar {
  
  /** The logger. */
  static Logger logger = Logger.getLogger(Bar.class);

  /**
   * Do it.
   */
  public void doIt() {
    logger.debug("Did it again!");
  }
}