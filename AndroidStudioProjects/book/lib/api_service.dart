import 'dart:convert';
import 'package:http/http.dart' as http;

import 'book.dart';

class ApiService {
  final String baseUrl;

  ApiService(this.baseUrl);

  Future<List<Book>> list() async {
    final res = await http.get(Uri.parse('$baseUrl/api/books'));
    if(res.statusCode != 200) throw Exception('LIST ${res.statusCode}');
    final data = json.decode(res.body) as List;
    return data.map((e) => Book.fromJson(e)).toList();
  }

  Future<Book> get(int id) async {
    final res = await http.get(Uri.parse('$baseUrl/api/books/$id'));
    if(res.statusCode != 200) throw Exception('GET ${res.statusCode}');
    return Book.fromJson(json.decode(res.body));
  }

  Future<Book> create(Book b) async {
    final res = await http.post(
      Uri.parse('$baseUrl/api/books'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(b.toJson()),
    );
    if(res.statusCode != 200 && res.statusCode != 201)
      throw Exception('CREATE');
    return Book.fromJson(json.decode(res.body));
  }

  Future<Book> update(Book b) async {
    final res = await http.put(
      Uri.parse('$baseUrl/api/books/${b.id}'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(b.toJson()),
    );
    if(res.statusCode != 200) throw Exception('UPDATE');
    return Book.fromJson(json.decode(res.body));
  }

  Future<void> delete(int id) async {
    final res = await http.delete(Uri.parse('$baseUrl/api/books/$id'));
    if(res.statusCode != 200 && res.statusCode != 204)
      throw Exception('DELETE');
  }
}