package utills;

public class Init {

    private static final String pagesPackage = "pages";
    private static PageFactory pageFactory;

    public static String getPagesPackage(){
        return pagesPackage;
    }

    public static PageFactory getPageFactory(){
        if (null == pageFactory){
            pageFactory = new PageFactory(getPagesPackage());
        }
        return pageFactory;
    }
}
