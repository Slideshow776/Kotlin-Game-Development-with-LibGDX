package chapter6.rythmTapper

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import java.io.File;
import com.badlogic.gdx.files.FileHandle;

class FileUtils {
    companion object{
        private var finished: Boolean
        private var fileHandle: FileHandle
        private var openDialog = 1
        private var saveDialog = 2
    }
}
