class Prediction {
  final int prediction;
  final List<TopProb> top3;

  Prediction({required this.prediction, required this.top3});

  factory Prediction.fromJson(Map<String, dynamic> j) => Prediction(
      prediction: j['prediction'] as int,
      top3: ((j['top3'] as List?) ?? [])
      .map(
          (e) => TopProb(
            digit: e['digit'] as int,
            prob: (e['prob'] as num).toDouble(),
          ),
      ).toList(),
  );
}

class TopProb {
  final int digit;
  final double prob;

  TopProb({required this.digit, required this.prob});
}