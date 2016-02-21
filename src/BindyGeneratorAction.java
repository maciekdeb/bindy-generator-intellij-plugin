import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import pl.lodz.p.BindyGeneratorApp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by maciek on 01/02/16.
 */
public class BindyGeneratorAction extends AnAction {

    public BindyGeneratorAction() {
        super("Bindy Generator");
    }

    public void actionPerformed(AnActionEvent event) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        GenerateDialog dlg = new GenerateDialog(file, psiFile);
        dlg.show();
        if (dlg.isOK()) {
            try {
                BindyGeneratorApp.generate(dlg.getArguments());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
