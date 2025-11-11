from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
import joblib
from tensorflow.keras.models import load_model
from sklearn.preprocessing import StandardScaler
import pandas as pd

app = Flask(__name__)
CORS(app)

model = load_model('d:/data/health/health_score_model.keras')

df = pd.read_csv('d:/data/health/health_data.csv')
X = df.drop(columns=['score'])
scaler = joblib.load('health_scaler.pkl') if 'health_scaler.pkl' in joblib.os.listdir('.') else None
if scaler is None:
    scaler = StandardScaler()
    scaler.fit(X)
    joblib.dump(scaler, 'health_scaler.pkl')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    try:
        features = np.array([
            data['age'],
            data['gender'],
            data['height'],
            data['weight'],
            data['bmi'],
            data['systolic_bp'],
            data['diastolic_bp'],
            data['cholesterol'],
            data['smoker'],
            data['exercise_freq']
        ]).reshape(1, -1)

        features_scaled = scaler.transform(features)

        score = model.predict(features_scaled)[0][0]

        if score >= 80:
            message = "건강 상태가 매우 양호합니다."
        elif score >= 60:
            message = "보통입니다. 꾸준한 운동이 필요합니다."
        else:
            message = "주의가 필요합니다. 생활습관 개선이 필요합니다."
        return jsonify({
            'score': round(float(score), 1),
            'message': message
        })
    except Excetpion as e:
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(port=5000, debug=True)