package org.acme.config;

// COMMENTED OUT: This configuration is only needed when using BAMOE/Kogito dependencies
// Since this is a plain Spring Boot API without BAMOE, this configuration is not needed

/*
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;

/**
 * Configuration to provide missing Kogito beans for rules-only applications.
 * This satisfies dependencies required by jBPM Spring Boot starter without
 * requiring actual BPMN process definitions.
 */
/*
@Configuration
public class KogitoConfiguration {

    /**
     * Provides an empty Processes bean when no actual processes are defined.
     * This prevents startup failures in rules-only applications that include
     * jbpm-spring-boot-starter but don't use BPMN processes.
     */
/*
    @Bean
    @ConditionalOnMissingBean(name = "processes")
    public org.kie.kogito.process.Processes processes() {
        return new org.kie.kogito.process.Processes() {
            @Override
            public org.kie.kogito.process.Process processById(String processId) {
                return null;
            }

            @Override
            public Collection<String> processIds() {
                return Collections.emptyList();
            }
        };
    }
}
*/

// Made with Bob
