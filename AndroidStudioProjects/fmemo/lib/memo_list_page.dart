import 'package:flutter/material.dart';

import 'memo.dart';
import 'memo_edit_page.dart';
import 'memo_service.dart';

class MemoListPage extends StatefulWidget {
  final MemoService service;
  
  const MemoListPage({super.key, required this.service});
  
  @override
  State<MemoListPage> createState() => _MemoListPageState();
}

class _MemoListPageState extends State<MemoListPage> {
  List<Memo> memos = [];
  
  @override
  void initState() {
    super.initState();
    _load();
  }
  
  Future<void> _load() async {
    final list = await widget.service.fetchMemos();
    setState(() => memos = list);
  }
  
  Future<void> _goToEdit(Memo? memo) async {
    await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => MemoEditPage(service: widget.service, memo: memo),
      ),
    );
    await _load();
  }
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('메모장')),
      body: ListView.builder(
          itemCount: memos.length,
          itemBuilder: (_, i) {
            final m = memos[i];
            return ListTile(
              title: Text(
                m.content,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
              ),
              subtitle: m.createdAt != null
                ? Text(m.createdAt!.toLocal().toString())
                  : null,
              onTap: () => _goToEdit(m),
            );
          },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _goToEdit(null),
        child: const Icon(Icons.add),
      ),
    );
  }
}