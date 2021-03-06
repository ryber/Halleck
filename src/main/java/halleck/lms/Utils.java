package halleck.lms;

import com.google.common.primitives.Ints;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Strings.nullToEmpty;

public class Utils {

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

    public static <R> R propogate(ExceptionalSupplier<R> func){
        try{
            return func.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void propogate(ExceptionaRunnable func){
        try{
             func.run();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public static interface ExceptionalSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public static interface ExceptionaRunnable<R> {
        void run() throws Exception;
    }
}
