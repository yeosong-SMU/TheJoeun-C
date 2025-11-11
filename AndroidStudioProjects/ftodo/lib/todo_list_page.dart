import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'add_todo_page.dart';
import 'api.dart';
import 'todo.dart';
import 'todo_detail_page.dart';

class TodoListPage extends StatefulWidget {
  const TodoListPage({super.key});

  @override
  State<TodoListPage> createState() => _TodoListPageState();
}

class _TodoListPageState extends State<TodoListPage> {
  List<Todo> items = [];
  bool loading = false;
  bool bulkDeleting = false;
  TodoFilter filter = TodoFilter.all;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    setState(() => loading = true);
    try {
      late Uri uri;
      if (filter == TodoFilter.all) {
        uri = Api.u('/api/todos');
      } else if(filter == TodoFilter.notDone) {
        uri = Api.u('/api/todos?done=false');
      } else {
        uri = Api.u('/api/todos?done=true');
      }

      final res = await http.get(uri);
      if(res.statusCode == 200) {
        final list = (jsonDecode(res.body) as List)
            .map((e) => Todo.fromJson(e))
            .toList();
        setState(() => items = list);
      } else {
        _snack('목록 실패: ${res.statusCode}');
      }
    } catch(e) {
      _snack('목록 에러: $e');
    } finally {
      if(mounted) setState(() => loading = false);
    }
  }

  Future<void> _toggle(Todo t) async {
    try {
      final res = await http.patch(Api.u('/api/todos/${t.id}/toggle'));
      if(res.statusCode == 200) {
        setState(() => t.done = !t.done);
      } else {
        _snack('토글 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('토글 에러: $e');
    }
  }

  Future<void> _deleteOne(Todo t) async {
    final ok = await _confirm('삭제하시겠습니까?', ' 「${t.text}」 항목이 삭제됩니다.');
    if(ok != true) return;

    try {
      final res = await http.delete(Api.u('/api/todos/${t.id}'));
      if(res.statusCode == 204 || res.statusCode == 200) {
        if(!mounted) return;
        setState(() => items.removeWhere((e) => e.id == t.id));
        _snack('삭제되었습니다.');
      } else {
        _snack('삭제 실패: ${res.statusCode}');
      }
    } catch (e) {
      _snack('삭제 에러: $e');
    }
  }

  Future<void> _deleteCompleted() async {
    final targets = items.where((e) => e.done).toList();
    if(targets.isEmpty) {
      _snack('완료된 항목이 없습니다.');
      return;
    } final ok = await _confirm(
      '완료 항목 일괄 삭제',
      '완료된 ${targets.length}건을 삭제하시겠습니까?',
    );
    if(ok != true) return;

    setState(() => bulkDeleting = true);
    int success = 0, fail = 0;
    for(final t in targets) {
      try {
        final res = await http.delete(Api.u('/api/todos/${t.id}'));
        if(res.statusCode == 204 || res.statusCode == 200) {
          success++;
        } else {
          fail++;
        }
      } catch(_) {
        fail++;
      }
    }
    if(!mounted) return;
    setState(() {
      items.removeWhere((e) => e.done);
      bulkDeleting = false;
    });
    _snack('삭제 완료: $success건' + (fail > 0 ? ', 실패 $fail건' : ''));
  }

  Future<void> _goAdd() async {
    final created = await Navigator.push<bool> (
      context,
      MaterialPageRoute(builder: (_) => const AddTodoPage()),
    );
    if(created == true) _load();
  }

  Future<void> _goDetail(Todo t) async {
    final changed = await Navigator.push<bool>(
      context,
      MaterialPageRoute(builder: (_) => TodoDetailPage(todoId: t.id)),
    );
    if(changed == true) _load();
  }

  Future<bool?> _confirm(String title, String body) {
    return showDialog<bool> (
      context: context,
      builder: (c) => AlertDialog(
        title: Text(title),
        content: Text(body),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(c, false),
            child: const Text('취소'),
          ),
          FilledButton(
            onPressed: () => Navigator.pop(c, true),
            child: const Text('확인'),
          ),
        ],
      ),
    );
  }

  void _snack(String msg) {
    if(!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg)));
  }

  @override
  Widget build(BuildContext context) {
    final hasDone = items.any((e) => e.done);

    return Scaffold(
      appBar: AppBar(
        title: const Text('할 일'),
        actions: [
          PopupMenuButton<TodoFilter>(
            tooltip: '필터',
            initialValue: filter,
            onSelected: (f) {
              setState(() => filter = f);
              _load();
            },
            itemBuilder: (c) => const [
              PopupMenuItem(value: TodoFilter.all, child: Text('전체')),
              PopupMenuItem(value: TodoFilter.notDone, child: Text('미완료')),
              PopupMenuItem(value: TodoFilter.done, child: Text('완료')),
            ],
            icon: const Icon(Icons.filter_list),
          ),
          IconButton(
            tooltip: '완료 항목 일괄 삭제',
            onPressed: (!hasDone || bulkDeleting) ? null : _deleteCompleted,
            icon: const Icon(Icons.delete_sweep),
          ),
          IconButton(
            onPressed: _load,
            tooltip: '새로고침',
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),

      floatingActionButton: FloatingActionButton(
        onPressed: _goAdd,
        child: const Icon(Icons.add),
      ),

      body: loading
        ? const Center(child: CircularProgressIndicator())
          : RefreshIndicator(
            onRefresh: _load,
            child: ListView.separated(
              itemCount: items.length,
                separatorBuilder: (_, __) => const Divider(height: 1,),
                itemBuilder: (c, i) {
                  final t = items[i];
                  return ListTile(
                    dense: true,
                    contentPadding: const EdgeInsets.symmetric(horizontal: 12),
                    leading: Checkbox(
                      value: t.done,
                      onChanged: (_) => _toggle(t),
                    ),
                    title: Text(
                      t.text,
                      overflow: TextOverflow.ellipsis,
                      style: TextStyle(
                        decoration: t.done ? TextDecoration.lineThrough : null,
                        color: t.done ? Colors.grey : null,
                      ),
                    ),
                    onTap: () => _goDetail(t),
                    onLongPress: () => _deleteOne(t),
                  );
                },
            ),
          ),
    );
  }
}