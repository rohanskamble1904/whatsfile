import 'package:flutter_test/flutter_test.dart';
import 'package:whatsfile/whatsfile.dart';
import 'package:whatsfile/whatsfile_platform_interface.dart';
import 'package:whatsfile/whatsfile_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockWhatsfilePlatform
    with MockPlatformInterfaceMixin
    implements WhatsfilePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final WhatsfilePlatform initialPlatform = WhatsfilePlatform.instance;

  test('$MethodChannelWhatsfile is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelWhatsfile>());
  });

  test('getPlatformVersion', () async {
    ShareToWhatsapp whatsfilePlugin = ShareToWhatsapp();
    MockWhatsfilePlatform fakePlatform = MockWhatsfilePlatform();
    WhatsfilePlatform.instance = fakePlatform;

    expect(await whatsfilePlugin.getPlatformVersion(), '42');
  });
}
