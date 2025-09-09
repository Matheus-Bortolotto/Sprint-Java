import argparse
import whisper
from scipy.io.wavfile import read
import numpy as np
import sys
import os

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
NOME_ARQUIVO_TRANSCRICAO = os.path.join(BASE_DIR, "transcricao.txt")

def transcrever_audio(caminho_audio):
    try:
        model = whisper.load_model("small")
        taxa, data = read(caminho_audio)
        audio_float = data.astype(np.float32) / np.iinfo(np.int16).max
        if audio_float.ndim > 1:
            audio_float = audio_float[:, 0]
        result = model.transcribe(audio_float, language="pt", fp16=False)
        texto = result.get("text", "").strip()
        print(texto)  # stdout para o Java capturar
        with open(NOME_ARQUIVO_TRANSCRICAO, "w", encoding="utf-8") as f:
            f.write(texto)
        return texto
    except Exception as e:
        print("Erro na transcrição:", e, file=sys.stderr)
        return ""

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--audio", type=str, help="Caminho do WAV para transcrever.")
    args = parser.parse_args()

    if args.audio:
        transcrever_audio(args.audio)
    else:
        print("Uso: python whisper_cli.py --audio arquivo.wav", file=sys.stderr)
