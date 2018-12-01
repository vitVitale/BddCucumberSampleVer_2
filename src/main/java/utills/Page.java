package utills;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public abstract class Page {
    private static final Logger LOG = LoggerFactory.getLogger(Page.class);
    private static final String BLOCK_OBJECT_CACHE = "findElementInBlock-object-cache";

    public Page() {
    }

    private List<Method> getDeclaredMethods() {
        List<Method> methods = new ArrayList();
        methods.addAll(Arrays.asList(this.getClass().getDeclaredMethods()));

        for(Class supp = this.getClass().getSuperclass(); !supp.getName().equals("java.lang.Object"); supp = supp.getSuperclass()) {
            methods.addAll(Arrays.asList(supp.getDeclaredMethods()));
        }

        return methods;
    }

    private String getMethodActionAnnotation(Method method) {
        StringBuilder sb = new StringBuilder("");
        Arrays.stream(method.getAnnotations()).filter((a) -> {
            return a instanceof ActionTitle;
        }).forEach((e) -> {
            sb.append("\"");
            sb.append(((ActionTitle)e).value());
            sb.append("\" ");
        });
        return !"".equals(sb.toString()) ? "\" with actionTitle annotation " + sb.toString() : "";
    }

    private String allMethodToString(List<Method> methods) {
        StringBuilder sb = new StringBuilder();
        methods.forEach((method) -> {
            sb.append("\t\"").append(this.getTitle()).append("\"->\"").append(method.getName()).append(this.getMethodActionAnnotation(method)).append("\" with ").append(method.getGenericParameterTypes().length).append(" input parameters").append("\n");
        });
        return sb.toString();
    }

    private Boolean isActionTitleContainsInAnnotation(Method method, String title) {
        ActionTitle actionTitle = (ActionTitle)method.getAnnotation(ActionTitle.class);
        ActionTitles actionTitles = (ActionTitles)method.getAnnotation(ActionTitles.class);
        List<ActionTitle> actionList = new ArrayList();
        if (actionTitles != null) {
            actionList.addAll(Arrays.asList(actionTitles.value()));
        }

        if (actionTitle != null) {
            actionList.add(actionTitle);
        }

        return actionList.stream().filter((action) -> {
            return action.value().equals(title);
        }).findFirst().isPresent();
    }

    public String getTitle() {
        return ((PageEntry)this.getClass().getAnnotation(PageEntry.class)).title();
    }

    public void setValue(String field, String value) throws Exception {
        this.getClass().getDeclaredField(field).set(this, value);
    }

    public void takeAction(String action, Object... param) throws Throwable {
        String actionName = action.replaceAll(" ", "_");
        List methods = this.getDeclaredMethods();

        try {
            Iterator var5 = methods.iterator();

            while(true) {
                if (!var5.hasNext()) {
                    LOG.debug("There is no action {}", action);
                    StringBuilder sb = (new StringBuilder()).append("There is no \"").append(action).append("\" action ").append("in ").append(this.getTitle()).append(" page object").append("\n").append("Possible actions are:").append("\n").append(this.allMethodToString(methods));
                    throw new NoSuchMethodException(sb.toString());
                }

                Method method = (Method)var5.next();
                if (method.getName() == null) {
                    if (actionName == null) {
                        break;
                    }
                } else if (method.getName().equals(actionName)) {
                    break;
                }

                if (this.isActionTitleContainsInAnnotation(method, action)) {
                    method.setAccessible(true);
                    MethodUtils.invokeMethod(this, method.getName(), param);
                    return;
                }
            }

            MethodUtils.invokeMethod(this, actionName, param);
        } catch (InvocationTargetException var7) {
            LOG.debug("Failed to invocate action {}", action, var7);
            throw var7.getCause();
        }
    }

    public void takeActionInBlock(String block, String actionTitle) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.takeActionInBlock(block, actionTitle);
    }


    public <T> T executeMethodByTitle(String title, Class<T> type, Object... param) throws Exception {
        List<Method> methods = this.getDeclaredMethods();
        Iterator var5 = methods.iterator();

        Method method;
        do {
            if (!var5.hasNext()) {
                throw new NoSuchElementException("There is no " + title + " method on " + this.getTitle() + " page object.");
            }

            method = (Method)var5.next();
        } while(!this.isActionTitleContainsInAnnotation(method, title));

        method.setAccessible(true);
        return type.cast(MethodUtils.invokeMethod(this, method.getName(), param));
    }

    public void fireValidationRule(String title, Object... params) throws IllegalAccessException, IllegalArgumentException, Throwable{
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods){
            if (null != method.getAnnotation(ValidationRule.class) && method.getAnnotation(ValidationRule.class).title().equals(title)){
                try{
                    method.invoke(this, params);
                }catch (InvocationTargetException e){
                    LOG.debug("Failed to invoke method {}", method, e);
                    throw e.getCause();
                }
                return;
            }
        }
        throw new Exception("There is no \"" + title + "\" validation rule in \"" + getTitle() + "\" page!");
    }
}