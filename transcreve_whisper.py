import whisper
import sounddevice as sd
from scipy.io.wavfile import write, read
import numpy as np
import time
import os

BASE_DIR = "C:/Users/Matheus/Documents/GitHub/Sprint-Java"
NOME_ARQUIVO_AUDIO = os.path.join(BASE_DIR, "gravacao.wav")
NOME_ARQUIVO_TRANSCRICAO = os.path.join(BASE_DIR, "transcricao.txt")

def gravar_audio(nome_arquivo=NOME_ARQUIVO_AUDIO, duracao=5, taxa=16000):
    print("Preparando para gravar...", flush=True)
    time.sleep(1)
    print("3...")
    time.sleep(1)
    print("2...")
    time.sleep(1)
    print("1...")
    print("Gravando agora! Fale à vontade...")
    audio = sd.rec(int(duracao * taxa), samplerate=taxa, channels=1, dtype='int16')
    sd.wait()
    write(nome_arquivo, taxa, audio)
    print("Gravação salva como", nome_arquivo)
    return nome_arquivo

def transcrever_audio(caminho_audio):
    try:
        model = whisper.load_model("small")
        taxa, data = read(caminho_audio)
        audio_float = data.astype(np.float32) / np.iinfo(np.int16).max
        if audio_float.ndim > 1:
            audio_float = audio_float[:, 0]
        result = model.transcribe(audio_float, language="pt", fp16=False)
        texto = result["text"]
        print("Transcrição:", texto)
        with open(NOME_ARQUIVO_TRANSCRICAO, "w", encoding="utf-8") as f:
            f.write(texto)
        return texto
    except Exception as e:
        print("Erro na transcrição:", e)
        return ""

if __name__ == "__main__":
    arquivo = gravar_audio()
    texto = transcrever_audio(arquivo)
