/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jca.sample.listfiles;

import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.jboss.jca.sample.listfiles.*;

/**
 * ConnectorTestCase
 *
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class ConnectorTestCase
{
   private static Logger log = Logger.getLogger("ConnectorTestCase");

   private static String deploymentName = "ConnectorTestCase";

   /**
    * Define the deployment
    *
    * @return The deployment archive
    */
   @Deployment
   public static ResourceAdapterArchive createDeployment()
   {
      ResourceAdapterArchive raa =
         ShrinkWrap.create(ResourceAdapterArchive.class, deploymentName + ".rar");
      JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + ".jar");
      ja.addClasses(ListFilesResourceAdapter.class, ListFilesManagedConnectionFactory.class, ListFilesManagedConnection.class, ListFilesConnectionFactory.class, ListFilesConnectionFactoryImpl.class, ListFilesConnection.class, ListFilesConnectionImpl.class);
      raa.addAsLibrary(ja);

      raa.addAsManifestResource("META-INF/ironjacamar.xml", "ironjacamar.xml");

      return raa;
   }

   /** Resource */
   @Resource(mappedName = "java:/eis/ListFilesConnectionFactory")
   private ListFilesConnectionFactory connectionFactory1;

   /**
    * Test getConnection
    *
    * @exception Throwable Thrown if case of an error
    */
   @Test
   public void testGetConnection1() throws Throwable
   {
      assertNotNull(connectionFactory1);
      ListFilesConnection connection1 = connectionFactory1.getConnection();
      assertNotNull(connection1);
      connection1.close();
   }

}
