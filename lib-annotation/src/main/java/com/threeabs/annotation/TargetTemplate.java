package com.threeabs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface TargetTemplate {

    /**
     * 目标类,需要动态创建的目标类名称
     */
    String targetName();

    /**
     * 目标路径，需要将targetClass创建到的位置
     */
    String targetPath();


    /**
     * 目标类继承模板，编写你需要的业务逻辑
     */

    Class targetTemplate();
}
