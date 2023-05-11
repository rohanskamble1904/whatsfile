#include "include/whatsfile/whatsfile_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "whatsfile_plugin.h"

void WhatsfilePluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  whatsfile::WhatsfilePlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
