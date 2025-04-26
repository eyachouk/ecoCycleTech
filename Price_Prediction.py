from flask import Flask, request, jsonify
import joblib

app = Flask(__name__)
model = joblib.load('modele_prix_appareil.pkl')

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
