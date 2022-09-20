package paynefulapps.gocd.github.repo.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RepoFetcher {
    private final HttpClient client;
    private Context pluginContext;

    public RepoFetcher(Context context) {
        pluginContext = context;

        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    public Result fetchRepo(String repoUrl, String branchName, String authToken) {
        try {
            File zippedRepo = downloadRepo(repoUrl, branchName, authToken);
            unzipRepo(zippedRepo);
            zippedRepo.delete();

            return new Result(true, "Successfully downloaded " + branchName + " branch of " + repoUrl + ".");
        } catch (Exception e) {
            return new Result(false, "Failed downloading repo. Please verify your URL, branch, and auth token are correct.");
        }
    }

    public File downloadRepo(String repoUrl, String branchName, String authToken) throws IOException, InterruptedException {
        String repoDownloadUrl = repoUrl + "/archive/refs/heads/" + branchName + ".zip";
        URI uri = URI.create(repoDownloadUrl);
        HttpRequest repoRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .header("Authorization", "Bearer " + authToken)
                .build();

        HttpResponse<byte[]> response =  client.send(repoRequest, HttpResponse.BodyHandlers.ofByteArray());
        System.out.println("Status code: " + response.statusCode());

        String filePath = pluginContext.getWorkingDir() + "/repo.zip";
        File zippedRepo = new File(filePath);
        FileOutputStream fileWriter = new FileOutputStream(zippedRepo);
        fileWriter.write(response.body());
        fileWriter.close();

        return zippedRepo;
    }

    public void unzipRepo(File zippedRepo) throws IOException {
        ProcessBuilder unzipCommandBuilder = new ProcessBuilder("unzip", zippedRepo.getAbsolutePath());
        unzipCommandBuilder.directory(new File(pluginContext.getWorkingDir()));
        unzipCommandBuilder.start();
    }
}
