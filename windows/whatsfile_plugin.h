#ifndef FLUTTER_PLUGIN_WHATSFILE_PLUGIN_H_
#define FLUTTER_PLUGIN_WHATSFILE_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace whatsfile {

class WhatsfilePlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  WhatsfilePlugin();

  virtual ~WhatsfilePlugin();

  // Disallow copy and assign.
  WhatsfilePlugin(const WhatsfilePlugin&) = delete;
  WhatsfilePlugin& operator=(const WhatsfilePlugin&) = delete;

 private:
  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace whatsfile

#endif  // FLUTTER_PLUGIN_WHATSFILE_PLUGIN_H_
