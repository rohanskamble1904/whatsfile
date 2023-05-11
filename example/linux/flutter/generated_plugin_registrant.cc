//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <whatsfile/whatsfile_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) whatsfile_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "WhatsfilePlugin");
  whatsfile_plugin_register_with_registrar(whatsfile_registrar);
}
