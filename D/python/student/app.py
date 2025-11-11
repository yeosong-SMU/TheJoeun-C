from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import joblib

app = Flask(__name__)
CORS(app)

model = joblib.load("d:/data/student/student_score_model.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json()
        print(data)
        required_fields = ["age", "studytime", "failures", "absences", "G1", "G2", "Medu", "Fedu"]
        if not all (field in data for field in required_fields):
            return jsonify({"error": "Missing fields"}), 400
        
        input_df = pd.DataFrame([[
            data["age"], data["studytime"],
            data["failures"], data["absences"],
            data["G1"], data["G2"],
            data["Medu"], data["Fedu"]
        ]], columns=required_fields)

        prediction = model.predict(input_df)[0]
        return jsonify({"prediction": round(prediction, 2)})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=8000, debug=True)