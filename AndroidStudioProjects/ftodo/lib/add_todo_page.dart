import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'api.dart';

class AddTodoPage extends StatefulWidget {
  const AddTodoPage({super.key});

  @override
  State<AddTodoPage> createState() => _AddTodoPageState();
}

class _AddTodoPageState extends State<AddTodoPage> {
  final _ctrl = TextEditingController();
  bool _saving = false;

  Future<void> _submit() async {
    final text = _ctrl.text.trim();
    if(text.isEmpty) {
      _snack('내용을 입력해 주세요.');
      return;
    }
    setState(() => _saving = true);
    try {
      final res = await http.post(
        Api.u('/api/todos'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'text' : text}),
      );
      if(!mounted) return;
      if(res.statusCode == 201 || res.statusCode == 200) {
        Navigator.pop(context, true);
      } else {
        _snack('생성 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('생성 에러: $e');
    } finally {
      if (mounted) setState(() => _saving = false);
    }
  }

  void _snack(String msg) {
    if(!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
          child: TextField(
            controller: _ctrl,
            autofocus: true,
            minLines: 1,
            maxLines: null,
            textInputAction: TextInputAction.done,
            onSubmitted: (_) => _submit(),
            decoration: const InputDecoration.collapsed(
              hintText: '할 일 내용을 입력하세요',
            ),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: _saving ? null : _submit,
        icon: _saving ? null : const Icon(Icons.save),
        label: _saving
          ? const SizedBox(
            width: 16,
            height: 16,
            child: CircularProgressIndicator(strokeWidth: 2,),
          )
          : const Text('저장'),
      ),
    );
  }
}