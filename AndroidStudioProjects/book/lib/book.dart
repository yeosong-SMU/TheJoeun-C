class Book {
  final int? id;
  final String title;
  final String author;
  final String genre;

  Book({
    this.id,
    required this.title,
    required this.author,
    required this.genre,
  });

  factory Book.fromJson(Map<String, dynamic> j) => Book(
    id: j['id'],
    title: j['title'] ?? '',
    author: j['author'] ?? '',
    genre: j['genre'] ?? '',
  );

  Map<String, dynamic> toJson() => {
    'id': id,
    'title': title,
    'author': author,
    'genre': genre,
  };
}