import 'dart:io';

void main() {
  stdout.write("정수를 입력하세요: ");
  int num = int.parse(stdin.readLineSync()!);

  if(num % 2 == 0) {
    print("$num : 짝수입니다.");
  } else {
    print("$num : 홀수입니다.");
  }
}