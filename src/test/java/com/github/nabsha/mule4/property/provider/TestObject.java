/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.github.nabsha.mule4.property.provider;

public class TestObject {

  private String valueFromProperty;

  public String getOverrideProperty() {
    return overrideProperty;
  }

  public void setOverrideProperty(String overrideProperty) {
    this.overrideProperty = overrideProperty;
  }

  private String overrideProperty;

  public String getValueFromProperty() {
    return valueFromProperty;
  }

  public void setValueFromProperty(String valueFromProperty) {
    this.valueFromProperty = valueFromProperty;
  }
}
