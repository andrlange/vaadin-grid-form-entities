package cool.cfapps.vaadinentity.entity.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface GridColumn {
    String header() default "";
    int order() default 999;
    boolean sortable() default true;
    String dateTimeFormat() default "";
    boolean showAsComponent() default false;
}