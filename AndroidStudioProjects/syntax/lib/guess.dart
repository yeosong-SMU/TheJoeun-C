import 'dart:io';
import 'dart:math';

void main() {
  int answer = Random().nextInt(100) + 1;
  int guess;

  print("1~100 사이의 숫자를 맞춰보세요!");

  do {
    stdout.write("숫자 입력: ");
    guess = int.parse(stdin.readLineSync()!);

    if(guess > answer) {
      print("더 작은 수입니다.");
    } else if(guess < answer) {
      print("더 큰 수입니다.");
    } else {
      print("정답입니다! $answer");
    }
  } while (guess != answer);
}