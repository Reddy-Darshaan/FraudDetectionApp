from flask import Flask, request, jsonify
import sounddevice as sd
from scipy.io.wavfile import write
import speech_recognition as sr
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
import text2emotion as te
import os

app = Flask(__name__)

# Record Audio
def record_audio(filename="live_audio.wav", duration=3, fs=44100):
    print("Recording...")
    audio_data = sd.rec(int(fs * duration), samplerate=fs, channels=1, dtype='int16')
    sd.wait()
    write(filename, fs, audio_data)
    print("Recording saved!")

# Convert Speech to Text
def speech_to_text(audio_path):
    recognizer = sr.Recognizer()
    with sr.AudioFile(audio_path) as source:
        audio_data = recognizer.record(source)
        try:
            return recognizer.recognize_google(audio_data)
        except:
            return "Could not transcribe speech"

# Sentiment Analysis
def analyze_sentiment(text):
    analyzer = SentimentIntensityAnalyzer()
    sentiment_score = analyzer.polarity_scores(text)
    sentiment = "Positive" if sentiment_score['compound'] >= 0.05 else "Negative" if sentiment_score['compound'] <= -0.05 else "Neutral"
    return sentiment, sentiment_score['compound']

# Emotion Analysis
def analyze_emotion(text):
    emotions = te.get_emotion(text)
    return max(emotions, key=emotions.get)

@app.route('/process_audio', methods=['POST'])
def process_audio():
    audio_file = request.files['audio']
    audio_path = 'live_audio.wav'
    audio_file.save(audio_path)

    # Step 2: Convert Speech to Text
    transcribed_text = speech_to_text(audio_path)

    # Step 3: Sentiment Analysis
    sentiment, score = analyze_sentiment(transcribed_text)

    # Step 4: Emotion Analysis
    emotion = analyze_emotion(transcribed_text)

    # Return response
    return jsonify({
        "transcribed_text": transcribed_text,
        "sentiment": sentiment,
        "sentiment_score": score,
        "emotion": emotion
    })

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
