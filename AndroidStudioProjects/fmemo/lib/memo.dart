class Memo {
  final int id;   //final 상수
  final String content;
  final DateTime? createdAt;   //? null 허용

  //생성자   required는 필수란 뜻
  Memo({required this.id, required this.content, this.createdAt});

  //json ==> dto 변환
  factory Memo.fromJson(Map<String, dynamic> j) {  //dynamic  다양한 자료형. java에서 object 같은.
    return Memo(
      id: (j['id'] as num).toInt(),
      content: (j['content'] ?? '') as String,  //?? null 대체값
      createdAt: j['created_at'] != null
        ? DateTime.tryParse(j['created_at'].toString())
          : null,
    );
  }

  //해시맵 ==> json
  Map<String, dynamic> toJson() => {
    'id' : id,
    'content' : content,
    if(createdAt != null) 'created_at' : createdAt!.toIso8601String(),
  };
}