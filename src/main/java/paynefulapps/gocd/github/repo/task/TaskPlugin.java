package paynefulapps.gocd.github.repo.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Arrays;

@Extension
public class TaskPlugin implements GoPlugin {

    public static final String REPO_URL_PROPERTY = "repoUrl";

    public static final String BRANCH_NAME_PROPERTY = "branchName";

    public static final String AUTH_TOKEN_VAR_PROPERTY = "authTokenVar";

    public static final Gson GSON = new GsonBuilder().serializeNulls().create();

    public static Logger LOGGER = Logger.getLoggerFor(TaskPlugin.class);

    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
        if ("configuration".equals(request.requestName())) {
            return new GetConfigRequest().execute();
        } else if ("validate".equals(request.requestName())) {
            return new ValidateRequest().execute(request);
        } else if ("execute".equals(request.requestName())) {
            return new ExecuteRequest().execute(request);
        } else if ("view".equals(request.requestName())) {
            return new GetViewRequest().execute();
        }
        throw new UnhandledRequestTypeException(request.requestName());
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("task", Arrays.asList("1.0"));
    }
}
