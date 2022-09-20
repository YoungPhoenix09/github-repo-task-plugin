package paynefulapps.gocd.github.repo.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class RepoFetcherTest {
    private final RepoFetcher repoFetcher = new RepoFetcher();
    private final String repoUrl = "https://github.com/YoungPhoenix09/simple-image-server";
    private final String branchName = "main";
    private String authToken = "";

    @Before
    public void setup() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("./secrets.txt"));

        authToken = (String) props.get("authToken");
    }

    @Test
    public void repoIsFetchedAsZip() {
        String expectedRepoName = "./simple-image-server-main";
        Map<String,Object> contextMap = new HashMap<>();
        contextMap.put("workingDirectory", "./");
        Context context = new Context(contextMap);

        repoFetcher.fetchRepo(
            repoUrl,
            branchName,
            authToken,
            context
        );

        File repoDir = new File(expectedRepoName);
        assertTrue(repoDir.exists());
        assertTrue(repoDir.isDirectory());
    }

    @After
    public void cleanup() throws IOException {
        String repoPath = "./simple-image-server-main";
        File repoDir = new File(repoPath);

        if (repoDir.exists()) {
            ProcessBuilder deleteProcess = new ProcessBuilder();
            deleteProcess.command("rm", "-rf", repoPath);
            deleteProcess.start();
        }
    }
}
