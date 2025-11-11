import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import 'config.dart';
import 'product.dart';
import 'product_api.dart';

class ProductFormPage extends StatefulWidget {
  final Product? initial;

  const ProductFormPage({super.key, this.initial});

  @override
  State<ProductFormPage> createState() => _ProductFormPageState();
}

class _ProductFormPageState extends State<ProductFormPage> {
  final _formKey = GlobalKey<FormState>();
  final _name = TextEditingController();
  final _price = TextEditingController(text: '0');
  final _desc = TextEditingController();
  final api = ProductApi();

  String? imageUrl;
  bool saving = false;

  @override
  void initState() {
    super.initState();
    final init = widget.initial;
    if (init != null) {
      _name.text = init.name;
      _price.text = init.price.toString();
      _desc.text = init.description ?? '';
      imageUrl = init.imageUrl;
    }
  }

  @override
  void dispose() {
    _name.dispose();
    _price.dispose();
    _desc.dispose();
    super.dispose();
  }

  Future<void> _pickAndUpload() async {
    final picker = ImagePicker();
    final x = await picker.pickImage(
      source: ImageSource.gallery,
      maxWidth: 1280,
    );
    if(x == null) return;
    final bytes = await x.readAsBytes();
    final url = await api.uploadImage(bytes, x.name);
    setState(() => imageUrl = url);
  }

  Future<void> _save() async {
    if (!_formKey.currentState!.validate()) return;
    final price = double.tryParse(_price.text.trim()) ?? 0;
    final payload = Product(
      id: widget.initial?.id,
      name: _name.text.trim(),
      price: price,
      description: _desc.text.trim().isEmpty ? null : _desc.text.trim(),
      imageUrl: imageUrl,
    );

    setState(() => saving = true);
    try {
      if (widget.initial == null) {
        await api.create(payload);
      } else {
        await api.update(payload);
      }
      if (!mounted) return;
      Navigator.pop(context);
    } finally {
      if (mounted) setState(() => saving = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final img = imageUrl;
    return Scaffold(
      appBar: AppBar(title: Text(widget.initial == null ? '상품 등록' : '상품 수정')),
      body: AbsorbPointer(
        absorbing: saving,
        child: Padding(
          padding: const EdgeInsets.all(8),
          child: Form(
            key: _formKey,
            child: ListView(
              children: [
                TextFormField(
                  controller: _name,
                  decoration: const InputDecoration(labelText: '상품명'),
                  validator: (v) =>
                    (v == null || v.trim().isEmpty) ? '필수 입력입니다.' : null,
                ),
                const SizedBox(height: 8,),
                TextFormField(
                  controller: _price,
                  decoration: const InputDecoration(labelText: '가격'),
                  keyboardType: TextInputType.number,
                ),
                const SizedBox(height: 8,),
                TextFormField(
                  controller: _desc,
                  decoration: const InputDecoration(labelText: '설명'),
                  maxLines: 3,
                ),
                const SizedBox(height: 8,),
                Row(
                  children: [
                    ElevatedButton(
                      onPressed: _pickAndUpload,
                      child: const Text('이미지 업로드'),
                    ),
                    const SizedBox(width: 8,),
                    if(img != null && img.isNotEmpty) const Text('업로드됨'),
                  ],
                ),
                if (img != null && img.isNotEmpty)
                  Padding(
                    padding: const EdgeInsets.only(top: 8),
                    child: Image.network('$apiBase$img', fit: BoxFit.contain),
                  ),

                const SizedBox(height: 12,),
                SizedBox(
                  height: 44,
                  child: ElevatedButton(
                    onPressed: saving ? null : _save,
                    child: Text(saving ? '저장 중...' : '저장'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}