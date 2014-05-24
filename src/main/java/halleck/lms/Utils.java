package halleck.lms;

import com.google.common.primitives.Ints;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Strings.nullToEmpty;

public class Utils {
    public static String toSafeString(String url) {
        return url == null ? "" : url;
    }

    public static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
        String query = url.substring(url.indexOf('?') + 1);
        String[] pairs = query.split("&");
        Map<String, String> query_pairs = new LinkedHashMap<>();
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static Integer toInteger(String value){
        return Ints.tryParse(nullToEmpty(value));
    }

    public static <R> Optional<R> tryget(ExceptionalSupplier<R> func){
        try{
            return Optional.of(func.get());
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @FunctionalInterface
    public static interface ExceptionalSupplier<T> {
        T get() throws Exception;
    }

}
