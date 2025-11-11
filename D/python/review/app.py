from flask import Flask, request, jsonify
import joblib
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
model = joblib.load("d:/data/review/sentiment_model.joblib")

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json(force=True) or {}
    text = (data.get("text") or "").strip()
    if not text:
        return jsonify({"error": "text is required"}), 400
    proba = model.predict_proba([text])[0]
    neg, pos = float(proba[0]), float(proba[1])
    return jsonify({"label": "POS" if pos >= 0.5 else "NEG", "score" : pos})

if __name__ == "__main__" :
    app.run(host="0.0.0.0", port=5001)