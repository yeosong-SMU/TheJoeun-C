class Prediction {
  final double predictedPrice;
  final String? currency;

  Prediction({required this.predictedPrice, this.currency});

  factory Prediction.fromJson(Map<String, dynamic> json) {
    return Prediction(
      predictedPrice: (json['predicted_price'] as num).toDouble(),
      currency: json['currency'] as String?,
    );
  }

  @override
  String toString() =>
      "예측 가격: $predictedPrice${currency != null ? ' $currency' : ''}";
}