package com.imaginea.rm.mapreduce.dn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * All the data nucleus configurations should be provided in the
 * datanucleus.properties file. This class assumes that this file is in the
 * application classpath. All the application specific datanucleus properties
 * should be updated in the datanucleus.properties and should be submitted along
 * with the application.
 */
public class DNConfiguration {

  private static final String DN_PROPERTIES = "datanucleus.properties";
  private Properties dnProperties;

  public DNConfiguration() throws IOException {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    InputStream jdoStream = cl.getResourceAsStream(DN_PROPERTIES);
    dnProperties = new Properties();
    dnProperties.load(jdoStream);
  }

  public PersistenceManagerFactory getPersistentManagerFactory() {
    return JDOHelper.getPersistenceManagerFactory(dnProperties);
  }

}
