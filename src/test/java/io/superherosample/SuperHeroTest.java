/*
 * Copyright 2019 jefrajames.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.superherosample;

import java.io.File;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.eclipse.microprofile.graphql.tck.dynamic.GraphQLDynamicClientTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 *
 * @author jefrajames
 */
@Log
public class SuperHeroTest extends GraphQLDynamicClientTest {
    
    @Deployment(name = "superhero")
    public static WebArchive createDeployment() {

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "superhero.war");

        // Non-Java EE libraries
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        archive.addAsLibraries(libs);

        // Application packages excluding test classes
        archive.addPackages(true, "org.jefrajames.mpql.tck.run", "io.superherosample");

        // Copied from src/test/resources
        archive.addAsWebInfResource("beans.xml");

        // Copied from src/main/resources
        archive.addAsResource("META-INF/services/javax.enterprise.inject.spi.Extension");
        archive.addAsResource("superheroes.json");

        log.log(Level.INFO, "deploying webarchive: {0}", archive.toString(true));

        return archive;
    }
    
}
