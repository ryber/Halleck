package webserver;

import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.io.Resources.getResource;

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
        return Files.getFileExtension(og);
    }

    private static class ResourceLoader extends CacheLoader<String,String> {
        @Override
        public String load(String resourceName) throws Exception {
            return Resources.toString(getResource(resourceName.substring(1)), Charsets.UTF_8);

        }
    }
}
