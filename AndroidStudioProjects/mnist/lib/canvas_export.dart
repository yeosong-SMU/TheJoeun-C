import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

//pixelRatio = 2.0 픽셀값을 2배로 설정
Future<Uint8List> capturePng(GlobalKey key, {double pixelRatio = 2.0}) async {
  final b = key.currentContext!.findRenderObject() as RenderRepaintBoundary;
  final img = await b.toImage(pixelRatio: pixelRatio);
  final data = await img.toByteData(format: ui.ImageByteFormat.png);
  return data!.buffer.asUint8List();
}