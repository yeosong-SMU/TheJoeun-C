import 'dart:io';

void main() {
  List<int> numbers = [];

  for(int i = 1; i <= 5; i++) {
    stdout.write("$i 번째 숫자 입력: ");
    numbers.add(int.parse(stdin.readLineSync()!));
  }

  //reduce 리스트의 모든 원소를 하나로 합치는 함수
  double avg = numbers.reduce((a, b) => a + b) / numbers.length;
  print("평균은 ${avg.toStringAsFixed(2)} 입니다.");
}