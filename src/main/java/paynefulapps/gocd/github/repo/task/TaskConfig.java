package paynefulapps.gocd.github.repo.task;

import java.util.Map;

public class TaskConfig {
    private final String repoUrl;
    private final String branchName;
    private final String authToken;

    public TaskConfig(Map config) {
        repoUrl = getValue(config, TaskPlugin.REPO_URL_PROPERTY);
        branchName = getValue(config, TaskPlugin.BRANCH_NAME_PROPERTY);
        authToken = getValue(config, TaskPlugin.AUTH_TOKEN_PROPERTY);
    }

    private String getValue(Map config, String property) {
        return (String) ((Map) config.get(property)).get("value");
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getAuthToken() {
        return authToken;
    }
}
