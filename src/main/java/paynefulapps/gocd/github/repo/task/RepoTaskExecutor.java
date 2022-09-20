package paynefulapps.gocd.github.repo.task;

import com.thoughtworks.go.plugin.api.task.*;

// TODO: execute your task and setup stdout/stderr to pipe the streams to GoCD
public class RepoTaskExecutor {

    public Result execute(paynefulapps.gocd.github.repo.task.TaskConfig taskConfig, Context context, JobConsoleLogger console) {
        try {
            RepoFetcher repoFetcher = new RepoFetcher(context);
            String authToken = getTokenFromEnvironment(taskConfig, context);

            if (authToken == null) {
                return new Result(false, "Environment variable $" + taskConfig.getAuthTokenVar() + " does not have a value.");
            }

            return repoFetcher.fetchRepo(taskConfig.getRepoUrl(), taskConfig.getBranchName(), taskConfig.getAuthTokenVar());
        } catch (Exception e) {
            return new Result(false, "Failed to pull down repo from URL: " + taskConfig.getRepoUrl(), e);
        }
    }

    private String getTokenFromEnvironment(TaskConfig taskConfig, Context context) {
        String authTokenVar = taskConfig.getAuthTokenVar();

        return (String) context.getEnvironmentVariables().get(authTokenVar);
    }
}
