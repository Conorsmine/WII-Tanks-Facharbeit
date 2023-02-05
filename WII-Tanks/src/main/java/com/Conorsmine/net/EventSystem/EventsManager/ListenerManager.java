package com.Conorsmine.net.EventSystem.EventsManager;

import io.github.classgraph.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListenerManager {

    public static ListenerManager INSTANCE;
    private final List<RegisteredListener> listenerList;

    public ListenerManager() {
        // Only one instance should ever exist
        if (INSTANCE != null) this.listenerList = new LinkedList<>();
        else {
            INSTANCE = this;
            this.listenerList = registerListeners("com.Conorsmine.net");
        }
    }

    private List<RegisteredListener> registerListeners(String packageName) {
        List<RegisteredListener> out = new LinkedList<>();
        List<Class<? extends Listener>> implementing = getImplementingClasses(packageName);


        // Note them as registered Listeners
        for (Class<? extends Listener> clazz : implementing) {
            out.add(new RegisteredListener(
                    clazz,
                    getAnnotedMethods(clazz)
            ));
        }
        return out;
    }

    private List<Class<? extends Listener>> getImplementingClasses(String packageName) {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .acceptPackages(packageName)
                             .enableClassInfo()
                             .scan()) {

            List<Class<? extends Listener>> implementing = new LinkedList<>();
            for (Class<?> clazz : scanResult.getClassesImplementing(Listener.class).loadClasses())
                implementing.add(((Class<? extends Listener>) clazz));
            return implementing;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<EventMethod> getAnnotedMethods(Class<? extends Listener> clazz) {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .acceptPackages(clazz.getPackage().getName())
                             .enableMethodInfo()
                             .enableAnnotationInfo()
                             .scan()) {
            LinkedList<EventMethod> methodList = new LinkedList<>();
            ClassInfo info = scanResult.getClassInfo(clazz.getName());

            // Get all methods with the "EventExecutor" annotation
            // which also have an event as their parameter
            for (MethodInfo methodInfo : info.getMethodInfo()) {
                if (!methodInfo.hasAnnotation(EventExecutor.class)) continue;
                EventMethod method = hasEvent(methodInfo);
                if (method == null) continue;
                AnnotationParameterValueList annotationInfo = methodInfo.getAnnotationInfo(EventExecutor.class).getParameterValues();

                // Add the special tags given via the Annotation
                methodList.add(new EventMethod(method,
                        EventPriority.valueOf(annotationInfo.getValue("priority").toString().replaceAll(".+\\.", ""))  // Don't like this
//                        (boolean) annotationInfo.getValue("isCancelable")
//                        methodInfo.getAnnotationInfo(EventExecutor.class).getParameterValues(false)
//                                .getValue("isCancelable") == null    // Checks if the value has been manually added
                ));
            }

            return methodList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * @param method Method to be checked for having an event parameter
     * @return Null if the method parameter is not an event
     */
    private EventMethod hasEvent(MethodInfo method) {
        final MethodParameterInfo[] args = method.getParameterInfo();
        if (args.length != 1)
            return null;                                      // Method may only have the event as a parameter
        return getClassEvent(args[0].getTypeDescriptor().toString(), method);   // Parameter is an event
    }

    /**
     * @param className Name of the class to check
     * @return Null if the class does not implement "Event"
     */
    private EventMethod getClassEvent(String className, MethodInfo methodInfo) {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .acceptClasses(className)
                             .enableClassInfo()
                             .scan()) {

            // Class doesn't extend "Event"
            if (scanResult.getClassInfo(className) == null) return null;
            if (!scanResult.getClassInfo(className).extendsSuperclass(Event.class)) return null;

            Class<? extends Event> eventClass = (Class<? extends Event>) scanResult.getClassInfo(className).loadClass();
            return new EventMethod(
                    methodInfo.getClassInfo().loadClass().getDeclaredMethod(methodInfo.loadClassAndGetMethod().getName(), eventClass),
                    eventClass.getName()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredListener> getListenerList() {
        return listenerList;
    }
}
