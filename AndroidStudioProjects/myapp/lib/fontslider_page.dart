import 'package:flutter/material.dart';

class FontSliderPage extends StatefulWidget {
  const FontSliderPage({super.key});

  @override
  State<FontSliderPage> createState() => _FontSliderPageState();
}

class _FontSliderPageState extends State<FontSliderPage> {
  double _size = 24;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('슬라이더로 글자 크기 조절')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Slider(
              min: 12,
              max: 64,
              divisions: 52,
              value: _size,
              label: _size.toStringAsFixed(0),
              onChanged: (v) => setState(() => _size = v),
            ),
            const SizedBox(height: 8),
            Expanded(
              child: Center(
                child: Text('크기를 조절해 보세요', style: TextStyle(fontSize: _size)),
              ),
            ),
          ],
        ),
      ),
    );
  }
}