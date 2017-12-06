package com.example.myapplication.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtils {

    private static final int BUFFER_SIZE_STREAM_COPY = 32 * 1024;
    public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024;
    private static final int BUFFER_SIZE = 4096;

    public static boolean copyStream(final InputStream pIs, final OutputStream pOs)
            throws IOException {

        final byte[] bytes = new byte[BUFFER_SIZE_STREAM_COPY];
        int count;
        while ((count = pIs.read(bytes, 0, BUFFER_SIZE_STREAM_COPY)) != -1) {
            pOs.write(bytes, 0, count);
        }
        pOs.flush();
        return true;
    }

    public static InputStream getIsFromFile(final File pFile) throws IOException {
        return new BufferedInputStream(new FileInputStream(pFile), BUFFER_SIZE);
    }

}

