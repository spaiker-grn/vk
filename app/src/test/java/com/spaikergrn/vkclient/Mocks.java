package com.spaikergrn.vkclient;

import java.io.InputStream;

public final class Mocks {

    public static InputStream mockedStream(final Object clazz, final String pFilename) {
        return clazz.getClass().getClassLoader().getResourceAsStream("feeds/" + pFilename);
    }

}
