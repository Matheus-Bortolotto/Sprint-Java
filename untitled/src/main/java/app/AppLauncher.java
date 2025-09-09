package app;

public class AppLauncher {
    public static void main(String[] args) {
        String ui = "console";
        for (String a : args) {
            String v = a.trim().toLowerCase();
            if (v.startsWith("--ui=")) ui = v.substring("--ui=".length());
            if (v.equals("--terminal")) ui = "console";
            if (v.equals("--gui")) ui = "swing";
        }

        switch (ui) {
            case "swing":
            case "gui":
                UiMain.start();
                break;
            case "console":
            default:
                TerminalRunner.start();
        }
    }
}
