import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'whatsfile_platform_interface.dart';

/// An implementation of [WhatsfilePlatform] that uses method channels.
class MethodChannelWhatsfile extends WhatsfilePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('file_sharing_channel');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
