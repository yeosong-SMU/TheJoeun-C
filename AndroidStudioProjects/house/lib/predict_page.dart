import 'dart:async';

import 'package:flutter/material.dart';

import 'api_service.dart';
import 'number_field.dart';

class PricePredictPage extends StatefulWidget {
  const PricePredictPage({super.key});

  @override
  State<PricePredictPage> createState() => _PricePredictPageState();
}

class _PricePredictPageState extends State<PricePredictPage> {
  final TextEditingController _roomsController = TextEditingController();
  final TextEditingController _areaController = TextEditingController();

  String _result = "";
  Timer? _debounce;

  @override
  void initState() {
    super.initState();
    _roomsController.addListener(_onChanged);
    _areaController.addListener(_onChanged);
  }

  @override
  void dispose() {
    _debounce?.cancel();
    _roomsController.dispose();
    _areaController.dispose();
    super.dispose();
  }

  void _onChanged() {
    _debounce?.cancel();
    _debounce = Timer(const Duration(milliseconds: 500), () async {
      if (_isValidInputs()) {
        await _predict();
      } else {
        setState(() => _result = "");
      }
    });
  }

  bool _isValidInputs() {
    final rooms = int.tryParse(_roomsController.text);
    final area = double.tryParse(_areaController.text);
    return rooms != null &&
    rooms >= 0 &&
    rooms <= 10 &&
    area != null &&
    area > 0 &&
    area <= 1000;
  }

  Future<void> _predict() async {
    if (!_isValidInputs()) return;

    final rooms = int.parse(_roomsController.text);
    final area = double.parse(_areaController.text);

    try {
      final pred = await ApiService.predict(rooms: rooms, areaM2: area);
      if(!mounted) return;
      setState(() => _result = pred.toString());
    } catch(e) {
      if (!mounted) return;
      setState(() => _result = "오류: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("주택 가격 예측")),
      body: Padding(
        padding: EdgeInsets.zero,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8),
              child: NumberField(
                controller: _roomsController,
                label: "rooms",
                hint: "방 개수",
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8),
              child: NumberField(
                controller: _areaController,
                label: "area",
                hint: "면적",
              ),
            ),
            const SizedBox(height: 4,),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8),
              child: Text(_result, style: const TextStyle(fontSize: 14)),
            ),
          ],
        ),
      ),
    );
  }
}