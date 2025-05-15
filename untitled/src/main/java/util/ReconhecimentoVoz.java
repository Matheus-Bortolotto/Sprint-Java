package util;

/**
 * Simula reconhecimento de voz para transcrição médica.
 */
public class ReconhecimentoVoz {
    private String audioCapturado;
    private String textoTranscrito;

    public void capturarAudio(String audio) {
        this.audioCapturado = audio;
    }

    public void transcreverAudio() {
        this.textoTranscrito = "Transcrição: " + audioCapturado;
    }

    public String getTextoTranscrito() {
        return textoTranscrito;
    }
}