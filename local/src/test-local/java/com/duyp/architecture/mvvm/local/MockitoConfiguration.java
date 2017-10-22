package org.mockito.configuration;

/**
 * Created by duypham on 10/17/17.
 * https://github.com/powermock/powermock/issues/593
 */

public class MockitoConfiguration extends DefaultMockitoConfiguration{
    @Override
    public boolean enableClassCache() {
        return false;
    }
}