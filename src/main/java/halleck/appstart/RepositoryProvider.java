package halleck.appstart;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Provider;
import halleck.lms.Settings;
import halleck.lms.CourseRepository;
import halleck.lms.InMemoryCourseRepository;
import halleck.lms.MongoCourseRepository;

public class RepositoryProvider implements Provider<CourseRepository> {

    private Settings settings;
    private MongoCourseRepository mongoRepo;
    private InMemoryCourseRepository memoryRepo;

    @Inject
    public RepositoryProvider(Settings settings,
                              MongoCourseRepository mongoRepo,
                              InMemoryCourseRepository memoryRepo){
        this.settings = settings;
        this.mongoRepo = mongoRepo;
        this.memoryRepo = memoryRepo;
    }

    @Override
    public CourseRepository get() {
        if(Objects.equal(settings.getPersistenceType(), "mongo")){
            return mongoRepo;
        }
        return memoryRepo;
    }
}
