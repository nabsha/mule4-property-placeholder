package com.github.nabsha.mule4.property.provider.api;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.runtime.api.component.AbstractComponent;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationProperty;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static java.util.Optional.of;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

public class Mule4PropertyPlaceholderProvider extends AbstractComponent implements ConfigurationPropertiesProvider {

  Log logger = LogFactory.getLog( Mule4PropertyPlaceholderProvider.class );

  ResourceProvider resourceProvider;
  String encoding;
  boolean ignoreUnresolvable;


  protected final Map< String, ConfigurationProperty > configurationAttributes = new HashMap<>();

  public Mule4PropertyPlaceholderProvider ( String locationParameterValue, boolean ignoreResourceNotFound, boolean ignoreUnresolvable, String encoding, ResourceProvider externalResourceProvider ) {

    this.resourceProvider = externalResourceProvider;
    this.encoding = encoding;
    this.ignoreUnresolvable = ignoreUnresolvable;

    logger.debug( "Initializing mule4-property-placeholder ..." );
    if ( locationParameterValue.contains( "," ) ) {
      List< String > locationList = Arrays.asList( locationParameterValue.split( "," ) );
      locationList.forEach( ( location ) -> loadProperties( location.trim(), ignoreResourceNotFound ) );
    } else {
      loadProperties( locationParameterValue.trim(), ignoreResourceNotFound );
    }
  }


  /**
   * Tries to load properties from classpath first, if not found in classpath then tries to find in file system
   * @param file
   * @return InputStream to underlying resource
   * @throws IOException
   */
  protected InputStream getResourceInputStream ( String file ) throws IOException {
    InputStream is = getResourceFromSystemClassLoader(file);
    if (is != null){
      logger.debug(file + " found in system classpath");
      return is;
    }

     is = resourceProvider.getResourceAsStream( file );
    // if failed to load from classpath, try loading from file system
    if ( is == null ) {
      File file1 = new File(file);
      if (file1.exists()) {
        logger.debug(file + " found in file system");
        is = new FileInputStream( file1 );
      }
    } else {
      logger.debug(file + " found in classpath");
    }
    return is;
  }

  private InputStream getResourceFromSystemClassLoader(final String file) {
    final URL resourceUrl = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getResource(file);
    if (resourceUrl != null) {
      final File resourceFile = new File(resourceUrl.getPath());
      if (resourceFile.exists()) {
        try {
          return new FileInputStream(resourceFile);
        } catch (FileNotFoundException e) {
          throw new Mule4PropertyPlaceholderConfigurationException(createStaticMessage(String.format("Couldn't read file %s from SystemClassLoader", resourceFile.getPath())), this, e);
        }
      }
    }
    return null;
  }

  protected InputStreamReader getResourceInputStreamReader ( String file ) throws IOException {
    InputStream in = getResourceInputStream( file );
    return encoding != null ? new InputStreamReader( in, encoding ) : new InputStreamReader( in );
  }


  private void loadProperties ( String location, boolean ignoreResourceNotFound ) {

    InputStreamReader is = null;

    try {
      logger.debug( "mul4-property-placeholder: Loading resource:" + location );
      is = getResourceInputStreamReader( location );
    } catch ( NullPointerException e ) {
      if ( !ignoreResourceNotFound )
        throw new Mule4PropertyPlaceholderConfigurationException( createStaticMessage( "Couldn't read from file " + location ), this, e );

      logger.warn( "mul4-property-placeholder: Loading resource:" + location + " failed, ignoring because ignore-resource-not-found=" + ignoreResourceNotFound );
    } catch ( IOException e ) {
      logger.error( ExceptionUtils.getStackTrace( e ) );
    }

    if ( is == null )
      return;


    Properties properties = new Properties();
    try {
      properties.load( is );
    } catch ( IOException e ) {
      logger.error( ExceptionUtils.getStackTrace( e ) );
    }


    properties.keySet().stream().map( key -> {
      Object rawValue = properties.get( key );
      return new ConfigurationProperty() {

        @Override
        public Object getSource () {
          return of( this );
        }

        @Override
        public Object getRawValue () {
          return rawValue;
        }

        @Override
        public String getKey () {
          return ( String ) key;
        }
      };
    } ).forEach( configurationAttribute -> {
      configurationAttributes.put( configurationAttribute.getKey(), configurationAttribute );
    } );

  }


  @Override
  public Optional< ConfigurationProperty > getConfigurationProperty ( String configurationAttributeKey ) {
    if ( ignoreUnresolvable )
      return Optional.ofNullable( configurationAttributes.get( configurationAttributeKey ) );
    else {
      ConfigurationProperty property = configurationAttributes.get( configurationAttributeKey );
      if ( property == null ) {
        throw new Mule4PropertyPlaceholderConfigurationException( createStaticMessage( "Unable to find property " + configurationAttributeKey ), this );
      }

      return Optional.of( configurationAttributes.get( configurationAttributeKey ) );

    }
  }

  @Override
  public String getDescription () {
    return "mule4-property-placeholder";
  }
}
