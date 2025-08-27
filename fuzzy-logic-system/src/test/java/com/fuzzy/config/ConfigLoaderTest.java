package com.fuzzy.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void testLoadConfig() {
        Configuration config = ConfigLoader.loadConfig("test-config.properties");
        assertNotNull(config);
        assertEquals("test_value", config.property1());
        assertEquals(456, config.property2());
    }

    @Test
    void testLoadConfig_nonExistentFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            ConfigLoader.loadConfig("non-existent-file.properties");
        });
    }
}
