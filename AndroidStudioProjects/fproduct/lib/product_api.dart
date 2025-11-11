import 'dart:convert';
import 'dart:typed_data';

import 'package:http/http.dart' as http;

import 'config.dart';
import 'product.dart';

class ProductApi {
  final String base;

  ProductApi({String? baseUrl}) : base = baseUrl ?? apiBase;

  Future<List<Product>> list() async {
    final res = await http.get(Uri.parse('$base/api/products'));
    if(res.statusCode != 200) {
      throw Exception('list failed: ${res.statusCode}');
    }
    final data = json.decode(res.body) as List;
    return data
        .map((e) => Product.fromJson(e as Map<String, dynamic>))
        .toList();
  }
  Future<Product> create(Product p) async {
    final res = await http.post(
      Uri.parse('$base/api/products'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(p.toJson()),
    );
    if(res.statusCode != 201 && res.statusCode != 200) {
      throw Exception('create failed: ${res.statusCode}');
    }
    return Product.fromJson(json.decode(res.body) as Map<String, dynamic>);
  }

  Future<Product> update(Product p) async {
    if(p.id == null) throw Exception('update requires id');
    final res = await http.put(
      Uri.parse('$base/api/products/${p.id}'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(p.toJson()),
    );
    if (res.statusCode != 200) {
      throw Exception('update failed: ${res.statusCode}');
    }
    return Product.fromJson(json.decode(res.body) as Map<String, dynamic>);
  }

  Future<void> delete(int id) async {
    final res = await http.delete(Uri.parse('$base/api/products/$id'));
    if(res.statusCode != 204) {
      throw Exception('delete failed: ${res.statusCode}');
    }
  }

  Future<String> uploadImage(Uint8List bytes, String filename) async {
    final req = http.MultipartRequest(
      'POST',
      Uri.parse('$base/api/files/upload'),
    );

    req.files.add(
      http.MultipartFile.fromBytes('file', bytes, filename: filename),
    );

    final streamed = await req.send();
    final res = await http.Response.fromStream(streamed);
    if(res.statusCode != 200) {
      throw Exception('upload failed : ${res.statusCode}');
    }
    final j = json.decode(res.body) as Map<String, dynamic>;
    return j['url']?.toString() ?? '';
  }
}