import picocli.CommandLine.Option;

class KeepassCommand {

    @Option(names = "--config", description = "the config file location.")
    String configLocation = "config.properties";

    @Option(names = "--key", negatable = true, description = "sync the key file")
    boolean keySync;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;
}
