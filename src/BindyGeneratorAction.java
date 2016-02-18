import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import pl.lodz.p.BindyGeneratorApp;

/**
 * Created by maciek on 01/02/16.
 */
public class BindyGeneratorAction extends AnAction {

    public BindyGeneratorAction() {
        super("Bindy Generator");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        GenerateDialog dlg = new GenerateDialog();
        dlg.show();
        System.out.println(dlg.isOK());

        String[] a = new String[1];
        try {
            BindyGeneratorApp.generate(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(AnActionEvent e) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        boolean isPlainFile = false, isCsv = false;
        if (file != null) {
            isPlainFile = file.getFileType() instanceof PlainTextFileType;
            isCsv = "csv".equalsIgnoreCase(file.getExtension());
        }

        e.getPresentation().setEnabled(isPlainFile && isCsv);
    }
}
