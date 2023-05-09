package Main.studySpring.utils.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AnnotationUtils
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/27 21:43
 * @Created by Enzuo
 */

public class AnnotationUtils {
    private static boolean isAnnotation(Annotation annotation, List<Class<?>> list, Class<? extends Annotation> annotationClazz){
        if (annotation.annotationType().getAnnotation(annotationClazz)!=null)return true;
        if(list.contains(annotation.annotationType()))return false;
        list.add(annotation.annotationType());
        Class<?> annotationType = annotation.annotationType();
        Annotation[] annotations = annotationType.getDeclaredAnnotations();
        boolean flag=false;
        for (Annotation an : annotations) {
            flag=flag||isAnnotation(an,list,annotationClazz);
        }
        return flag;
    }
    public static boolean isAnnotation(Class<?> clazz,Class<? extends Annotation> annotationClazz,List<Class<? extends Annotation>> temp){
        if (clazz.getAnnotation(annotationClazz)!=null) {
            Annotation annotation = clazz.getAnnotation(annotationClazz);
            if(temp!=null)
            temp.add(annotation.annotationType());
            return true;
        }
        boolean flag= false;
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            flag=flag|| AnnotationUtils.isAnnotation(annotation, new ArrayList<>(), annotationClazz);
            if(temp!=null&&flag){
                temp.add(annotation.annotationType());
            }
        }
        return flag;
    }
}
