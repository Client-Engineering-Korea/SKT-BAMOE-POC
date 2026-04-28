package org.acme.runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionPlan implements Serializable {
    private final List<ActionItem> autoSubscribe = new ArrayList<>();
    private final List<ActionItem> autoTerminate = new ArrayList<>();
    private final List<ActionItem> manualSubscribeRequired = new ArrayList<>();
    private final List<ActionItem> manualTerminateRequired = new ArrayList<>();

    public List<ActionItem> getAutoSubscribe() { return autoSubscribe; }
    public List<ActionItem> getAutoTerminate() { return autoTerminate; }
    public List<ActionItem> getManualSubscribeRequired() { return manualSubscribeRequired; }
    public List<ActionItem> getManualTerminateRequired() { return manualTerminateRequired; }
}
