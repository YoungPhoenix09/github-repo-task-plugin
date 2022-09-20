package paynefulapps.gocd.github.repo.task;

import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;

// TODO: change this to allow configuration options in your configuration
public class GetConfigRequest {

    public GoPluginApiResponse execute() {
        HashMap<String, Object> config = new HashMap<>();

        HashMap<String, Object> url = new HashMap<>();
        url.put("display-order", "0");
        url.put("display-name", "Repo URL");
        url.put("required", true);
        config.put(TaskPlugin.REPO_URL_PROPERTY, url);

        HashMap<String, Object> branch = new HashMap<>();
        branch.put("default-value", "master");
        branch.put("display-order", "1");
        branch.put("display-name", "Repo Branch Name");
        branch.put("required", true);
        config.put(TaskPlugin.BRANCH_NAME_PROPERTY, branch);

        HashMap<String, Object> token = new HashMap<>();
        token.put("display-order", "2");
        token.put("display-name", "GitHub Auth Token Env Variable");
        token.put("required", true);
        config.put(TaskPlugin.AUTH_TOKEN_VAR_PROPERTY, token);

        return DefaultGoPluginApiResponse.success(TaskPlugin.GSON.toJson(config));
    }
}
