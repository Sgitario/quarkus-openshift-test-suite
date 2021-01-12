package io.quarkus.ts.openshift.common.after;

import io.quarkus.ts.openshift.common.Command;

public class PrintStatusOnOpenShiftFailureActionImpl implements OnOpenShiftFailureAction {

    @Override
    public void execute() {
        new Command("oc", "status", "--suggest").runAndWait();
    }

}
