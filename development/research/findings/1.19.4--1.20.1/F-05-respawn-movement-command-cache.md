# F-05: Respawn movement-command cache can suppress first sneak/sprint update

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/network/protocol/game/ClientboundRespawnPacket.java`, shouldKeep 109-111.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/network/protocol/game/ClientboundRespawnPacket.java`, shouldKeep 114-116.

Difference: When `ClientboundRespawnPacket.shouldKeep((byte)2)` is false, 1.19.4's `ClientPacketListener#handleRespawn` still calls the five-argument `createPlayer` with the old local player's `isShiftKeyDown()` and `isSprinting()` values. 1.20.1 instead calls the three-argument `createPlayer`; that overload delegates with `false,false`. `LocalPlayer` stores those arguments in its `wasShiftKeyDown` and `wasSprinting` command caches on both sides. 1.19.4 and 1.20.1 then use the same `sendPosition`/`sendIsSprintingIfNeeded` comparisons to decide whether to emit the respective command. 1.19.4 held sneak key can therefore match 1.19.4's inherited cache and suppress PRESS_SHIFT_KEY, while 1.20.1 starts with a false cache and emits it. Likewise, if the fresh player becomes sprinting before the first command comparison and the old player was sprinting, 1.19.4 can suppress START_SPRINTING while 1.20.1 emits it. Both server handlers directly map the commands to `setShiftKeyDown` or `setSprinting`. This establishes the conditional command and state-writer difference only; it does not establish a trajectory or the outcome for every server respawn state. - 1.19.4 `ClientPacketListener#handleRespawn`, lines 1060-1129 - 1.20.1 `ClientPacketListener#handleRespawn`, lines 1065-1143 - 1.19.4 `LocalPlayer` constructor, lines 134-141, and command emission, lines 218-279 - 1.20.1 `LocalPlayer` constructor, lines 136-143, and command emission, lines 220-281 - 1.19.4 `MultiPlayerGameMode#createPlayer` three-argument overload, lines 377-378 - 1.20.1 `MultiPlayerGameMode#createPlayer` three-argument overload, lines 370-371 - 1.19.4 `ServerGamePacketListenerImpl#handlePlayerCommand`, lines 1372-1386 - 1.20.1 `ServerGamePacketListenerImpl#handlePlayerCommand`, lines 1376-1390

Movement meaning: This path requires a respawn with keep-entity-data false. The conditional sneak case also requires the sneak input to remain active when the new LocalPlayer samples input. The conditional sprint case requires sprinting to be true at the first emission check and the old player's sprint state to have seeded 1.19.4's cache true.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/network/protocol/game/ClientboundRespawnPacket.java",
      "member": "shouldKeep",
      "lines": "109-111",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 26261980683e598d52685eb07e053e1093fa4a4a98e5b5898bfdd8bf362beed9; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/network/protocol/game/ClientboundRespawnPacket.java",
      "member": "shouldKeep",
      "lines": "114-116",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 60ea8e3b4f1672c2f634f54f3cfa0de3c894b1797c82d577ee62ac096ffb2a45; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
