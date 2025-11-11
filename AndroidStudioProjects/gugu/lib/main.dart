import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// 시작점, 플러터 앱 시작
// => 화살표 함수 {} 대체
// 시작화면 Gugu 클래스
void main() => runApp(const MaterialApp(home: Gugu()));

// super.key 기존 위젯인지 새로운 위젯인지 구분하는 값
class Gugu extends StatefulWidget {
  const Gugu({super.key});

  // _로 시작 : private
  @override
  State<Gugu> createState() => _GuguState();
}

class _GuguState extends State<Gugu> {
  // 텍스트 입력값 참조 객체
  final c = TextEditingController();
  List<String> r = const [];

  void gen() {
    final n = int.tryParse(c.text.trim());
    setState(() {
      r = n ==null
          ? const ['정수를 입력하세요!']
          : List.generate(9, (i) => '$n x ${i + 1}');
    });
  }

  // 위젯이 제거될 때 호출, 메모리 정리
  @override
  void dispose() {
    c.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => Scaffold( // 앱의 뼈대
    appBar: AppBar(title: const Text('구구단')),
    body: Padding(
      //상하좌우 패딩 설정
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [TextField(
          controller: c,
          keyboardType: TextInputType.number,
          inputFormatters: [FilteringTextInputFormatter.digitsOnly],
            decoration: const InputDecoration(
              labelText: '단 입력 (예: 7)',
              border: OutlineInputBorder(),
            ),
          ),
          const SizedBox(height: 12),
          //버튼 클릭하면 gen() 함수 호출
          ElevatedButton(onPressed: gen, child: const Text('구구단 출력')),
          const SizedBox(height: 16),
          //남은 화면을 리스트뷰가 꽉 채움
          Expanded(
            child: ListView.builder(
              itemCount: r.length,
              // (BuildContext, 인덱스)
              itemBuilder: (_, i) => ListTile(title: Text(r[i])),
            ),
          ),
        ],
      ),
    ),
  );
}