import 'package:flutter/material.dart';

class CalculatorPage extends StatefulWidget {
  const CalculatorPage({super.key});

  @override
  State<CalculatorPage> createState() => _CalculatorPageState();
}

class _CalculatorPageState extends State<CalculatorPage> {
  final _aCtrl = TextEditingController();
  final _bCtrl = TextEditingController();
  double? _result;
  final List<String> _history = [];

  void _calc(String op) {
    final a = double.tryParse(_aCtrl.text);
    final b = double.tryParse(_bCtrl.text);
    if(a == null || b == null) return;
    //State 클래스의 내장함수, 상태를 변경하는 함수
    setState(
      //익명함수
        () {
          switch (op) {
            case '+':
              _result = a + b;
              break;
            case '-':
              _result = a - b;
              break;
            case 'x':
              _result = a * b;
              break;
            case '%':
              if (b != 0) _result = a / b;
              break;
          }
          if(_result != null) {
            _history.insert(0, "$a $op $b = $_result");
          }
        },
    );
  }

  void _clearAll() {
    setState(() {
      _aCtrl.clear();
      _bCtrl.clear();
      _result = null;
      _history.clear();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('계산기'),
        actions: [
          IconButton(onPressed: _clearAll, icon: const Icon(Icons.delete)),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            TextField(
              controller: _aCtrl,
              decoration: const InputDecoration(labelText: 'A'),
            ),
            TextField(
              controller: _bCtrl,
              decoration: const InputDecoration(labelText: 'B'),
            ),
            Wrap(
              spacing: 10,
              children: ['+', '-', 'x', '%']
                .map(
                  (op) => ElevatedButton(
                    onPressed: () => _calc(op),
                    child: Text(op),
                  ),
              )
                .toList(),
            ),
            const SizedBox(height: 16),
            Text("결과: ${_result ?? '-'}"),
            const Divider(),
            Expanded (
              child: ListView(
                children: _history
                    .map(
                  //ListTile 목록용 아이템
                    (h) => ListTile(
                      leading: const Icon(Icons.history),
                      title: Text(h),
                    ),
                )
                    .toList(),
              ),
            ),
          ],
        ),
      ),
    );
  }
}