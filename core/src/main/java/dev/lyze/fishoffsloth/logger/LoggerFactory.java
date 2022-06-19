package dev.lyze.fishoffsloth.logger;

public class LoggerFactory {
    public static Logger getLogger(String clazz) {
        return new Logger(clazz);
    }
}
