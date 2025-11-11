import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class NumberField extends StatelessWidget {
  final TextEditingController controller;
  final String label;
  final String? hint;
  final void Function(String)? onChanged;

  const NumberField({
    super.key,
    required this.controller,
    required this.label,
    this.hint,
    this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: controller,
      keyboardType: TextInputType.number,
      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9.]'))],
      decoration: InputDecoration(
        hintText: hint,
        isDense: true,
        contentPadding: EdgeInsets.zero,
        border: InputBorder.none,
      ),
      onChanged: onChanged,
    );
  }
}