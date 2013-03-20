package webserver;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;

public class AssetRouts extends Route {

    private static final ImmutableMap<String, String> mimeTypes;
    private static final LoadingCache<String,String> assets;

    static {
        mimeTypes = ImmutableMap.of("css", "text/css;charset=utf-8",
                                     "js", "application/javascript;charset=utf-8");

        assets = CacheBuilder.newBuilder().build(new ResourceLoader());
    }

    protected AssetRouts(String path) {
        super(path);
    }

    @Override
    public String handle(Request request, Response response)  {
        String path = request.pathInfo();
        response.raw().setContentType(mimeTypes.get(getExtension(path)));
        return assets.getUnchecked(path);
    }

    private String getExtension(String og){
        return og.substring(og.lastIndexOf('.'));
    }

    private static class ResourceLoader extends CacheLoader<String,String> {
        @Override
        public String load(String resourceName) throws Exception {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream file = classLoader.getResourceAsStream(resourceName.substring(1));

            if(file == null){
                return null;
            }

            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(file, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return writer.toString();
        }
    }
}
