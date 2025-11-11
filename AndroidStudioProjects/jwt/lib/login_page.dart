import 'package:flutter/material.dart';

import 'api_client.dart';

class LoginPage extends StatefulWidget {
  final ApiClient api;
  final void Function(String username) onLoggedIn;

  const LoginPage({super.key, required this.api, required this.onLoggedIn});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final idCtrl = TextEditingController(text: '');
  final pwCtrl = TextEditingController(text: '');
  bool working = false;
  String? errorText;

  Future<void> _submit() async {
    setState(() {
      working = true;
      errorText = null;
    });
    try {
      final r = await widget.api.login(idCtrl.text.trim(), pwCtrl.text);
      if(r.ok) {
        widget.onLoggedIn(r.username ?? idCtrl.text.trim());
      } else {
        setState(() => errorText = r.error ?? '로그인에 실패했습니다.');
      }
    } finally {
      if (mounted) setState(() => working = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final spacing = const SizedBox(height: 12,);
    return Scaffold(
      appBar: AppBar(title: const Text('로그인')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            TextField(
              controller: idCtrl,
              decoration: const InputDecoration(labelText: '아이디'),
            ),
            spacing,
            TextField(
              controller: pwCtrl,
              decoration: const InputDecoration(labelText: '비밀번호'),
              obscureText: true,  //암호 마스킹
            ),
            spacing,
            if(errorText != null)
              Text(errorText!, style: const TextStyle(color: Colors.red),),
            spacing,
            ElevatedButton(
              onPressed: working ? null : _submit,
              child: Text(working ? '로그인 중...' : '로그인'),
            ),
          ],
        ),
      ),
    );
  }
}