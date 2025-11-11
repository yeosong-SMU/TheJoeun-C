import 'dart:convert';

import 'package:http/http.dart' as http;

import 'address_book.dart';

class ApiService {
  final String baseUrl;

  ApiService(this.baseUrl);

  Future<List<AddressBook>> list() async {
    final res = await http.get(Uri.parse('$baseUrl/api/address'));
    if(res.statusCode != 200) {
      throw Exception('LIST ${res.statusCode}');
    }
    final data = json.decode(res.body) as List;
    return data.map((e) => AddressBook.fromJson(e)).toList();
  }

  Future<AddressBook> get(int id) async {
    final res = await http.get(Uri.parse('$baseUrl/api/address/$id'));
    if(res.statusCode != 200) {
      throw Exception('GET ${res.statusCode}');
    }
    return AddressBook.fromJson(json.decode(res.body));
  }

  Future<AddressBook> create(AddressBook ab) async {
    final res = await http.post(
      Uri.parse('$baseUrl/api/address'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(ab.toJson()),
    );
    if(res.statusCode != 200 && res.statusCode != 201) {
      throw Exception('CREATE ${res.statusCode}');
    }
    return AddressBook.fromJson(json.decode(res.body));
  }

  Future<AddressBook> update(AddressBook ab) async {
    if (ab.id == null) throw Exception('ID required');
    final res = await http.put(
      Uri.parse('$baseUrl/api/address/${ab.id}'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(ab.toJson()),
    );
    if(res.statusCode != 200) {
      throw Exception('UPDATE ${res.statusCode}');
    }
    return AddressBook.fromJson(json.decode(res.body));
  }

  Future<void> delete(int id) async {
    final res = await http.delete(Uri.parse('$baseUrl/api/address/$id'));
    if(res.statusCode != 200 && res.statusCode != 204) {
      throw Exception('DELETE ${res.statusCode}');
    }
  }
}