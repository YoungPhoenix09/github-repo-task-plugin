package paynefulapps.gocd.github.repo.task;

import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest {
    public GoPluginApiResponse execute(GoPluginApiRequest request) {
        HashMap<String, Object> validationResult = new HashMap<>();
        int responseCode = DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE;
        Map configMap = (Map) new GsonBuilder().create().fromJson(request.requestBody(), Object.class);
        HashMap errorMap = new HashMap();
        if (!configMap.containsKey(TaskPlugin.REPO_URL_PROPERTY) || ((Map) configMap.get(TaskPlugin.REPO_URL_PROPERTY)).get("value") == null || ((String) ((Map) configMap.get(TaskPlugin.REPO_URL_PROPERTY)).get("value")).trim().isEmpty()) {
            errorMap.put(TaskPlugin.REPO_URL_PROPERTY, "Repo URL cannot be empty");
        }
        if (!configMap.containsKey(TaskPlugin.BRANCH_NAME_PROPERTY) || ((Map) configMap.get(TaskPlugin.BRANCH_NAME_PROPERTY)).get("value") == null || ((String) ((Map) configMap.get(TaskPlugin.BRANCH_NAME_PROPERTY)).get("value")).trim().isEmpty()) {
            errorMap.put(TaskPlugin.BRANCH_NAME_PROPERTY, "Repo branch name cannot be empty");
        }
        validationResult.put("errors", errorMap);
        return new DefaultGoPluginApiResponse(responseCode, TaskPlugin.GSON.toJson(validationResult));
    }
}
