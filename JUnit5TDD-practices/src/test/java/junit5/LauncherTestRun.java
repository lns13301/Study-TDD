package junit5;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;


public class LauncherTestRun {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener summaryGeneratingListener =  new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(summaryGeneratingListener);
        LauncherDiscoveryRequest launcherDiscoveryRequest = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectPackage("bookstoread"))
                .build();
        launcher.execute(launcherDiscoveryRequest);
        summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
    }
}
