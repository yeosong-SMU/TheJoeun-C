from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import joblib

app = Flask(__name__)
CORS(app)

model = joblib.load("d:/data/car/car_model.pkl")
X_columns = joblib.load("d:/data/car/X_columns.pkl")

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        input_df = pd.DataFrame([data])

        input_df = pd.get_dummies(input_df).reindex(columns=X_columns, fill_value=0)

        pred_price = model.predict(input_df)[0]
        return jsonify({'predicted_price': int(pred_price)})
    except Exception as e:
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(port=5000, debug=True)