import 'package:flutter/material.dart';

import 'stroke.dart';

class DrawCanvas extends StatefulWidget {
  const DrawCanvas({
    super.key,
    required this.width,
    required this.height,
    required this.strokes,
    this.penColor = Colors.black,
    this.penWidth = 18,
  });

  final double width, height;
  final List<Stroke> strokes;
  final Color penColor;
  final double penWidth;

  @override
  State<DrawCanvas> createState() => _DrawCanvasState();
}

class _DrawCanvasState extends State<DrawCanvas> {
  @override
  Widget build(BuildContext context) => Container(
    width: widget.width,
    height: widget.height,
    decoration: BoxDecoration(
      color: Colors.white,
      border: Border.all(color: Colors.black12),
      borderRadius: BorderRadius.circular(12),
    ),
    child: GestureDetector(
      //처음 터치
      onPanStart: (d) =>
        setState(() => widget.strokes.add(Stroke([d.localPosition]))),
      //손가락을 움직일 때
      onPanUpdate: (d) =>
        setState(() => widget.strokes.last.points.add(d.localPosition)),
      child: CustomPaint(
        painter: _Painter(widget.strokes, widget.penColor, widget.penWidth),
        size: Size(widget.width, widget.height),
      ),
    ),
  );
}

class _Painter extends CustomPainter {
  _Painter(this.strokes, this.color, this.w);

  final List<Stroke> strokes;
  final Color color;
  final double w;

  @override
  void paint(Canvas c, Size s) {
    // .. 여러 속성을 연속적으로 지정
    final p = Paint()
        ..color = color
        ..strokeWidth = w
        ..strokeCap = StrokeCap.round
        ..strokeJoin = StrokeJoin.round
        ..style = PaintingStyle.stroke;
    for (final s in strokes) {
      for (int i = 0; i < s.points.length - 1; i++) {
        c.drawLine(s.points[i], s.points[i + 1], p);
      }
    }
  }

  @override
  //bool shouldRepaint(convariant _Painter o) => true;

  bool shouldRepaint(covariant _Painter o) => true;
}