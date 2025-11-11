import 'package:flutter/material.dart';

import 'api_service.dart';
import 'book.dart';

class BookFormPage extends StatefulWidget {
  final ApiService api;
  final Book? book;

  const BookFormPage({super.key, required this.api, this.book});

  @override
  State<BookFormPage> createState() => _BookFormPageState();
}

class _BookFormPageState extends State<BookFormPage> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _title;
  late TextEditingController _author;
  late TextEditingController _genre;

  @override
  void initState() {
    super.initState();
    _title = TextEditingController(text: widget.book?.title ?? '');
    _author = TextEditingController(text: widget.book?.author ?? '');
    _genre = TextEditingController(text: widget.book?.genre ?? '');
  }

  Future<void> _save() async {
    if(!_formKey.currentState!.validate()) return;
    final b = Book (
      id: widget.book?.id,
      title: _title.text,
      author: _author.text,
      genre: _genre.text,
    );
    try {
      if(b.id == null) {
        await widget.api.create(b);
      } else {
        await widget.api.update(b);
      }
      if(!mounted) return;
      Navigator.pop(context, true);
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('저장 실패: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    final isEdit = widget.book != null;
    return Scaffold(
      appBar: AppBar(title: Text(isEdit ? '도서 수정' : '도서 추가')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                controller: _title,
                decoration: const InputDecoration(labelText: '제목'),
                validator: (v) => v!.isEmpty ? '필수입력' : null,
              ),
              TextFormField(
                controller: _author,
                decoration: const InputDecoration(labelText: '저자'),
                validator: (v) => v!.isEmpty ? '필수입력' : null,
              ),
              TextFormField(
                controller: _genre,
                decoration: const InputDecoration(labelText: '장르'),
                validator: (v) => v!.isEmpty ? '필수입력' : null,
              ),
              const SizedBox(height: 16,),
              ElevatedButton(onPressed: _save, child: const Text('저장')),
            ],
          ),
        ),
      ),
    );
  }
}