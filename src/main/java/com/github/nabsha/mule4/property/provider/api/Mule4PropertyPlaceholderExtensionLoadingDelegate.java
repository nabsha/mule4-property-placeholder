/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.github.nabsha.mule4.property.provider.api;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterGroupDeclarer;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.api.loader.ExtensionLoadingDelegate;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.meta.Category.SELECT;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;

/**
 * Declares extension for Secure Properties Configuration module
 *
 * @since 1.0
 */
public class Mule4PropertyPlaceholderExtensionLoadingDelegate implements ExtensionLoadingDelegate {

  // TODO replace with you extension name. This must be a meaningful name for this module.
  public static final String EXTENSION_NAME = "Mule4 Property Placeholder";
  public static final String CONFIG_ELEMENT = "config";

  @Override
  public void accept ( ExtensionDeclarer extensionDeclarer, ExtensionLoadingContext context ) {
    ConfigurationDeclarer configurationDeclarer = extensionDeclarer.named( EXTENSION_NAME )
      .describedAs( String.format( "Crafted %s Extension", EXTENSION_NAME ) )
      .withCategory( SELECT )
      .onVersion( "1.0.0" )
      .fromVendor( "com.github.nabsha" )
      // This defines a global element in the extension with name config
      .withConfig( CONFIG_ELEMENT );

    ParameterGroupDeclarer defaultParameterGroup = configurationDeclarer.onDefaultParameterGroup();
    // TODO you can add/remove configuration parameter using the code below.

    defaultParameterGroup
      .withRequiredParameter( "location" ).ofType( BaseTypeBuilder.create( JAVA ).stringType().build() )
      .withExpressionSupport( NOT_SUPPORTED )
      .describedAs( "Comma separated list of file paths" );

    defaultParameterGroup
      .withOptionalParameter( "ignore-resource-not-found" )
      .defaultingTo( false )
      .ofType( BaseTypeBuilder.create( JAVA ).booleanType().build() )
      .withExpressionSupport( NOT_SUPPORTED )
      .describedAs( "Specifies if failure to find the property resource location should be ignored. Default is \"false\", meaning that " +
        " if there is no file in the location specified an exception will be raised at runtime." );

    defaultParameterGroup
      .withOptionalParameter( "ignore-unresolvable" )
      .defaultingTo( false )
      .ofType( BaseTypeBuilder.create( JAVA ).booleanType().build() )
      .withExpressionSupport( NOT_SUPPORTED )
      .describedAs( "Specifies if failure to find the property value to replace " +
        " a key should be ignored. Default is \"false\", meaning " +
        " that this placeholder configurer will raise an exception " +
        " if it cannot resolve a key. Set to \"true\" to allow the " +
        " configurer to pass on the key to any others in the " +
        " context that have not yet visited the key in question." );

    defaultParameterGroup
      .withOptionalParameter( "file-encoding" )
      .ofType( BaseTypeBuilder.create( JAVA ).stringType().build() )
      .defaultingTo( "" )
      .withExpressionSupport( NOT_SUPPORTED )
      .describedAs( "Specifies the encoding to use for parsing properties " +
        " files. Default is none, using the java.util.Properties " +
        " default encoding. Only applies to classic properties " +
        " files, not to XML files." );

  }

}
