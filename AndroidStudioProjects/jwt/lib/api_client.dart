import 'dart:async';
import 'dart:convert';

import 'package:http/browser_client.dart' as http_browser;
import 'package:http/http.dart' as http;

import 'config.dart';

class ApiClient {
  late final http.Client _client;

  ApiClient() {
    final bc = http_browser.BrowserClient()..withCredentials = true;
    _client = bc;
  }

  Future<({bool ok, String? username, String? error})> login(
      String username,
      String password,
      ) async {
    final uri = Uri.parse('$kBaseUrl$kLoginPath');
    try {
      //10초내에 응답을 못받으면 종료
      final res = await _client
          .post(
            uri,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: {'username': username, 'password': password},
          )
          .timeout(const Duration(seconds: 10));

      if(res.statusCode == 200) {
        final map = jsonDecode(res.body) as Map<String, dynamic>;
        final name = (map['username'] ?? username).toString();
        return (ok: true, username: name, error: null);
      } else {
        String? msg;
        try {
          final map = jsonDecode(res.body) as Map<String, dynamic>;
          msg = (map['message'] ?? map['error'])?.toString();
        } catch (_) {}
        return (ok: false, username: null, error: msg ?? '로그인에 실패했습니다.');
      }
    } on TimeoutException {
      return (ok: false, username: null, error: '서버 응답이 지연되고 있습니다.');
    } catch(_) {
      return (ok: false, username: null, error: '네트워크 오류가 발생했습니다.');
    }
  }

  Future<void> logout() async {
    final uri = Uri.parse('$kBaseUrl$kLogoutPath');
    try {
      //6초 안에 응답하지 않으면 종료
      await _client.post(uri).timeout(const Duration(seconds: 6));
    } catch (_) {}
  }

  Future<bool> isAuthenticated() async {
    final uri = Uri.parse('$kBaseUrl$kProtectedPath');
    try {
      final res = await _client.get(uri).timeout(const Duration(seconds: 6));
      return res.statusCode == 200;
    } catch(_) {
      return false;
    }
  }
}