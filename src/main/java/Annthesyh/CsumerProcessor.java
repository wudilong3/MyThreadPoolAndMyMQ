package Annthesyh;

import Annthesyh.TestDire.Csumer;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CsumerProcessor extends AbstractProcessor {
    private static CsumerProcessor csumerProcessor = null;
    private List<String> nameList = new ArrayList<>();



    private CsumerProcessor(){

    }

    public  List<String> getCsumerProcessorNameList() {
        return nameList;
    }

    public static CsumerProcessor getCsumerProcessor() {
        return csumerProcessor;
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        csumerProcessor = this;
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()){
            for (Element element : roundEnv.getElementsAnnotatedWith(Csumer.class)) {
                String name = element.getSimpleName().toString();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "element name: "+ name);
            }
        }
        return false;
    }

    public void parseMethod(final Class<?> clazz) throws Exception {
        final Object object = clazz.getConstructor(new Class[] {}).newInstance();
        final Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            final Csumer my = method.getAnnotation(Csumer.class);
            if (null != my) {
                method.invoke(object,my);
            }
        }
    }
}
