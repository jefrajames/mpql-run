/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomee.microprofile.graphql;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * Reads the pom file and enhance the deployed archive with out implementation.
 * This is a temp solution. In the end, the impl will be located in the app server itself
 */
public class GraphQLArchiveProcessor implements ApplicationArchiveProcessor {

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass) {

        if (applicationArchive instanceof WebArchive) {

            final WebArchive testDeployment = (WebArchive) applicationArchive;

            final String[] deps = {
                    "org.jefrajames:mp-graphql" // The implementation
            };
            final File[] dependencies = Maven.resolver().loadPomFromFile(new File("pom.xml")).resolve(deps).withTransitivity()
                    .asFile();

            // Make sure it's unique
            final Set<File> dependenciesSet = new LinkedHashSet<>(Arrays.asList(dependencies));
            testDeployment.addAsLibraries(dependenciesSet.toArray(new File[dependenciesSet.size()]));

        }
    }

}
