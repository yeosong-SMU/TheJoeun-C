class AddressBook {
  final int? id;
  final String name;
  final String phone;
  final String email;
  final String address;

  AddressBook({
    this.id,
    required this.name,
    this.phone = '',
    this.email = '',
    this.address = '',
  });

  factory AddressBook.fromJson(Map<String, dynamic> j) => AddressBook(
    id: j['id'],
    name: j['name'] ?? '',
    phone: j['phone'] ?? '',
    email: j['email'] ?? '',
    address: j['address'] ?? '',
  );

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'phone': phone,
    'email': email,
    'address': address,
  };
}