package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.DbManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/mainTable.fxml"));
        DbManager db = new DbManager();
        db.createTablesAndForeignKeys();
        primaryStage.setTitle("Библиотека программных продуктов");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}
//TODO ПРОВЕРИТЬ ВСЕ ПРОВЕРКИ
//TODO СДЕЛАТЬ КНОПКИ С onAction(а не лямбдой)
//TODO все перевести в lowercase
//TODO удаление (если удаляем категорию - спрашивать, удалить ли все продукты, которые содержат категорию)
//TODO СДЕЛАТЬ ВЕЗДЕ IsEmpty
