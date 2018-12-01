package utills;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ActionTitles.class)
public @interface ActionTitle {
    String value();
}
