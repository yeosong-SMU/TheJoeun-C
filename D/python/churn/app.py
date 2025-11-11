from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import pandas as pd
from sklearn.preprocessing import OneHotEncoder
import numpy as np

app = Flask(__name__)
CORS(app)

model = joblib.load('d:/data/churn/rf_best_model.joblib')

train_df = pd.read_csv('d:/data/churn/churn_sample.csv')
categorical_cols = ['gender', 'contract_type', 'payment_method', 'internet_service']
encoder = OneHotEncoder(drop='first')
encoder.fit(train_df[categorical_cols])

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    df = pd.DataFrame([data])

    X_num = df.drop(columns=categorical_cols).values
    X_cat = encoder.transform(df[categorical_cols]).toarray()
    X = np.hstack([X_num, X_cat])

    pred = model.predict(X)[0]
    prob = model.predict_proba(X)[0][1]

    return jsonify({
        'predicted_churn': int(pred),
        'churn_probability': float(prob)
    })

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']

    if file.filename == '' :
        return jsonify({'error': 'No selected file'}), 400

    if file:
        file.save('d:/data/churn/test.csv')
        return jsonify({'message': 'File successfully uploaded'}), 200

@app.route('/predict-csv', methods=['GET'])
def predict_csv():
    try:
        df = pd.read_csv('d:/data/churn/test.csv')

        required_cols = ['age', 'tenure_months', 'monthly_charges', 'gender', 
        'contract_type', 'payment_method', 'internet_service']
        missing_cols = [col for col in required_cols if col not in df.columns]
        if missing_cols:
            return jsonify({'error': f'Missing columns: {", ".join(missing_cols)}'}), 400
        
        results = []

        for _, row in df.iterrows() :
            row_data = row[required_cols].to_dict()
            row_df = pd.DataFrame([row_data])

            X_num = row_df.drop(columns=categorical_cols).values
            X_cat = encoder.transform(row_df[categorical_cols]).toarray()
            X = np.hstack([X_num, X_cat])

            pred = model.predict(X)[0]
            prob = model.predict_proba(X)[0][1]

            results.append({
                'customerId': row['customer_id'], 
                'age': row['age'],
                'tenureMonths': row['tenure_months'],
                'monthlyCharges': row['monthly_charges'],
                'gender': row['gender'],
                'contractType': row['contract_type'],
                'paymentMethod': row['payment_method'],
                'internetService': row['internet_service'],
                'churn': int(pred),
                'churnProbability': float(prob)
            })
        return jsonify(results)
    
    except Exception as e: 
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__' :
    app.run(host='0.0.0.0', port=5000)