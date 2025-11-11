import 'package:flutter/material.dart';

import 'api_service.dart';

class SignUpPage extends StatefulWidget {
  const SignUpPage({super.key});

  @override
  State<SignUpPage> createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUpPage> {
  final api = ApiService();
  final idCtrl = TextEditingController();
  final pwCtrl = TextEditingController();
  final nameCtrl = TextEditingController();
  final emailCtrl = TextEditingController();
  bool loading = false;

  Future<void> _submit() async {
    final m = Member (
      id: idCtrl.text.trim(),
      password: pwCtrl.text,
      name: nameCtrl.text.trim(),
      email: emailCtrl.text.trim(),
      role: 'user',
    );
    setState(() => loading = true);
    final ok = await api.signUp(m);
    setState(() => loading= false);
    if(ok && mounted) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('회원가입이 완료되었습니다. 로그인 해주세요.')));
      Navigator.pop(context);
    } else {
      if(!mounted) return;
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('회원가입 실패했습니다.')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('회원가입')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          TextField(
            controller: idCtrl,
            decoration: const InputDecoration(labelText: '아이디'),
          ),
          const SizedBox(height: 8,),
          TextField(
            controller: pwCtrl,
            decoration: const InputDecoration(labelText: '비밀번호'),
            obscureText: true,
          ),
          const SizedBox(height: 8,),
          TextField(
            controller: nameCtrl,
            decoration: const InputDecoration(labelText: '이름'),
          ),
          const SizedBox(height: 8,),
          TextField(
            controller: emailCtrl,
            decoration: const InputDecoration(labelText: '이메일'),
          ),
          const SizedBox(height: 16,),
          FilledButton(
            onPressed: loading ? null : _submit,
            child: loading
                ? const CircularProgressIndicator()
                : const Text('가입하기'),
          ),
        ],
      ),
    );
  }
}