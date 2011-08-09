package org.slf4j.impl;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;

public class StaticMDCBinder {
  /**
   * The unique instance of this class.
   */
  public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

  private StaticMDCBinder() {
  }

  /**
   * Currently this method always returns an instance of
   * {@link BasicMDCAdapter}.
   */
  public MDCAdapter getMDCA() {
    // note that this method is invoked only from within the static initializer of
    // the org.slf4j.MDC class.
    return new BasicMDCAdapter();
  }

  public String  getMDCAdapterClassStr() {
    return BasicMDCAdapter.class.getName();
  }
}