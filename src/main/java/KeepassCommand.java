import picocli.CommandLine.Option;

class KeepassCommand {

    @Option(names = "--config", description = "the config file location.")
    String configLocation = "config.properties";

    @Option(names = "--key", negatable = true, description = "sync the key file")
    boolean keySync;
}
