package com.github.nabsha.mule4.property.provider.api;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.component.location.ComponentProvider;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.util.Preconditions;


public class Mule4PropertyPlaceholderConfigurationException extends MuleRuntimeException implements ComponentProvider {
  private Component component;

  public Mule4PropertyPlaceholderConfigurationException ( I18nMessage message, Component component ) {
    super( message );
    Preconditions.checkNotNull( component, "component cannot be null" );
    this.component = component;
  }

  public Mule4PropertyPlaceholderConfigurationException ( I18nMessage message, Component component, Exception e ) {
    super( message, e );
    Preconditions.checkNotNull( component, "component cannot be null" );
    this.component = component;
  }

  public Component getComponent () {
    return this.component;
  }
}
