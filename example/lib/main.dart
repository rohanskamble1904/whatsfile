import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:whatsfile/whatsfile.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _whatsfilePlugin = ShareToWhatsapp();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await _whatsfilePlugin.getPlatformVersion() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }
  var paths="";
 // /storage/emulated/0/Android/data/com.example.filesharing/files/rohan.pdf
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('WhatsApp Share App'),
        ),
        body:  Center(
          child:Column(
            children: [

              MaterialButton(onPressed: () async {
                print(paths);

                ShareToWhatsapp.sharePDF(paths!, 'application/pdf', "911234567891");
              }, child: Text("Send Pdf To WhatsApp",style: TextStyle(color: Colors.white),),color: Colors.blue,),
            ],
          ),)
      ),
    );
  }
}
