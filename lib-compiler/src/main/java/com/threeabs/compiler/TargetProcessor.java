package com.threeabs.compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.threeabs.annotation.TargetTemplate;

import java.io.IOException;
;
import java.util.LinkedHashSet;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
public class TargetProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 获取到注解的名字
     *
     * @return Set
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(TargetTemplate.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //获取注解集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(TargetTemplate.class);
        //判空
        if (elements == null) return false;
        //遍历
        for (Element element : elements) {
            //获取注解模板
            TargetTemplate template = element.getAnnotation(TargetTemplate.class);
            //获取需要将目标类创建的位置
            String targetPath = template.targetPath();
            //判空
            if (targetPath.isEmpty()) {
                throw new IllegalArgumentException("targetPath can not be empty or null !");
            }
            //获取需要将目标类创建的位置
            String targetName = template.targetName();
            //判空
            if (targetName.isEmpty()) {
                throw new IllegalArgumentException("targetName can not be empty or null !");
            }

            //获取TypeMirror
            TypeMirror typeMirror = null;

            try {
                template.targetTemplate();
            } catch (MirroredTypeException e) {
                typeMirror = e.getTypeMirror();
            }

            //创建目标类
            final TypeSpec targetActivity = TypeSpec.classBuilder(targetName)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(TypeName.get(typeMirror))
                    .build();
            //创建
            final JavaFile javaFile = JavaFile.builder(targetPath, targetActivity)
                    .build();
            //开始写入
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
