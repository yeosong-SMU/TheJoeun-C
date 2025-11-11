import 'package:flutter/material.dart';

import 'api_service.dart';
import 'book.dart';
import 'book_form_page.dart';

class BookListPage extends StatefulWidget {
  final ApiService api;

  const BookListPage({super.key, required this.api});

  @override
  State<BookListPage> createState() => _BookListPageState();
}

class _BookListPageState extends State<BookListPage> {
  List<Book> _all = [];
  List<Book> _filtered = [];

  String _criteria = 'title';
  final TextEditingController _searchCtl = TextEditingController();

  bool _loading = false;
  String? _error;

  @override
  void initState() {
    super.initState();
    _load();
  }

  @override
  void dispose() {
    _searchCtl.dispose();
    super.dispose();
  }

  Future<void> _load() async {
    setState(() {
      _loading = true;
      _error = null;
    });
    try {
      final list = await widget.api.list();
      if(!mounted) return;
      setState(() {
        _all = list;
        _applyFilter();
        _loading = false;
      });
    } catch(e) {
      if(!mounted) return;
      setState(() {
        _error = e.toString();
        _loading = false;
      });
    }
  }

  void _applyFilter() {
    final q = _searchCtl.text.trim().toLowerCase();
    if(q.isEmpty) {
      _filtered = List<Book>.from(_all);
      return;
    }
    _filtered = _all.where((b) {
      switch (_criteria) {
        case 'author':
          return (b.author).toLowerCase().contains(q);
        case 'genre':
          return (b.genre).toLowerCase().contains(q);
        case 'title':
        default:
          return (b.title).toLowerCase().contains(q);
      }
    }).toList();
  }

  Future<void> _delete(int id) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('삭제 확인'),
        content: Text('이 도서를 정말 삭제하시겠습니까?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('취소'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            child: const Text('삭제'),
          ),
        ],
      ),
    );

    if(confirmed == true) {
      await widget.api.delete(id);
      if(!mounted) return;
      await _load();
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('삭제되었습니다.')));
    }
  }

  Future<void> _openForm([Book? b]) async {
    final changed = await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => BookFormPage(api: widget.api, book: b),
      ),
    );
    if (!mounted) return;
    if (changed == true) {
      await _load();
    }
  }

  @override
  Widget build(BuildContext context) {
    final body = _loading
        ? const Center(child: CircularProgressIndicator())
        : (_error != null)
        ? Center (
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text('오류: $_error'),
              const SizedBox(height: 12,),
              ElevatedButton(onPressed: _load, child: const Text('다시 시도')),
            ],
          ),
        )
        : _filtered.isEmpty
        ? Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text('검색 결과가 없습니다.'),
              const SizedBox(height: 12,),
              ElevatedButton(
                onPressed: () {
                  _searchCtl.clear();
                  setState(() {
                    _applyFilter();
                  });
                },
                child: const Text('검색 초기화'),
              ),
            ],
          ),
        )
        : ListView.separated(
          physics: const AlwaysScrollableScrollPhysics(),
          itemCount: _filtered.length,
          separatorBuilder: (_, __) => const Divider(height: 1,),
          itemBuilder: (_, i) {
            final b = _filtered[i];
            return ListTile(
              title: Text(b.title),
              subtitle: Text('${b.author} / ${b.genre}'),
              trailing: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  IconButton(
                    tooltip: '수정',
                    icon: const Icon(Icons.edit),
                    onPressed: () => _openForm(b),
                  ),
                  IconButton(
                    tooltip: '삭제',
                    icon: const Icon(Icons.delete),
                    onPressed: () => _delete(b.id!),
                  ),
                ],
              ),
            );
          },
        );

    return Scaffold(
      appBar: AppBar(title: const Text('도서 목록')),
      body: RefreshIndicator(
        onRefresh: _load,
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(12, 12, 12, 6),
              child: Row(
                children: [
                  DropdownButton<String>(
                    value: _criteria,
                    items: const[
                      DropdownMenuItem(value: 'title', child: Text('제목')),
                      DropdownMenuItem(value: 'author', child: Text('저자')),
                      DropdownMenuItem(value: 'genre', child: Text('장르')),
                    ],
                    onChanged: (v) {
                      if(v == null) return;
                      setState(() {
                        _criteria = v;
                        _applyFilter();
                      });
                    },
                  ),
                  const SizedBox(width: 8,),
                  Expanded(
                    child: TextField(
                      controller: _searchCtl,
                      decoration: InputDecoration(
                        hintText: '검색어를 입력하세요',
                        isDense: true,
                        suffixIcon: _searchCtl.text.isEmpty
                            ? null
                            : IconButton(
                              icon: const Icon(Icons.clear),
                              onPressed: () {
                                _searchCtl.clear();
                                setState(_applyFilter);
                              },
                            ),
                      ),
                      onChanged: (v) => setState(_applyFilter),
                      onSubmitted: (v) => setState(_applyFilter),
                    ),
                  ),
                  const SizedBox(width: 8,),
                  ElevatedButton(
                    onPressed: () => setState(_applyFilter),
                    child: const Text('검색'),
                  ),
                ],
              ),
            ),
            const Divider(height: 1,),
            Expanded(child: body),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _openForm(),
        child: const Icon(Icons.add),
      ),
    );
  }
}