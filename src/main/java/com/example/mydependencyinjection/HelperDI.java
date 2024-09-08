package com.example.mydependencyinjection;

import com.example.mydependencyinjection.util.annotations.MyAutowired;
import com.example.mydependencyinjection.util.annotations.MyComponent;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Log4j2
public class HelperDI {

    private String packageName() {
        // Получаем имя пакета текущего класса
        return MyDependencyInjectionApplication.class.getPackage().getName();
    }

    private Set<Class<?>> findAllClasses() {
        String packageName = packageName();

        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        return allClasses;
    }

    int id = 0;
    HashMap<Class<?>, Integer> beanMap = new HashMap<>();
    private Set<Class<?>> findClassesWithAnnotationMyComponents() {
        Set<Class<?>> classes = findAllClasses();

        Set<Class<?>> classesMyComponent = new HashSet<>();
        for (Class<?> clazz : classes) {
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof MyComponent) {
                    classesMyComponent.add(clazz);

                    for (Class<?> interfaze : clazz.getInterfaces()) {
                        beanMap.put(interfaze, id++);
                    }
                    beanMap.put(clazz, id++);
                }
            }
        }

        return classesMyComponent;
    }

    HashMap<Integer, Object> createAlready = new HashMap<>();
    HashSet<Integer> createInProcess = new HashSet<>();

    public void createInstanceClasses() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        createInstanceClasses(findClassesWithAnnotationMyComponents());
    }

    private Object createInstanceClasses(Set<Class<?>> classesMyComponent) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object instance = null;
        for (Class<?> clazzComponent : classesMyComponent) {
            if (createAlready.containsKey(beanMap.get(clazzComponent)) || createInProcess.contains(beanMap.get(clazzComponent)))
                continue;
            Constructor[] constructors = clazzComponent.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.isAnnotationPresent(MyAutowired.class)) {

                    createInProcess.add(beanMap.get(clazzComponent));
                    for (Class<?> interfaze : clazzComponent.getInterfaces()) {
                        createInProcess.add(beanMap.get(interfaze));
                    }

                    Class<?>[] params = constructor.getParameterTypes();
                    List<Object> instanceParams = new ArrayList<>();
                    for (Class<?> clazzComponentChild : params) {
                        while (!createAlready.containsKey(beanMap.get(clazzComponentChild))) {
                            if (createInProcess.contains(beanMap.get(clazzComponent)) && createInProcess.contains(beanMap.get(clazzComponentChild))){
                                throw new RuntimeException("Infinite cycle: " + clazzComponentChild.getName() + " <-> " + clazzComponent.getName());
                            }

                            createInstanceClasses(classesMyComponent);
                        }
                        instanceParams.add(createAlready.get(beanMap.get(clazzComponentChild)));
                    }
                    instance = constructor.newInstance(instanceParams.toArray());
                    Class<?>[] interfazes = clazzComponent.getInterfaces();
                    for (Class<?> interfaze : interfazes) {
                        createAlready.put(beanMap.get(interfaze), instance);
                    }
                    createAlready.put(beanMap.get(clazzComponent), instance);

                    createInProcess.remove(beanMap.get(clazzComponent));
                    for (Class<?> interfaze : clazzComponent.getInterfaces()) {
                        createInProcess.remove(beanMap.get(interfaze));
                    }
                }
            }
        }
        return instance;
    }

}
