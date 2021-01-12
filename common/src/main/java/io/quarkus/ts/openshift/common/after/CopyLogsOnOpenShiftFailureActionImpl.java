package io.quarkus.ts.openshift.common.after;

import java.io.File;

import io.quarkus.ts.openshift.common.Command;
import io.quarkus.ts.openshift.common.config.Config;
import io.quarkus.ts.openshift.common.injection.TestResource;
import io.quarkus.ts.openshift.common.util.OpenShiftUtil;

public class CopyLogsOnOpenShiftFailureActionImpl implements OnOpenShiftFailureAction {

    private static final String INSTANCES_LOGS_OUTPUT_DIRECTORY = "instance.logs";
    private static final String DEFAULT_LOG_OUTPUT_DIRECTORY = "target/logs";
    private static final String LOG_SUFFIX = ".log";

    @TestResource
    private OpenShiftUtil openShiftUtil;

    @Override
    public void execute() {
        String namespace = openShiftUtil.getNamespace();
        openShiftUtil.getPods().forEach(pod -> {
            String podName = pod.getMetadata().getName();
            new Command("oc", "logs", podName).outputToFile(getOutputFile(podName, namespace)).runAndWait();
        });
    }

    static String getOutputFolder() {
        return Config.get().getAsString(INSTANCES_LOGS_OUTPUT_DIRECTORY, DEFAULT_LOG_OUTPUT_DIRECTORY);
    }

    private static File getOutputFile(String name, String customLogFolderName) {
        File outputDirectory = new File(getOutputFolder(), customLogFolderName);
        outputDirectory.mkdirs();
        return new File(outputDirectory, name + LOG_SUFFIX);
    }

}
