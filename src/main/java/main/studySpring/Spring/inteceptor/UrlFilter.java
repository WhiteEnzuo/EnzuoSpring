package main.studySpring.Spring.inteceptor;

import main.studySpring.Tomcat.agreement.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UrlFilter
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/9 21:29
 * @Created by Enzuo
 */

public class UrlFilter {
    private final List<String> interceptorNormalList;
    private final List<String> interceptorOneStarList;
    private final List<String> interceptorTwoStarList;
    private final List<String> accessListNormalList;
    private final List<String> accessListOneStarList;
    private final List<String> accessListTwoStarList;

    public UrlFilter() {
        interceptorNormalList = new ArrayList<>();
        interceptorOneStarList = new ArrayList<>();
        interceptorTwoStarList = new ArrayList<>();
        accessListNormalList = new ArrayList<>();
        accessListOneStarList = new ArrayList<>();
        accessListTwoStarList = new ArrayList<>();
    }

    public UrlFilter addAccessUrl(String s) {
        return getUrlFilter(s, accessListTwoStarList, accessListOneStarList, accessListNormalList);
    }

    public UrlFilter addInterceptorUrl(String s) {
        return getUrlFilter(s, interceptorTwoStarList, interceptorOneStarList, interceptorNormalList);
    }

    private UrlFilter getUrlFilter(String s, List<String> interceptorTwoStarList, List<String> interceptorOneStarList, List<String> interceptorNormalList) {
        String[] split = s.split("/");
        if (split.length == 0) return this;
        if (s.contains("**")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                sb.append("/").append(split[i]);
            }
            if (split.length == 1) {
                sb.append("/");
            }
            interceptorTwoStarList.add(sb.toString());
            return this;
        }
        if (s.contains("*")) {
            StringBuilder sb = new StringBuilder();
            String lastString = split[split.length - 1];
            String[] split1 = lastString.split("\\.");
            if (split1.length < 2) return this;

            for (int i = 0; i < split.length - 1; i++) {
                sb.append("/").append(split[i]);
            }
            if (split.length == 1) {
                sb.append("/");
            }
            String suffixString = split1[1];
            sb.append("/");
            sb.append(suffixString);
            interceptorOneStarList.add(sb.toString());
            return this;
        }
        interceptorNormalList.add(s);
        return this;
    }

    public boolean isUrlAccess(Request request) {
        String uri = request.getUri();
        return isNormal(uri) || isOneStar(uri) || isTwoStar(uri);
    }

    private boolean isNormal(String uri) {
        return accessListNormalList.contains(uri) && !(interceptorNormalList.contains(uri));
    }

    private boolean isOneStar(String uri) {
        String[] split = uri.split("/");
        StringBuilder sb = new StringBuilder();
        String lastString = split[split.length - 1];
        String[] split1 = lastString.split("\\.");
        if (split1.length < 2) return false;
        for (int i = 0; i < split.length - 1; i++) {
            sb.append("/").append(split[i]);
        }
        if (split.length == 1) {
            sb.append("/");
        }
        String suffixString = split1[1];
        sb.append("/");
        sb.append(suffixString);
        return accessListOneStarList.contains(sb.toString()) && (!interceptorOneStarList.contains(sb.toString()));
    }

    private boolean isTwoStar(String uri) {
        String[] split = uri.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            sb.append("/").append(split[i]);
        }
        if (split.length == 1) {
            sb.append("/");
        }
        return accessListTwoStarList.contains(sb.toString()) && !(interceptorTwoStarList.contains(sb.toString()));
    }
}
