package util;

import javax.sound.sampled.*;
import java.io.File;

public class GravadorAudio {
    private TargetDataLine linha;
    private Thread writerThread;
    private File arquivoSaida;

    // 16 kHz, 16-bit, mono, signed, little-endian
    private static final AudioFormat FORMATO = new AudioFormat(
            16000.0f, 16, 1, true, false
    );

    public synchronized void iniciar(File wavFile) throws Exception {
        if (writerThread != null && writerThread.isAlive())
            throw new IllegalStateException("Já está gravando.");

        this.arquivoSaida = wavFile;

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, FORMATO);
        linha = (TargetDataLine) AudioSystem.getLine(info);
        linha.open(FORMATO);
        linha.start();

        writerThread = new Thread(() -> {
            try (AudioInputStream ais = new AudioInputStream(linha)) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, arquivoSaida);
            } catch (Exception e) {
                // Se a linha for fechada normalmente, ignore IOException
                if (linha != null && linha.isOpen()) {
                    e.printStackTrace();
                }
            }
        }, "WAV-Writer");
        writerThread.start();
    }

    public synchronized File encerrar() throws Exception {
        if (linha == null) return null;
        try {
            linha.stop();
            linha.close();
        } finally {
            linha = null;
        }
        if (writerThread != null) {
            writerThread.join(2000);
            writerThread = null;
        }
        return arquivoSaida;
    }

    public synchronized boolean gravando() {
        return linha != null && linha.isActive();
    }
}
