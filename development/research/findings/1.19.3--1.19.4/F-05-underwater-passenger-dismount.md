# F-05: underwater passenger dismount gate depends on entity-type tag

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, dismountsUnderwater 1981-1983.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, baseTick 325-379; rideableUnderWater 555-557.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, baseTick 320-374.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/commands/RideCommand.java`, mount 62-86.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/tags/TagLoader.java`, load 44-65.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/packs/resources/MultiPackResourceManager.java`, listResourceStacks 97-106.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleUpdateTags 1505-1507.

Difference: For a player already riding an untagged LivingEntity such as Cow while underwater, 1.19.3's inherited false predicate is negated by the server gate, so 1.19.3 detaches the player. With 1.19.4's built-in tag, Cow is absent and the positive predicate is false, so that gate leaves the player mounted. The outcome in 1.19.4 depends on the effective server tag set. Skeleton Horse was checked as a control: 1.19.3 explicitly returns true from its `rideableUnderWater` override, while 1.19.4's built-in tag omits Skeleton Horse. Both versions therefore leave that passenger mounted under this gate; it is not the changed case.

Movement meaning: In 1.19.3, `LivingEntity#dismountVehicle()` selects a vehicle-provided passenger dismount position and dispatches through `ServerPlayer#dismountTo`; the player removes its vehicle and the connection sends the dismount position/rotation. `ServerGamePacketListenerImpl#dismount` teleports with the dismount flag. The client packet handler removes the local vehicle when it receives that flag and applies the supplied coordinates. Source hashes: 1.19.3 `ServerPlayer.java` , `ServerGamePacketListenerImpl.java` , and `ClientPacketListener.java` . In 1.19.4, `ServerPlayer#dismountTo(DDD)V`, lines 937–942, removes the vehicle and writes the selected position. `ServerEntity#sendChanges()`, lines 80–89, sends passenger-list changes and, for a removed player passenger, a position teleport. Source hashes: 1.19.4 `ServerPlayer.java` and `ServerEntity.java` . 1.19.3 client-only compatibility hook can respond to the server's resulting passenger/position update, but it cannot change whether the authoritative 1.19.4 server executes `stopRiding()` or which dismount position it selects. Both decisions are made on the server using server state and its effective tag data. This finding is about direct player passenger state and the resulting player dismount position. No network-arrival ordering relative to a local tick is inferred from source.


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
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "baseTick",
      "lines": "325-379",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "rideableUnderWater",
      "lines": "555-557",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "baseTick",
      "lines": "320-374",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "dismountsUnderwater",
      "lines": "1981-1983",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/server/commands/RideCommand.java",
      "member": "mount",
      "lines": "62-86",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 45e7148e117cf020f927542ebee7743f68520758d6f330bfc7af92aa3df89c80; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/tags/TagLoader.java",
      "member": "load",
      "lines": "44-65",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0bf78c838fbbd5e034097a067757a9a25aa4373622de77b0b762b1985bf39e2b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/server/packs/resources/MultiPackResourceManager.java",
      "member": "listResourceStacks",
      "lines": "97-106",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5c8722689bcca6179a849af89a312f4ae24938a6bfb850652012e77a500e2231; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleUpdateTags",
      "lines": "1505-1507",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
