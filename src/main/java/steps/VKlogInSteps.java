package steps;

import pages.VKlogInPage;

/**
 * Created by VITALIY on 07.11.2017.
 */
public class VKlogInSteps {

    VKlogInPage page = new VKlogInPage();

    //@Step("Вводим емаил и пароль")
   /* public void fillFields(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> page.fillFieldOfLogInVK(field, value));
    }

    @Step("Подтверждаем вход")
    public void sumbitLogIn(){
        page.buttonPusher();
    }

    @ActionTitle(value = "авторизуется в клиенте 2")
    public void fillAutification(){
                page.fillField(page.getIndex_emailFLD(), "vittestovoi@gmail.com");
                page.fillField(page.getIndex_passFLD(), "aplana1234admin");
                page.buttonPusher();
    }*/
}
