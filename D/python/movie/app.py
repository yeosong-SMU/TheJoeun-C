from flask import Flask, jsonify, request
from flask_cors import CORS
import joblib

app = Flask(__name__)
CORS(app)

model = joblib.load('d:/data/movie/content_recommender.pkl')
sim_matrix = model['sim_matrix']
df_movies = model['df_movies']
movie_id_to_index = model['movie_id_to_index']
index_to_movie_id = model['index_to_movie_id']

@app.route('/recommend_by_movie/<int:movie_id>', methods=['GET'])
def recommend_by_movie(movie_id):
    top_n = int(request.args.get('top_n', 5))

    if movie_id not in movie_id_to_index:
        return jsonify({'error': 'Movie not found'}), 404
    
    idx = movie_id_to_index[movie_id]

    sim_scores = sorted(
        [(i, score) for i, score in enumerate(sim_matrix[idx]) if i != idx],
        key = lambda x: x[1],
        reverse = True
    )

    recommendations = [
        {
            'movieId': int(index_to_movie_id[i]),
            'title': df_movies.loc[i, 'title'],
            'score': float(round(score, 4))
        }
        for i, score in sim_scores[:top_n]
    ]

    return jsonify({
        'movieId': movie_id,
        'recommendations': recommendations
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)