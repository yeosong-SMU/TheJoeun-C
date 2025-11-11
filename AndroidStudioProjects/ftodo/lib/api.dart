class Api {
  static const String base = 'http://localhost';

  static Uri u(String path) => Uri.parse('$base$path');
}