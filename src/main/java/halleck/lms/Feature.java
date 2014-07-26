package halleck.lms;


import com.google.common.base.Enums;
import com.google.common.base.Optional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public enum Feature {

    VIDEOS,
    EMBEDDED_CONTENT,
    LEARING_DOJOS,
    GEO_CITIES;

    public boolean isActive() {
        return FeatureContext.check(this);
    }
}
