package org.pac4j.core.util;

import lombok.val;
import org.junit.Assert;
import org.pac4j.core.exception.TechnicalException;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is an helper for tests: get a basic web client, parameters from an url, a formatted date, etc.
 *
 * @author Jerome Leleu
 * @since 1.0.0
 */
public final class TestsHelper {

    public static void initShouldFail(final InitializableObject obj, final String message) {
        expectException(obj::init, TechnicalException.class, message);
    }

    public static Exception expectException(final Executable executable) {
        try {
            executable.execute();
        } catch (final Exception e) {
            return e;
        }
        return null;
    }

    public static void expectException(final Executable executable, final Class<? extends Exception> clazz, final String message) {
        val e = expectException(executable);
        Assert.assertTrue(clazz.isAssignableFrom(e.getClass()));
        Assert.assertEquals(message, e.getMessage());
    }

    public static Map<String, String> splitQuery(URL url) {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        var query = url.getQuery();
        var pairs = query.split("&", -1);
        for (var pair : pairs) {
            var idx = pair.indexOf("=");
            query_pairs.put(CommonHelper.urlEncode(pair.substring(0, idx)), CommonHelper.urlEncode(pair.substring(idx + 1)));
        }
        return query_pairs;
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
