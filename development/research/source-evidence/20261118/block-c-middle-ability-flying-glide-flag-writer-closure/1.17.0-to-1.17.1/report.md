# 1.17.0 → 1.17.1 ability-flight Elytra travel

**Result:** No source difference in the assigned pathway. This is a bounded no-difference result, not full-pair coverage or acceptance.

## Compared behavior

For an ordinary local player who is dry, airborne, not a passenger, has lawful creative ability flight enabled, and is actively fall-flying with an enabled chest-slot Elytra, `Player.travel(Vec3)` takes the same path in both releases:

1. The `abilities.flying && !isPassenger()` branch saves the incoming vertical velocity and current flying speed, updates flying speed, then calls `super.travel(vec3)`.
2. With fall-flying active, that call reaches `LivingEntity.travel(Vec3)`'s `isFallFlying()` glide branch. It applies the glide velocity math and calls `move(SELF, getDeltaMovement())` before returning.
3. The player wrapper then restores vertical velocity to the saved value times `0.6`, restores flying speed, sets `fallDistance = 0.0F`, and directly clears shared flag 7 with `setSharedFlag(7, false)`.

Both versions retain this gate, operation order, constants, and state writes. In particular, the post-glide fall-distance reset is a distinct write before the wrapper's direct flag-7 clear. The inherited glide branch can first set fall distance to `1.0F` when incoming vertical velocity is greater than `-0.5`; the player wrapper subsequently overwrites it with `0.0F` in this creative-flight path. No trajectory claim beyond these source operations is made.

## Exact source comparison

Mojmap, 1.17.0 (`1.17` source ID):

- `net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1418–1448; SHA-256 `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516`.
- `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1999–2135; glide branch lines 2064–2106; SHA-256 `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`.

Mojmap, 1.17.1:

- `net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1409–1439; SHA-256 `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`.
- `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2003–2138; glide branch lines 2068–2110; SHA-256 `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`.

The complete `Player.travel` slices and the corresponding glide branch lines compared identical after aligning their source offsets.

## Source and pin identity

For 1.17.0, canonical source root is `decompiled_minecraft/1.17/mojmap/`. Its hidden `.decompile-provenance.json` (SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`) includes the two source hashes above and these input hashes. I independently checked each cached artifact against its `.pin.json`; all matched:

- `.minecraft-decompile-cache/1.17/client.jar`, pin `.minecraft-decompile-cache/1.17/client.jar.pin.json`: `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`.
- `.minecraft-decompile-cache/1.17/client_mappings.txt`, pin `.minecraft-decompile-cache/1.17/client_mappings.txt.pin.json`: `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`.
- `.minecraft-decompile-cache/1.17/version.json`, pin `.minecraft-decompile-cache/1.17/version.json.pin.json`: `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`.

The 1.17.1 ready marker identifies version `1.17.1`, Mojmap, and source-manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; that hash matches `decompiled_minecraft/1.17.1/mojmap.sources.sha256`, whose Player and LivingEntity entries match the source files above. Its artifact manifest SHA-256 is `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`; the manifest pins the 1.17.1 client (`a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`), Mojmap remap jar (`2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c`), and client mappings (`2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9`). Its provenance identifies the exact requested 1.17.1 generation in the 1.15.2/1.16.5/1.17.1/1.18.2 batch.