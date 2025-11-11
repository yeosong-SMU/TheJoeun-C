import 'package:flutter/material.dart';

import 'address_book.dart';
import 'address_form_page.dart';
import 'api_service.dart';

class AddressListPage extends StatefulWidget {
  final ApiService api;

  const AddressListPage({super.key, required this.api});

  @override
  State<AddressListPage> createState() => _AddressListPageState();
}

class _AddressListPageState extends State<AddressListPage> {
  List<AddressBook> _all = [];
  List<AddressBook> _filtered = [];

  String _criteria = 'name';
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
      if (!mounted) return;
      setState(() {
        _all = list;
        _applyFilter();
        _loading = false;
      });
    } catch (e) {
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
      _filtered = List<AddressBook>.from(_all);
      return;
    }
    _filtered = _all.where((ab) {
      switch (_criteria) {
        case 'phone':
          return ab.phone.toLowerCase().contains(q);
        case 'email':
          return ab.email.toLowerCase().contains(q);
        case 'address':
          return ab.address.toLowerCase().contains(q);
        case 'name':
        default:
          return ab.name.toLowerCase().contains(q);
      }
    }).toList();
  }

  Future<void> _confirmAndDelete(int id, String name) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('삭제 확인'),
        content: Text('"$name" 항목을 삭제하시겠습니까?'),
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

  Future<void> _openForm([AddressBook? ab]) async {
    final changed = await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => AddressFormPage(api: widget.api, initial: ab),
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
                  setState(_applyFilter);
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
            final ab = _filtered[i];
            return ListTile(
              title: Text(ab.name),
              subtitle: Text(_buildSub(ab)),
              trailing: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  IconButton(
                    tooltip: '수정',
                    icon: const Icon(Icons.edit),
                    onPressed: () => _openForm(ab),
                  ),
                  IconButton(
                    tooltip: '삭제',
                    icon: const Icon(Icons.delete),
                    onPressed: () => _confirmAndDelete(ab.id!, ab.name),
                  ),
                ],
              ),
            );
          },
        );

    return Scaffold(
      appBar: AppBar(title: const Text('주소록')),
      body: RefreshIndicator(
        onRefresh: _load,
        child: Column(
          children: [
            //검색바
            Padding(
              padding: const EdgeInsets.fromLTRB(12, 12, 12, 6),
              child: Row(
                children: [
                  DropdownButton<String>(
                    value: _criteria,
                    items: const[
                      DropdownMenuItem(value: 'name', child: Text('이름')),
                      DropdownMenuItem(value: 'phone', child: Text('전화')),
                      DropdownMenuItem(value: 'email', child: Text('이메일')),
                      DropdownMenuItem(value: 'address', child: Text('주소')),
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
                        hintText: '검색어',
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

  String _buildSub(AddressBook ab) {
    final parts = <String>[];
    if (ab.phone.isNotEmpty) parts.add(ab.phone);
    if (ab.email.isNotEmpty) parts.add(ab.email);
    if (ab.address.isNotEmpty) parts.add(ab.address);
    return parts.isEmpty ? '-' : parts.join(' / ');
  }
}