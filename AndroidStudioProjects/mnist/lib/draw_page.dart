import 'package:flutter/material.dart';

import 'api_service.dart';
import 'canvas_export.dart';
import 'draw_canvas.dart';
import 'prediction.dart';
import 'stroke.dart';

class DrawPage extends StatefulWidget {
  const DrawPage({super.key});

  @override
  State<DrawPage> createState() => _DrawPageState();
}

class _DrawPageState extends State<DrawPage> {
  final keyCanvas = GlobalKey();
  final strokes = <Stroke>[];
  bool loading = false;
  Prediction? pred;
  String? error;

  void _clear() => setState(() {
    strokes.clear();
    pred = null;
    error = null;
  });

  Future<void> _predict() async {
    setState(() {
      loading = true;
      error = null;
    });
    try {
      final png = await capturePng(keyCanvas);
      pred = await ApiService.instance.predictDigit(png);
    } catch (e) {
      error = e.toString();
    }
    if(mounted) setState(() => loading = false);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("MNIST")),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              RepaintBoundary(
                key: keyCanvas,
                child: DrawCanvas(width: 280, height: 280, strokes: strokes),
              ),
              const SizedBox(height: 12,),
              Wrap(
                spacing: 10,
                children: [
                  FilledButton.icon(
                    onPressed: _clear,
                    icon: const Icon(Icons.clear),
                    label: const Text("지우기"),
                  ),
                  FilledButton.icon(
                    onPressed: loading ? null : _predict,
                    icon: const Icon(Icons.send),
                    label: const Text("예측"),
                  ),
                ],
              ),
              const SizedBox(height: 16,),
              if(loading) const CircularProgressIndicator(),
              if(error != null)
                Text("에러: $error", style: const TextStyle(color: Colors.red)),
              if (pred != null && error == null)
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      children: [
                        Text(
                          "예측 결과: ${pred!.prediction}",
                          style: const TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 8,),
                        const Text("Top-3 확률"),
                        for(final t in pred!.top3)
                          Text("${t.digit} : ${t.prob.toStringAsFixed(2)}"),
                      ],
                    ),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }
}