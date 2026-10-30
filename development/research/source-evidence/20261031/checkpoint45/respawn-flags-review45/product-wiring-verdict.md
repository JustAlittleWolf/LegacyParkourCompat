# Product integration wiring review: respawn movement flags

**Verdict for reviewed commit: REQUIRES CORRECTION.**

- Reviewed product integration: `.native-integrations/respawn-flags-product45`, commit `187187f7a0124cbbb5f317ce66ff21569cb6f196`.
- Candidate implementation review: `dd7f35b3479e5793492debfa52b224feb99b34cc`, separately accepted in `verdict.md`.
- Scope: product reconciliation, existing respawn mixin reuse, injection ordering/lifecycle, client thread, resolver boundary, discovery, mixin registration, and native fallback. No build or tests run.

## Required correction

`ClientPacketListenerRespawnMixin` is registered in both mixin configurations declared by `fabric.mod.json`: `src/main/resources/legacyparkourcompat.mixins.json` has it in its `client` array, and `src/client/resources/legacyparkourcompat.client.mixins.json` already contains it. The product base had the respawn mixin in the client configuration before this change. Remove the new duplicate `ClientPacketListenerRespawnMixin` entry from the main configuration. Keep the new `EntitySharedFlagsAccessor` registered once. Both configurations load on a client, so this creates duplicate application of the same mixin to `ClientPacketListener` and can fail mixin application at startup.

## Remaining reviewed wiring

No further issue found in the scoped code. The existing attribute respawn hooks remain separate. The new flag hook is independently `@MechanicType`-registered and auto-discovered from client changes. `V1_18_2` is the required lower native barrier so earlier selections do not inherit the later restore operation; it is not a claim of historical parity for those older profiles. The redundant `V1_19_3` native change is absent in this commit, and selected 1.19.3+ profiles naturally use native fallback because the 1.19 change is older than the selected version.

The flag capture follows `ensureRunningOnSameThread`; the packet's keep-data bit 2 gate is honored; the old local player's byte is captured before respawn replacement and merged at handler tail with only bits 3, 4 and 7 changed. The tail consumes the saved player field and checks that a replacement exists. The raw accessor update avoids `setSprinting` and attribute changes. The acceptance remains bounded to the accepted finding; review does not establish compilation or runtime parity.
