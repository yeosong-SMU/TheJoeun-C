import 'dart:convert';
import 'dart:typed_data';

import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'package:mime/mime.dart';

import 'prediction.dart';

class ApiService {
  ApiService._();    //private 생성자

  static final instance = ApiService._();
  final String baseUrl = "http://127.0.0.1:5000";

  Future<Prediction> predictDigit(
    Uint8List png, {
      String filename = 'digit.png',
    }
  ) async {
    final req = http.MultipartRequest('POST', Uri.parse("$baseUrl/predict"));
    final mime = lookupMimeType(filename, headerBytes: png) ?? "image/png";
    req.files.add(
      http.MultipartFile.fromBytes(
        'file',
        png,
        filename: filename,
        contentType: MediaType.parse(mime),
      ),
    );
    final res = await http.Response.fromStream(await req.send());
    if(res.statusCode != 200) {
      throw Exception("HTTP ${res.statusCode} : ${res.body}");
    }
    return Prediction.fromJson(json.decode(res.body) as Map<String, dynamic>);
  }
}