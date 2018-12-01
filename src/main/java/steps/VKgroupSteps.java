package steps;

import cucumber.api.DataTable;
import pages.VKgroupPage;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by VITALIY on 08.11.2017.
 */
public class VKgroupSteps {

    VKgroupPage page = new VKgroupPage();

    @Step("Создаем Альбом")
    public void createAlbum(){
        page.toAlbums();
    }

    @Step("Добавляем название и описание")
    public void albumCreate(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> page.fillAlbumParams(field, value));
    }

    @Step("Залив фото в альбом")
    public void photoGet(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> page.photoStream(field, value));
    }

    @Step("Проверка наличия выгруженных фото")
    public void checkAlbum(){
        page.checkPhotoIsUploaded();
    }
}
