package steps;

import cucumber.api.DataTable;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import utills.Init;

/**
 * Created by VITALIY on 27.10.2017.
 */

/*Задача следующая:
        1.  авторизоваться на vk.com
        2.  Создать группу (название можно любое придумать)
        3. в группе создать  альбом с фотографиями
        4. Загрузить несколько фотографий в созданный альбом
        5. Проверить, что фотографии успешно загрузились
        6. Перейти в группу https://vk.com/qa_alliance
        7. Получить всех пользователей группы и вывести данные в csv файл

        И большая просьба поменьше копи паста стандартного кода, даже того,
        который включает условия Before и After, ошибки у всех кандидатов одни и теже,
        особенно с использованием super. Дайте кандидату самому подумать и решить задачу.
        7 шаг не нужно решать в лоб.*/

public class ScenareioSteps {

    public ScenareioSteps() {
        super();
    }

    VKlogInSteps vKlogInSteps = new VKlogInSteps();
    VKpersonalSteps vKpersonalSteps = new VKpersonalSteps();
    VKgroupSteps vKgroupSteps = new VKgroupSteps();
    QAallianceSteps qAallianceSteps = new QAallianceSteps();


    @Когда("Авторизуемся на сайте ВК:$")
    public void authorization(DataTable table){
     /*   vKlogInSteps.fillFields(table);
        vKlogInSteps.sumbitLogIn();*/
    }

    @Когда("^на главное странице выполнено нажатие на раздел групп")
    public void groupsMeth(){
        vKpersonalSteps.groupBtn();
    }

    @Когда("Создание группы с параметрами:$")
    public void groupConfig(DataTable table){
        //vKpersonalSteps.groupCreate(table);
    }

    @Когда("^Альбомная составляющая")
    public void albumConfig(){
        vKgroupSteps.createAlbum();
    }

    @Когда("Добавляем описание альбома:$")
    public void albumDescrip(DataTable table){
        vKgroupSteps.albumCreate(table);
    }

    @Когда("^Отправляем фото:$")
    public void fillAlbumPhoto(DataTable table){
        vKgroupSteps.photoGet(table);
    }

    @Тогда("Проверяем наличие 3-х фото в альбоме")
    public void existanceOfPhotos(){
        vKgroupSteps.checkAlbum();
    }

    @Когда("Раздел подписчиков и получение всех")
    public void subsPart() {
        qAallianceSteps.subscribersOfGroup();
        qAallianceSteps.massOfSubs();
    }

    @Когда("Запись в файл:$")
    public void noteToFile(DataTable table) throws Throwable{
        qAallianceSteps.toFillFile(table);
    }

    //===============================================
    //===============================================

    @Когда("^(пользователь|он) \\((.*)\\)$")
    public void userAction_0(final String who, final String action) throws Throwable{
        Init.getPageFactory().getCurrentPage().takeAction(action);
    }

    @Когда("^(пользователь|он) \\((.*)\\)\\s*.*:$")
    public void userAction_0(final String who, final String action, final DataTable table) throws Throwable{
        Init.getPageFactory().getCurrentPage().takeAction(action, table);
    }

    @Когда("^(пользователь|он) \\((.*)\\) \"([^\"]*)\"$")
    public void userAction_0(final String who, final String action, final String param) throws Throwable {
        Init.getPageFactory().getCurrentPage().takeAction(action, param);
    }

    @Когда("^(?:открывается страница|открывается вкладка) \"(.*?)\"$")
    public void userOnThePage(String title) throws Throwable {
        Init.getPageFactory().currentPage = null;
        Init.getPageFactory().getPage(title);
    }

    // -------------------------------------------- Проверки -----------------------------------------------------------
    @Тогда("^проверяет, что \"([^\"]*)\"$")
    public void userCheckThat_0(final String rule) throws Throwable {
        Init.getPageFactory().getCurrentPage().fireValidationRule(rule);
    }
}
