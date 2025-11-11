//dart 표준 라이브러리 중에서 io 패키지 로딩
import 'dart:io';

void main() {
  stdout.write("숫자를 입력하세요: ");
  String? input = stdin.readLineSync();

  if (input != null) {
    int number = int.parse(input);

    for(int i = 1; i <= 9; i++) {
      print("$number x $i = ${number * i}");
    }
  } else {
    print("잘못된 입력입니다.");
  }
}