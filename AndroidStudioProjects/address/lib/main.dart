import 'package:flutter/material.dart';

import 'address_list_page.dart';
import 'api_service.dart';

void main() {
  runApp(const AddressApp());
}

class AddressApp extends StatelessWidget {
  const AddressApp({super.key});

  @override
  Widget build(BuildContext context) {
    final api = ApiService('http://localhost');
    return MaterialApp(
      title: 'Address App',
      theme: ThemeData(useMaterial3: true),
      home: AddressListPage(api: api),
    );
  }
}