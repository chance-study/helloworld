package org.chance.wrapper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnsafeWrapperTest {

    @Test
    public void getUnsafe() {

        assertThat(UnsafeWrapper.getUnsafe()).isNotNull();

    }
}