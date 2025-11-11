import 'package:flutter/material.dart';

import 'address_book.dart';
import 'api_service.dart';

class AddressFormPage extends StatefulWidget {
  final ApiService api;
  final AddressBook? initial;

  const AddressFormPage({
    super.key,
    required this.api,
    this.initial
  });

  @override
  State<AddressFormPage> createState() => _AddressFormPageState();
}

class _AddressFormPageState extends State<AddressFormPage> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _name;
  late TextEditingController _phone;
  late TextEditingController _email;
  late TextEditingController _address;
  bool _saving = false;

  @override
  void initState() {
    super.initState();
    _name = TextEditingController(text: widget.initial?.name ?? '');
    _phone = TextEditingController(text: widget.initial?.phone ?? '');
    _email = TextEditingController(text: widget.initial?.email ?? '');
    _address = TextEditingController(text: widget.initial?.address ?? '');
  }

  @override
  void dispose() {
    _name.dispose();
    _phone.dispose();
    _email.dispose();
    _address.dispose();
    super.dispose();
  }

  Future<void> _save() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _saving = true;
    });

    final ab = AddressBook(
      id: widget.initial?.id,
      name: _name.text.trim(),
      phone: _phone.text.trim(),
      email: _email.text.trim(),
      address: _address.text.trim(),
    );

    try {
      if (ab.id == null) {
        await widget.api.create(ab);
      } else {
        await widget.api.update(ab);
      }
      if(!mounted) return;
      Navigator.pop(context, true);
    } catch (e) {
      if(!mounted) return;
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('저장 실패: $e')));
      setState(() {
        _saving = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final isEdit = widget.initial != null;
    return Scaffold(
      appBar: AppBar(title: Text(isEdit ? '주소록 수정' : '주소록 추가')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                controller: _name,
                decoration: const InputDecoration(labelText: '이름 *'),
                validator: (v) =>
                  v == null || v.trim().isEmpty ? '이름을 입력하세요.' : null,
              ),
              TextFormField(
                controller: _phone,
                decoration: const InputDecoration(labelText: '전화번호'),
                keyboardType: TextInputType.phone,
              ),
              TextFormField(
                controller: _email,
                decoration: const InputDecoration(labelText: '이메일'),
                keyboardType: TextInputType.emailAddress,
              ),
              TextFormField(
                controller: _address,
                decoration: const InputDecoration(labelText: '주소'),
                maxLines: 2,
              ),
              const SizedBox(height: 16,),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                    onPressed: _saving ? null : _save,
                    child: Text(_saving ? '저장 중...' : '저장')
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}