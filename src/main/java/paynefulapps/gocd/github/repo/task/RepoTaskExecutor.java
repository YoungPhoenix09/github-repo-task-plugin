package paynefulapps.gocd.github.repo.task;

import com.thoughtworks.go.plugin.api.task.*;

// TODO: execute your task and setup stdout/stderr to pipe the streams to GoCD
public class RepoTaskExecutor {

    public Result execute(paynefulapps.gocd.github.repo.task.TaskConfig taskConfig, Context context, JobConsoleLogger console) {
        try {
            RepoFetcher repoFetcher = new RepoFetcher(context);
            return repoFetcher.fetchRepo(taskConfig.getRepoUrl(), taskConfig.getBranchName(), taskConfig.getAuthToken());
        } catch (Exception e) {
            return new Result(false, "Failed to pull down repo from URL: " + taskConfig.getRepoUrl(), e);
        }
    }
}
