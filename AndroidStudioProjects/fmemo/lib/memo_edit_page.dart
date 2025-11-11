import 'package:flutter/material.dart';

import 'memo.dart';
import 'memo_service.dart';

class MemoEditPage extends StatefulWidget {
  final MemoService service;
  final Memo? memo;

  const MemoEditPage({super.key, required this.service, this.memo});

  @override
  State<MemoEditPage> createState() => _MemoEditPageState();
}

class _MemoEditPageState extends State<MemoEditPage> {
  final _controller = TextEditingController();

  @override
  void initState() {
    super.initState();
    if(widget.memo != null) {
      _controller.text = widget.memo!.content;
    }
  }

  Future<void> _save() async {
    final text = _controller.text;
    if(widget.memo == null) {  //추가화면
      await widget.service.createMemo(text);   //서버 insert
    } else {
      await widget.service.updateMemo(widget.memo!.id, text);
    }
    //mounted : State에 내장된 변수, 화면으로 넘어오면 true, 화면이 사라지면 false
    if(mounted) Navigator.pop(context);
  }

  Future<void> _delete() async {
    if(widget.memo == null) return;
    await widget.service.deleteMemo(widget.memo!.id);
    if(mounted) Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    final editing = widget.memo != null;
    return Scaffold(
      appBar: AppBar(title: Text(editing ? '메모 수정' : '메모 추가')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            TextField(
              controller: _controller,
              maxLines: null,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText: '메모 내용을 입력하세요',
              ),
            ),
            const SizedBox(height: 12,),
            Row(
              children: [
                ElevatedButton(onPressed: _save, child: const Text('저장')),
                const SizedBox(width: 8,),
                if(editing)
                  OutlinedButton(onPressed: _delete, child: const Text('삭제')),
              ],
            ),
          ],
        ),
      ),
    );
  }
}