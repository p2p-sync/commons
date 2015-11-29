package org.rmatil.sync.commons.test.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Config {
    DEFAULT();

    private Path rootTestDir;

    private String testFileName1 = "file1.txt";

    private long timeGapPushInterval;

    Config() {
        rootTestDir = Paths.get("./org.rmatil.sync.event.aggregator.test.dir");
        timeGapPushInterval = 5500L;
    }

    public Path getRootTestDir() {
        return this.rootTestDir;
    }

    public long getTimeGapPushInterval() {
        return this.timeGapPushInterval;
    }

    public String getTestFileName1() {
        return this.testFileName1;
    }
}
