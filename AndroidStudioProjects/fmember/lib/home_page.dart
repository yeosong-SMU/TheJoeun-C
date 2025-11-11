import 'package:flutter/material.dart';

import 'api_service.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final api = ApiService();
  String? loginId;
  String? loginName;
  String? role;
  bool loading = true;

  @override
  void initState() {
    super.initState();
    _loadSession();
  }

  Future<void> _loadSession() async {
    setState(() => loading = true);
    try {
      final s = await api.session();
      setState(() {
        loginId = s['loginId']?.toString();
        loginName = s['loginName']?.toString();
        role = s['role']?.toString().toUpperCase();
      });
    } finally {
      if(mounted) setState(() => loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final loggedIn = loginId != null;

    return Scaffold(
      appBar: AppBar(
        title: const Text('홈'),
        actions: [
          IconButton(icon: const Icon(Icons.refresh), onPressed: _loadSession),
        ],
      ),
      body: loading
        ? const Center(child: CircularProgressIndicator())
        : Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              if(loggedIn) ...[
                // ...[] 위젯 리스트를 풀어서 Column의 children 안에 세팅
                Text('안녕하세요, ${loginName ?? ''} 님 (${role ?? 'user'})'),
                const SizedBox(height: 12,),
                if(role == 'admin')
                  FilledButton(
                    onPressed: () {
                      Navigator.pushNamed(context, '/members').then((_) => _loadSession());
                    },
                    child: const Text('회원 목록'),
                  ),
                const SizedBox(height: 8,),
                FilledButton.tonal(
                  onPressed: () async {
                    final ok = await api.logout();
                    if(!mounted) return;
                    if(ok) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('로그아웃되었습니다.')),
                      );
                      _loadSession();
                    }
                  },
                  child: const Text('로그아웃'),
                ),
              ] else ...[
                const Text('로그인 후 이용해주세요.'),
                const SizedBox(height: 12,),
                FilledButton(
                  onPressed: (){
                    Navigator.pushNamed(
                      context, '/login',
                    ).then((_) => _loadSession());
                  },
                  child: const Text('로그인'),
                ),
                const SizedBox(height: 8,),
                TextButton(
                    onPressed: (){
                      Navigator.pushNamed(context, '/signup');
                    },
                    child: const Text('회원가입'),
                ),
              ],
            ],
          ),
        ),
    );
  }
}