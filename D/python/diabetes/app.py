from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
import joblib

app = Flask(__name__)
CORS(app)

model = joblib.load('d:/data/diabetes/diabetes_model.pkl')

input_fields = [
    "pregnancies", "glucose", "bloodPressure", "skinThickness", 
    "insulin", "bmi", "diabetesPedigree", "age"
]

@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json()

        if not isinstance(data, dict):
            return jsonify({"error": "Invalid input format"}), 400
        
        input_values = []
        for field in input_fields:
            value = data.get(field, 0)
            try:
                input_values.append(float(value))
            except (ValueError, TypeError) :
                input_values.append(0.0)
        
        input_array = np.array([input_values])
        prediction = model.predict(input_array)[0]
        probability = model.predict_proba(input_array)[0][1]

        return jsonify({
            "prediction": int(prediction),
            "probability": round(float(probability), 4)
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=8000, debug=True)