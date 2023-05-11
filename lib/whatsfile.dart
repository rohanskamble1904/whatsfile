
import 'package:flutter/services.dart';

import 'whatsfile_platform_interface.dart';

class ShareToWhatsapp {
  static const MethodChannel _channel =
  const MethodChannel('file_sharing_channel');
  Future<String?> getPlatformVersion() {
    return WhatsfilePlatform.instance.getPlatformVersion();
  }
  static Future<void> sharePDF(String filePath,String mimeType,String mobile) async {
    try {
      await _channel.invokeMethod('shareFile', {'filePath': filePath,'mimeType': mimeType,'phone':mobile});
    } on PlatformException catch (e) {
      print('Error sharing file: ${e.message}');
    }
    }
}
