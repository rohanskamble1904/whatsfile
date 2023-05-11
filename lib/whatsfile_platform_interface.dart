import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'whatsfile_method_channel.dart';

abstract class WhatsfilePlatform extends PlatformInterface {
  /// Constructs a WhatsfilePlatform.
  WhatsfilePlatform() : super(token: _token);

  static final Object _token = Object();

  static WhatsfilePlatform _instance = MethodChannelWhatsfile();

  /// The default instance of [WhatsfilePlatform] to use.
  ///
  /// Defaults to [MethodChannelWhatsfile].
  static WhatsfilePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [WhatsfilePlatform] when
  /// they register themselves.
  static set instance(WhatsfilePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
