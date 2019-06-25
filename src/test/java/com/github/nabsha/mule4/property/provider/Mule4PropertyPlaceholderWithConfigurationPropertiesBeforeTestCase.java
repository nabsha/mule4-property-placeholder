package com.github.nabsha.mule4.property.provider;

import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Mule4PropertyPlaceholderWithConfigurationPropertiesBeforeTestCase extends MuleArtifactFunctionalTestCase {


  /**
   * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test
   * resources.
   */
  @Override
  protected String getConfigFile() {
    return "withConfigurationPropertiesBefore/test-mule-config.xml";
  }

  @Inject
  private TestObject testObject;

  @Test
  public void loadingPropertyTest() {
    assertThat(testObject.getValueFromProperty(), is("config-test"));
    // when mixing configuration-properties with this component, properties are not overridden so first component wins
    assertThat(testObject.getOverrideProperty(), is("initial"));

  }

}
