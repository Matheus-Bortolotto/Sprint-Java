import whisper
import sounddevice as sd
from scipy.io.wavfile import write

def gravar_audio(nome_arquivo="gravacao.wav", duracao=5, taxa=16000):
    print(f"Gravando {duracao} segundos de áudio...")
    audio = sd.rec(int(duracao * taxa), samplerate=taxa, channels=1, dtype='int16')
    sd.wait()
    write(nome_arquivo, taxa, audio)
    print("Gravação salva como", nome_arquivo)
    return nome_arquivo

def transcrever_audio(caminho_audio):
    print("Carregando modelo Whisper...")
    model = whisper.load_model("small")
    print("Transcrevendo áudio...")
    result = model.transcribe(caminho_audio, language='pt')
    print("Transcrição:")
    print(result["text"])
    return result["text"]

if _name_ == "_main_":
    arquivo = gravar_audio()
    transcrever_audio(arquivo)