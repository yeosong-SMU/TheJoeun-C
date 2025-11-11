import 'dart:convert';

import 'package:http/http.dart' as http;

import 'memo.dart';

class MemoService {
  final String baseUrl;

  MemoService({required this.baseUrl});

  Future<List<Memo>> fetchMemos() async {
    final res = await http.get(Uri.parse(baseUrl));
    final List<dynamic> data = json.decode(res.body);
    return data.map((e) => Memo.fromJson(e)).toList();
  }

  Future<void> createMemo(String content) async {
    //final res =
    await http.post(
      Uri.parse(baseUrl),
      headers: {'Content-Type': 'application/json'},
      body: json.encode({'content': content}),
    );
  }

  Future<void> updateMemo(int id, String content) async {
    await http.put(
      Uri.parse('$baseUrl/$id'),
      headers: {'Content-Type' : 'application/json'},
      body: json.encode({'content' : content}),
    );
  }

  Future<void> deleteMemo(int id) async {
    await http.delete(Uri.parse('$baseUrl/$id'));
  }
}