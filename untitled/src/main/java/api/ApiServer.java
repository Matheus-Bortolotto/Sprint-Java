package api;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import static io.javalin.apibuilder.ApiBuilder.*; // <- necessário para get/post/path(...)

public class ApiServer {
    public static void main(String[] args) {
        int port = 8080;
        for (String a : args) {
            if (a.startsWith("--port=")) {
                try { port = Integer.parseInt(a.substring(7)); } catch (Exception ignored) {}
            }
        }

        var medicoController = new MedicoController();
        var relatorioController = new RelatorioController();

        Javalin app = Javalin.create(config -> {
            // logging + CORS (API pública para o seu front)
            config.bundledPlugins.enableDevLogging();
            config.bundledPlugins.enableCors(cors -> cors.addRule(rule -> rule.anyHost()));

            // 🔁 ROTAS no Javalin 6: via router.apiBuilder(...)
            config.router.apiBuilder(() -> {
                get("/api/health", ctx -> ctx.result("ok"));

                path("api", () -> {
                    path("medicos", () -> {
                        get(medicoController::list);
                        post(medicoController::create);
                        path("{id}", () -> {
                            get(medicoController::get);
                            put(medicoController::update);
                            delete(medicoController::delete);
                        });
                    });

                    path("relatorios", () -> {
                        get(relatorioController::list);
                        post(relatorioController::create);
                        path("{id}", () -> {
                            get(relatorioController::get);
                            put(relatorioController::update);
                            delete(relatorioController::delete);
                        });
                    });
                });
            });
        });

        // Tratamento global de exceções
        app.exception(IllegalArgumentException.class, (e, ctx) ->
                ctx.status(HttpStatus.BAD_REQUEST).json(new ApiError(e.getMessage()))
        );
        app.exception(Exception.class, (e, ctx) ->
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ApiError("Erro interno: " + e.getMessage()))
        );

        app.start(port);
        System.out.println("API rodando em http://localhost:" + port + "/api/health");
    }
}
