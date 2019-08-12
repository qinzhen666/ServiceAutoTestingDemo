package com.greenvalley.brainPlatform;

import org.junit.jupiter.api.Test;

class WeworkConfigTest {

    @Test
    void load() {
        WeworkConfig.load("");
    }

    @Test
    void getInstance(){
        WeworkConfig.getInstance();
    }
}