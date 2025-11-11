import 'package:flutter/material.dart';

import 'config.dart';
import 'product.dart';
import 'product_api.dart';
import 'product_form_page.dart';

class ProductListPage extends StatefulWidget {
  const ProductListPage({super.key});

  @override
  State<ProductListPage> createState() => _ProductListPageState();
}

class _ProductListPageState extends State<ProductListPage> {
  final api = ProductApi();
  List<Product> items = [];
  bool loading = true;
  String? error;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    setState(() {
      loading = true;
      error = null;
    });
    try {
      items = await api.list();
    } catch (e) {
      error = e.toString();
    } finally {
      if (mounted) setState(() => loading = false);
    }
  }

  Future<void> _delete(Product p) async {
    final ok = await showDialog<bool>(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('삭제'),
        content: Text('"${p.name}" 삭제하시겠습니까?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('취소'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, true),
            child: const Text('삭제'),
          ),
        ],
      ),
    );
    if (ok == true) {
      await api.delete(p.id!);
      await _load();
    }
  }

  @override
  Widget build(BuildContext context) {
    Widget body;
    if (loading) {
      body = const Center(child: CircularProgressIndicator());
    } else if (error != null) {
      body = Center(child: Text('오류: $error'));
    } else {
      body = RefreshIndicator(
          onRefresh: _load,
          child: ListView.builder(
              itemCount: items.length,
              itemBuilder: (c, i) {
                final it = items[i];
                return ListTile(
                  leading: (it.imageUrl != null && it.imageUrl!.isNotEmpty)
                      ? Image.network(
                        '$apiBase${it.imageUrl}',
                        width: 48,
                        height: 48,
                        fit: BoxFit.cover,
                      )
                      : const Icon(Icons.image_not_supported),
                  title: Text(it.name),
                  subtitle: Text('${it.price.toStringAsFixed(0)}'),
                  onTap: () async {
                    await Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => ProductFormPage(initial: it,),
                      ),
                    );
                    if (mounted) _load();
                  },
                  trailing: IconButton(
                    icon: const Icon(Icons.delete_outline),
                    onPressed: it.id == null ? null : () => _delete(it),
                  ),
                );
              },
          ),
      );
    }

    return Scaffold(
      appBar: AppBar(title: const Text('상품 목록')),
      body: body,
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.push(
            context,
            MaterialPageRoute(builder: (_) => const ProductFormPage()),
          );
          if (mounted) _load();
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}