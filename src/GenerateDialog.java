import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maciek on 17/02/16.
 */
public class GenerateDialog extends DialogWrapper {

    private static final String SPACE = " ";
    private String fileName;
    private String modelPath;
    private JPanel component;

    public GenerateDialog(VirtualFile file, PsiFile psiFile) {
        super(true);
        setTitle("Bindy Generator | CSV");
        this.fileName = file.getCanonicalPath();
        this.modelPath = getPathForModel(psiFile);
        this.component = new CsvDialog();
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        return component;
    }

    public String[] getArguments() {
        StringBuilder arguments = new StringBuilder();
        if (this.component instanceof CsvDialog) {
            CsvDialog csvComponent = (CsvDialog) this.component;
            arguments.append("-f").append(SPACE).append(this.fileName).append(SPACE);
            arguments.append("-p").append(SPACE).append(this.modelPath).append(SPACE);
            arguments.append("-c").append(SPACE).append(csvComponent.getClassName()).append(SPACE);
            arguments.append("csv").append(SPACE);
            arguments.append("--separator").append(SPACE).append(csvComponent.getSeparator()).append(SPACE);
            if (csvComponent.getSkipFirstLine()) {
                arguments.append("--skipFirstLine").append(SPACE);
            }
        }
        return arguments.toString().trim().split(SPACE);
    }

    private static String getPathForModel(PsiFile psiFile) {
        String pathForModel = psiFile.getProject().getBaseDir().getCanonicalPath();

        List<VirtualFile> childSources = Arrays.stream(ProjectRootManager.getInstance(psiFile.getProject()).getContentSourceRoots()).collect(Collectors.toList());
        List<String> parts = Arrays.asList("src", "main", "java");
        for (String part : parts) {
            childSources = childSources.stream().filter(d -> d.getCanonicalPath().contains(part)).collect(Collectors.toList());
        }

        if (childSources.size() == 1) {
            pathForModel = childSources.get(0).getCanonicalPath();
        }

        return pathForModel;
    }


}
