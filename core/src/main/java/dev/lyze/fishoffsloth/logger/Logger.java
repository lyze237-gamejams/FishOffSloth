package dev.lyze.fishoffsloth.logger;

import com.badlogic.gdx.Gdx;
import lombok.var;

public class Logger {
    private final String prefix;

    public Logger(String prefix) {
        this.prefix = prefix;
    }
    
    public void logFatal(String message, Exception exception) {
        log(Level.Fatal, message, exception);
    }

    public void logFatal(String message) {
        log(Level.Fatal, message);
    }
    
    public void logError(String message, Exception exception) {
        log(Level.Error, message, exception);
    }

    public void logError(String message) {
        log(Level.Error, message);
    }
    
    public void logWarn(String message, Exception exception) {
        log(Level.Warn, message, exception);
    }

    public void logWarn(String message) {
        log(Level.Warn, message);
    }

    public void logInfo(String message, Exception exception) {
        log(Level.Info, message, exception);
    }

    public void logInfo(String message) {
        log(Level.Info, message);
    }

    public void logDebug(String message, Exception exception) {
        log(Level.Debug, message, exception);
    }

    public void logDebug(String message) {
        log(Level.Debug, message);
    }

    private void log(Level level, String message, Exception exception) {
        var time = " (" + Gdx.graphics.getFrameId() + ") ";

        if (level == Level.Debug)
            Gdx.app.debug(level.toString(), prefix + time + message, exception);
        else
            Gdx.app.log(level.toString(), prefix + time + message, exception);
    }

    private void log(Level level, String message) {
        var time = " (" + Gdx.graphics.getFrameId() + ") ";

        if (level == Level.Debug)
            Gdx.app.debug(level.toString(), prefix + time + message);
        else
            Gdx.app.log(level.toString(), prefix + time + message);
    }

    enum Level {
        Debug, Info, Warn, Error, Fatal
    }
}
