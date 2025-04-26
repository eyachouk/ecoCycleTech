import os
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LinearRegression
from sklearn.pipeline import Pipeline
import joblib
from flask import Flask, request, jsonify
from stop_words import get_stop_words

app = Flask(__name__)

MODEL_FILENAME = 'modele_prix_appareil.pkl'

def train_and_save_model():
    data = pd.DataFrame({
        'description': [
            'Smartphone avec écran 6 pouces, 64GB, couleur noire',
            'Ordinateur portable puissant, 16GB RAM, SSD 512GB',
            'Télévision 4K, 55 pouces, HDR',
        ],
        'prix': [300, 1200, 800]
    })

    stop_words_fr = get_stop_words('fr')

    pipeline = Pipeline([
        ('tfidf', TfidfVectorizer(stop_words=stop_words_fr)),
        ('regressor', LinearRegression())
    ])

    pipeline.fit(data['description'], data['prix'])

    current_dir = os.path.dirname(os.path.abspath(__file__))
    model_path = os.path.join(current_dir, MODEL_FILENAME)
    joblib.dump(pipeline, model_path)
    print(f"Modèle sauvegardé dans : {model_path}")

def load_model():
    current_dir = os.path.dirname(os.path.abspath(__file__))
    model_path = os.path.join(current_dir, MODEL_FILENAME)
    if not os.path.exists(model_path):
        print("Fichier modèle non trouvé, entraînement du modèle...")
        train_and_save_model()
    else:
        print(f"Chargement du modèle depuis : {model_path}")
    return joblib.load(model_path)

model = load_model()

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    description = data.get('description', '')
    if not description:
        return jsonify({'error': 'Description manquante'}), 400

    prediction = model.predict([description])
    prix_pred = round(float(prediction[0]), 2)
    return jsonify({'prix_pred': prix_pred})

if __name__ == '__main__':
    app.run(port=5000)
