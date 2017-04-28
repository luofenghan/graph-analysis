package com.analysis.graph.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public class IOUtils {
    public static void cleanup(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
