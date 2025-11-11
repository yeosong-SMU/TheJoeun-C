import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'api.dart';

class TodoDetailPage extends StatefulWidget {
  final int todoId;

  const TodoDetailPage({super.key, required this.todoId});

  @override
  State<TodoDetailPage> createState() => _TodoDetailPageState();
}

class _TodoDetailPageState extends State<TodoDetailPage> {
  final _textCtrl = TextEditingController();
  bool _done = false;
  bool _loading = true;
  bool _saving = false;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    setState(() => _loading = true);
    try {
      final res = await http.get(Api.u('/api/todos/${widget.todoId}'));
      if(res.statusCode == 200) {
        final j = jsonDecode(res.body) as Map<String, dynamic>;
        _textCtrl.text = (j['text'] ?? '') as String;
        _done = (j['done'] ?? false) as bool;
      } else {
        _snack('조회 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('조회 에러: $e');
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<void> _save() async {
    final text = _textCtrl.text.trim();
    if(text.isEmpty) {
      _snack('내용을 입력해 주세요.');
      return;
    }
    setState(() => _saving = true);
    try {
      final res = await http.put(
        Api.u('/api/todos/${widget.todoId}'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'text': text, 'done': _done}),
      );
      if(res.statusCode == 200) {
        if(!mounted) return;
        _snack('수정 완료');
        Navigator.pop(context, true);
      } else {
        _snack('수정 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('수정 에러: $e');
    } finally {
      if (mounted) setState(() => _saving = false);
    }
  }

  Future<void> _delete() async {
    final ok = await showDialog<bool>(
      context: context,
      builder: (c) => AlertDialog(
        title: const Text('삭제하시겠습니까?'),
        content: const Text('이 작업은 되돌릴 수 없습니다.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(c, false),
            child: const Text('취소'),
          ),
          FilledButton(
            onPressed: () => Navigator.pop(c, true),
            child: const Text('삭제')
          ),
        ],
      ),
    );
    if(ok != true) return;

    try {
      final res = await http.delete(Api.u('/api/todos/${widget.todoId}'));
      if(res.statusCode == 204 || res.statusCode == 200) {
        if(!mounted) return;
        Navigator.pop(context, true);
      } else {
        _snack('삭제 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('삭제 에러: $e');
    }
  }

  void _snack(String msg) {
    if(!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg),));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('상세 #${widget.todoId}'),
        actions: [
          TextButton(
            onPressed: _saving ? null : _save,
            child: const Text('저장')
          ),
          IconButton(
            onPressed: _delete,
            tooltip: '삭제',
            icon: const Icon(Icons.delete),
          ),
        ],
      ),
      body: _loading
        ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          children: [
            TextField(
              controller: _textCtrl,
              decoration: const InputDecoration(
                hintText: '내용을 입력하세요',
                border: UnderlineInputBorder(),
              ),
            ),
            const SizedBox(height: 8,),
            CheckboxListTile(
              value: _done,
              onChanged: (v) => setState(() => _done = (v ?? false)),
              title: const Text('완료'),
              controlAffinity: ListTileControlAffinity.leading,
            ),
          ],
        ),
      ),
    );
  }
}