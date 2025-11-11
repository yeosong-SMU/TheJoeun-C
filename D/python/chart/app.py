from flask import Flask, request, jsonify
import numpy as np
import pandas as pd
import os
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

def gbm_prices(days=90, s0=100.0, mu=0.12, sigma=0.25, seed=42):
    rng = np.random.default_rng(int(seed))   #랜덤값 범위
    dt = 1/252
    shocks = rng.normal((mu - 0.5*sigma**2)*dt, sigma*np.sqrt(dt), size=days)
    log_path = np.cumsum(shocks)
    prices = s0 * np.exp(log_path)
    p = np.insert(prices, 0, s0)
    returns = np.diff(p) / p[:-1]
    dates = pd.date_range(pd.Timestamp.today().normalize(), periods=days, freq="D")
    data = [
        {"date": d.date().isoformat(), "price": float(prices[i]), "ret": float(returns[i])}
        for i, d in enumerate(dates)
    ]
    return data

@app.get("/sim/stock")
def sim_stock():
    try:
        days = int(request.args.get("days", 90))
        mu = float(request.args.get("mu", 0.12))
        sigma = float(request.args.get("sigma", 0.25))
        s0 = float(request.args.get("s0", 100.0))
        seed = int(request.args.get("seed", 42))
        days = max(10, min(days, 365*3))
        data = gbm_prices(days=days, s0=s0, mu=mu, sigma=sigma, seed=seed)
        return jsonify({"params": {"days": days, "mu": mu, "sigma": sigma, "s0": s0, "seed": seed}, 
                        "data": data})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

def load_csv(path="data.csv") -> pd.DataFrame:
    if not os.path.exists(path):
        raise FileNotFoundError("data.csv not found")
    df = pd.read_csv(path)
    cols = {c.strip().lower(): c for c in df.columns}
    if "ts" not in cols or "y" not in cols:
        raise ValueError("data.csv must have columns: ts, y")
    ts_col, y_col = cols["ts"], cols["y"]
    df[ts_col] = pd.to_datetime(df[ts_col], errors="coerce")
    df = df.dropna(subset=[ts_col, y_col]).sort_values(ts_col)
    return df.rename(columns={ts_col: "ts", y_col: "y"})

@app.get("/api/series")
def series():
    try:
        limit = int(request.args.get("limit", 200))
        limit = max(1, min(limit, 5000))
        df = load_csv("d:/data/chart/stock_data.csv").tail(limit)
        data = [{"ts": ts.isoformat(), "y": float(v)} for ts, v in zip(df["ts"], df["y"])]
        return jsonify({"count": len(data), "data": data})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == "__main__":
    app.run(port=5000, debug=True)