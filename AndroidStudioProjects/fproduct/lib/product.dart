class Product {
  final int? id;
  final String name;
  final double price;
  final String? description;
  final String? imageUrl;

  Product({
    this.id,
    required this.name,
    required this.price,
    this.description,
    this.imageUrl,
  });

  factory Product.fromJson(Map<String, dynamic> j) => Product(
    id: j['id'] is int
        ? j['id']
      : (j['id'] == null ? null : int.parse(j['id'].toString())),
    name: j['name']?.toString() ?? '',
    price: (j['price'] ?? 0).toDouble(),
    description: j['description']?.toString(),
    imageUrl: j['imageUrl']?.toString(),
  );

  //객체를 json으로 변환
  Map<String, dynamic> toJson() => {
    if(id != null) 'id' : id,
    'name' : name,
    'price' : price,
    'description' : description,
    'imageUrl': imageUrl,
  };

  //상품정보 수정 관련 함수
  Product copyWith({
    int? id,
    String? name,
    double? price,
    String? description,
    String? imageUrl,
  }) => Product(
    id: id ?? this.id,
    name: name ?? this.name,
    price: price ?? this.price,
    description: description ?? this.description,
    imageUrl: imageUrl ?? this.imageUrl,
  );
}