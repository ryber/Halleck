package halleck.webserver;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Resources;

public class JsLocations {
    private static final LoadingCache<String, String> cache;

    static {
        cache = CacheBuilder.newBuilder().build(new ResourceChecker());
    }

    private static class ResourceChecker extends CacheLoader<String,String> {
        @Override
        public String load(String s) throws Exception {
            String transformed = "assets/js/" + s.replace(".mustache", ".js");
            try{
                 Resources.getResource(transformed);
                return transformed.replace("assets","");
            }catch (Exception e){
                return "";
            }
        }

    }

    public String getJavaScriptFileForStache(String mustacheTemplate){
        return cache.getUnchecked(mustacheTemplate);
    }
}
