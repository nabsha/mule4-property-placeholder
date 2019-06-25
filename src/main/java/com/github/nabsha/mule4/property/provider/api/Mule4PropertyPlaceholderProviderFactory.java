/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.github.nabsha.mule4.property.provider.api;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.config.api.dsl.model.ConfigurationParameters;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProviderFactory;

import static com.github.nabsha.mule4.property.provider.api.Mule4PropertyPlaceholderExtensionLoadingDelegate.CONFIG_ELEMENT;
import static com.github.nabsha.mule4.property.provider.api.Mule4PropertyPlaceholderExtensionLoadingDelegate.EXTENSION_NAME;
import static org.mule.runtime.api.component.ComponentIdentifier.builder;

/**
 * Builds the provider for a custom-properties-provider:config element.
 *
 * @since 0.0.1
 */
public class Mule4PropertyPlaceholderProviderFactory implements ConfigurationPropertiesProviderFactory {

  public static final String EXTENSION_NAMESPACE =
    EXTENSION_NAME.toLowerCase().replace( " ", "-" );
  private static final ComponentIdentifier CUSTOM_PROPERTIES_PROVIDER =
    builder().namespace( EXTENSION_NAMESPACE ).name( CONFIG_ELEMENT ).build();

  @Override
  public ComponentIdentifier getSupportedComponentIdentifier () {
    return CUSTOM_PROPERTIES_PROVIDER;
  }

  @Override
  public ConfigurationPropertiesProvider createProvider ( ConfigurationParameters parameters,
                                                          ResourceProvider externalResourceProvider ) {

    String locationParameterValue = getValue( parameters, "location" );
    boolean ignoreResourceNotFound = Boolean.parseBoolean( getValue( parameters, "ignore-resource-not-found" ) );
    boolean ignoreUnresolvable = Boolean.parseBoolean( getValue( parameters, "ignore-unresolvable" ) );
    String encoding = getValue( parameters, "file-encoding" );

    return new Mule4PropertyPlaceholderProvider( locationParameterValue, ignoreResourceNotFound, ignoreUnresolvable, encoding, externalResourceProvider );
  }

  private String getValue ( ConfigurationParameters parameters, String key ) {
    try {
      return parameters.getStringParameter( key );
    } catch ( NullPointerException e ) {
      return null;
    }
  }
}
