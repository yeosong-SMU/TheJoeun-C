import 'dart:io';

void main() {
  stdout.write("n 값을 입력하세요: ");
  int n = int.parse(stdin.readLineSync()!);

  int sum = 0;
  for(int i = 1; i <= n; i++) {
    sum += i;
  }

  print("합계: $sum");
}