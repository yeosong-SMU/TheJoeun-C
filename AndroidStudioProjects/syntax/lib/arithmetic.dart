void main() {
  int a = 10, b = 3;
  print("plus: ${a + b}");
  print("divide: ${a / b}");
  // 소수 둘째 자리 반올림
  print("divide: ${(a / b).toStringAsFixed(2)}");
  print("올림: ${(a / b).ceil()}");
  print("내림: ${(a / b).floor()}");
  print("mod: ${a % b}");
}