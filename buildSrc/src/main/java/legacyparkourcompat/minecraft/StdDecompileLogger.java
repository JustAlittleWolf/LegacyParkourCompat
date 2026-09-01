package legacyparkourcompat.minecraft;

final class StdDecompileLogger implements DecompileLogger {
    @Override
    public void lifecycle(String message, Object... args) {
        System.out.println(DecompileLogger.format(message, args));
    }

    @Override
    public void warn(String message, Object... args) {
        System.err.println(DecompileLogger.format(message, args));
    }

    @Override
    public void info(String message, Object... args) {
        System.out.println(DecompileLogger.format(message, args));
    }

    @Override
    public void error(String message, Object... args) {
        System.err.println(DecompileLogger.format(message, args));
    }
}
