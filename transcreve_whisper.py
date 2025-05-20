import whisper
import sounddevice as sd
from scipy.io.wavfile import write, read
import numpy as np
import os

def gravar_e_transcrever():
    fs = 16000
    duracao = 15
    nome_arquivo = "gravacao.wav"

    print("Gravando...")
    audio = sd.rec(int(fs * duracao), samplerate=fs, channels=1, dtype="int16")
    sd.wait()
    write(nome_arquivo, fs, audio)
    print("Gravação salva como", nome_arquivo)

    print("Carregando modelo Whisper...")
    model = whisper.load_model("small")

    taxa, data = read(nome_arquivo)
    audio_float = data.astype(np.float32) / np.iinfo(np.int16).max
    if audio_float.ndim > 1:
        audio_float = audio_float[:, 0]

    print("Transcrevendo...")
    result = model.transcribe(audio_float, language="pt", fp16=False)
    texto = result["text"]
    print("Transcrição:")
    print(texto)

    # Caminho fixo para o arquivo .txt que o Java vai ler
    caminho_txt = "C:/Users/Matheus/Documents/GitHub/Sprint-Java/transcricao.txt"
    with open(caminho_txt, "w", encoding="utf-8") as f:
        f.write(texto)

if __name__ == "__main__":
    gravar_e_transcrever()
