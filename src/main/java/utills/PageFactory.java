package utills;

import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;


public class PageFactory {
    private static final Logger LOG = LoggerFactory.getLogger(PageFactory.class);
    public String currentPageTitle;
    public Page currentPage;
    private final String pagesPackage;

    public PageFactory(String pagesPackage) {
        this.pagesPackage = pagesPackage;
    }

    public Page getPage(String title) throws Throwable {
        if (null == this.currentPage || !this.currentPageTitle.equals(title)) {
            try {
                if (null != this.currentPage) {
                    this.currentPage = this.getPage(this.currentPage.getClass().getPackage().getName(), title);
                }

                if (null == this.currentPage) {
                    this.currentPage = this.getPage(this.pagesPackage, title);
                }

                if (null == this.currentPage) {
                    throw new Exception("Page Object with title " + title + " is not registered");
                }
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException var3) {
                throw new Exception("Page " + title + " initialization error.", var3);
            }
        }

        return this.currentPage;
    }

    public Page getPage(Class<? extends Page> page) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.bootstrapPage(page);
    }

    public Page getPage(String packageName, String title) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        return this.bootstrapPage(this.getPageClass(packageName, title));
    }

    public Page getCurrentPage() throws InvocationTargetException {
        if (null == this.currentPage) {
            throw new InvocationTargetException(new Exception("Current page not initialized!"));
        } else {
            return this.currentPage;
        }
    }

    private Class<?> getPageClass(String packageName, String title) throws IllegalAccessException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        HashSet allClasses = new HashSet();

        try {
            ClassPath.from(loader).getAllClasses().stream().filter((info) -> {
                return info.getName().startsWith(packageName + ".");
            }).forEach((info) -> {
                allClasses.add(info.load());
            });
        } catch (IOException var10) {
            LOG.warn("Failed to shape class info set", var10);
        }

        Iterator var5 = allClasses.iterator();

        Class page;
        String pageTitle;
        do {
            if (!var5.hasNext()) {
                return null;
            }

            page = (Class)var5.next();
            pageTitle = null;
            if (null != page.getAnnotation(PageEntry.class)) {
                pageTitle = ((PageEntry)page.getAnnotation(PageEntry.class)).title();
            } else {
                try {
                    pageTitle = (String)FieldUtils.readStaticField(page, "title", true);
                } catch (IllegalArgumentException var9) {
                    LOG.debug("Failed to read {} because it is not page object", pageTitle, var9);
                }
            }
        } while(pageTitle == null || !pageTitle.equals(title));

        return page;
    }

    private Page bootstrapPage(Class<?> page) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        if (page != null) {
            //unchecked
            this.currentPage = (Page) page.newInstance();
            this.currentPageTitle = this.currentPage.getTitle();
            return this.currentPage;
        } else {
            return null;
        }
    }
}
