package kuku.mainApi.common.config.async;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

public class ContextAwareCallable<T> implements Callable<T> {

    private final CustomRequestScopeAttr requestAttributes;
    private Callable<T> task;

    public ContextAwareCallable(RequestAttributes requestAttributes, Callable<T> task) {
        this.task = task;
        this.requestAttributes = cloneRequestAttributes(requestAttributes);
    }

    @Override
    public T call() throws Exception {
        try {
            RequestContextHolder.setRequestAttributes(requestAttributes);

            return task.call();

        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }

    private CustomRequestScopeAttr cloneRequestAttributes(RequestAttributes requestAttributes) {
        CustomRequestScopeAttr clonedRequestAttribute = null;

        try {
            clonedRequestAttribute = new CustomRequestScopeAttr();

            for (String name : requestAttributes.getAttributeNames(RequestAttributes.SCOPE_REQUEST)) {
                clonedRequestAttribute.setAttribute(
                        name,
                        requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST),
                        RequestAttributes.SCOPE_REQUEST
                );
            }
            return clonedRequestAttribute;
        } catch (Exception e) {
            return new CustomRequestScopeAttr();
        }
    }
}
