class Todo {
  final int id;
  String text;
  bool done;

  Todo({required this.id, required this.text, required this.done});

  factory Todo.fromJson(Map<String, dynamic> j) => Todo(
    id: (j['id'] as num).toInt(),
    text: (j['text'] ?? '') as String,
    done: (j['done'] ?? false) as bool,
  );
}

enum TodoFilter {all, notDone, done}