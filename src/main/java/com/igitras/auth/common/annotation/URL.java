package com.igitras.auth.common.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * @author mason
 */
@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface URL {
    String message() default "{com.igitras.common.validation.constraints.URL.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * @return the protocol (scheme) the annotated string must match, eg ftp or http.
     *         Per default any protocol is allowed
     */
    String protocol() default "";

    /**
     * @return the host the annotated string must match, eg localhost. Per default any host is allowed
     */
    String host() default "";

    /**
     * @return the port the annotated string must match, eg 80. Per default any port is allowed
     */
    int port() default -1;

    /**
     * @return an additional regular expression the annotated URL must match. The default is any string ('.*')
     */
    @OverridesAttribute(constraint = Pattern.class, name = "regexp") String regexp() default ".*";

    /**
     * @return used in combination with {@link #regexp()} in order to specify a regular expression option
     */
    @OverridesAttribute(constraint = Pattern.class, name = "flags") Pattern.Flag[] flags() default { };

    /**
     * Defines several {@code @URL} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        URL[] value();
    }
}