import 'dart:convert';  //json 변환기

import 'package:http/browser_client.dart';
import 'package:http/http.dart' as http;

class Member {
  final String id;
  final String password;
  final String name;
  final String email;
  final String role;
  final String? regdate;

  //생성자, required 필수값
  Member({
    required this.id,
    required this.password,
    required this.name,
    required this.email,
    required this.role,
    this.regdate,
  });

  // json ==> dto 변환기
  factory Member.fromJson(Map<String, dynamic> j) => Member (
    id: j['id'] ?? '',
    //null일 때 대체값
    password: j['password'] ?? '',
    name: j['name'] ?? '',
    email: j['email'] ?? '',
    role: j['role'] ?? 'user',
    regdate: j['regdate'],
  );

  //mapp => json 변환기
Map<String, dynamic> toJson() => {
  'id': id,
  'password': password,
  'name': name,
  'email': email,
  'role': role,
  if(regdate != null) 'regdate': regdate,
  };
}

class ApiService {
  static const String base = 'http://localhost/api/members';

  // BrowserClient 객체를 만들고 withCredentials에 값 할당
  final http.Client _client = BrowserClient()..withCredentials = true;

  // Future<자료형> 비동기 방식 리턴자료형
  Future<bool> signUp(Member m) async {
    final url = Uri.parse(base);
    final res = await _client.post(
      url,
      headers: {'content-Type': 'application/json' },
      body: jsonEncode({
        'id': m.id,
        'password': m.password,
        'name': m.name,
        'email': m.email,
      }),
    );
    return res.statusCode == 200 || res.statusCode == 201;
  }

  Future<Member?> login(String id, String password) async {
    final url = Uri.parse('$base/login');
    final res = await _client.post(
      url,
      headers: {'Content-Type': 'application/json' },
      body: jsonEncode({'id': id, 'password': password}),
    );
    if(res.statusCode == 200) {
      return Member.fromJson(jsonDecode(res.body));
    }
    return null;
  }

  Future<bool> logout() async {
    final url = Uri.parse('$base/logout');
    final res = await _client.post(url);
    return res.statusCode == 200;
  }

  Future<Map<String, dynamic>> session() async {
    final url = Uri.parse('$base/session');
    final res = await _client.get(url);
    if(res.statusCode == 200) {
      return jsonDecode(res.body) as Map<String, dynamic>;
    }
    return {};
  }

  Future<List<Member>> listMembers() async {
    final url = Uri.parse(base);
    final res = await _client.get(url);
    if(res.statusCode == 200) {
      final data = jsonDecode(res.body) as List<dynamic>;
      return data.map((e) => Member.fromJson(e)).toList();
    }
    return [];
  }

  Future<Member?> getMember(String id) async {
    final url = Uri.parse('$base/$id');
    final res = await _client.get(url);
    if(res.statusCode == 200) {
      return Member.fromJson(jsonDecode(res.body));
    }
    return null;
  }

  Future<bool> updateMember(Member m) async {
    final url = Uri.parse('$base/${m.id}');
    final res = await _client.put(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'id': m.id,
        'name': m.name,
        'email': m.email,
        'role': m.role,
      }),
    );
    return res.statusCode == 200;
  }

  Future<bool> deleteMember(String id) async {
    final url = Uri.parse('$base/$id');
    final res = await _client.delete(url);
    return res.statusCode == 200;
  }
}