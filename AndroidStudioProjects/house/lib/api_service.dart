import 'dart:convert';

import 'package:http/http.dart' as http;

import 'prediction.dart';

class ApiService {
  static const String _base = "http://127.0.0.1:8000";

  static Future<Prediction> predict({
    required int rooms,
    required double areaM2,
  }) async {
    final url = Uri.parse("$_base/predict");
    final res = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({"rooms": rooms, "area_m2": areaM2}),
    );

    if(res.statusCode == 200) {
      final data = jsonDecode(res.body) as Map<String, dynamic>;
      return Prediction.fromJson(data);
    } else {
      throw Exception("Server error: ${res.statusCode}");
    }
  }
}