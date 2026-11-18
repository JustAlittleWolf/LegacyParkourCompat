# Independent verdict: Elytra glide / climb alias gate, 1.9.2 → 1.9.3

## Verdict

**Confirmed for the assigned slice: no 1.9.2 → 1.9.3 source delta found.** The cited Feather sources are the exact local 1.9.2 and 1.9.3 trees; the required provenance hashes and every source hash used here match their manifests. Independently extracted method bytes for the active flag updater, travel dispatcher, climb predicate and glide-status getter match across both releases and match the body hashes in the frozen claim.

This verdict is limited to the already-active, locally controlled player with a standard usable chest Elytra, dry movement, and feet at a ladder or vine. It is not whole-pair coverage, does not establish an intermediate patch boundary, and does not assert runtime parity.

## Independent trace

- **Activation authority / startup gate.** `LocalClientPlayerEntity.serverTickAi()` sends `START_FALL_FLYING` only under the input-jump, airborne, descending, not-already-gliding, not-creative-flying conditions, then verifies a usable chest Elytra. It sends a request; it does not set flag 7. `ServerPlayNetworkHandler.handlePlayerAction()` accepts that request under the corresponding airborne/descending/not-already-gliding/not-water gate and the same chest-item check; `ServerPlayerEntity.setFlying()` sets flag 7. Both request bodies and setter are unchanged at the adjacent release.
- **Active flag updater and direct authority.** `LivingEntity.mobTick()` calls `flyingTick()` before `moveRelative()`. The updater starts from `getFlag(7)`, validates airborne/not-riding and a usable chest Elytra, and writes the resulting bit only when `!world.isClient`. Consequently, for the assigned already-active client state, this updater does not locally overwrite the replicated flag; `isFallFlying()` directly returns `getFlag(7)`. This claim is about that active input state, not a claim that a client locally establishes the flag.
- **Branch precedence before climb cleanup.** `moveRelative()` first checks local/logical movement and the dry-water/lava gates, then checks `isFallFlying()` before its ordinary branch. When the active flag is true, glide code reaches its first `move(...)` without evaluating `isClimbing()` or executing ordinary climb cleanup. The climb predicate is only consulted in the non-gliding dry branch: before its `move`, and after movement when horizontal collision is present.
- **Pre-move threshold and subsequent writes.** The glide branch tests the double `velocityY > -0.5` strictly and writes `fallDistance = 1.0F` before look-vector math, velocity changes, drag and the first `move(...)`. There is no other `fallDistance` write in that branch before `move`. The inherited `Entity.move()` calls `checkFallDamage`: a landed move calls the block callback if the accumulated distance is positive and then resets `fallDistance` to `0.0F`; a negative resolved Y displacement adds `-dy`. The ordinary climb branch instead resets `fallDistance` to `0.0F` before its move, but active glide bypasses that write. This is the bounded meaning of the threshold write; landing or descent during `move` can then change it.
- **A/B contact predicate.** `isClimbing()` samples the block at `(floor(x), floor(getShape().minY), floor(z))`, rejects spectators, and returns true directly for `Blocks.LADDER` or `Blocks.VINE` (with a separate trapdoor case). Ladder and vine are therefore both covered feet-position aliases in the assignment. The glide branch does not call this predicate before its first move.

## Exact evidence identities

- Frozen claim/report: `c-glide-climb-192-193-alias-gate/report.md`, SHA-256 `68b747fb3067926b53b62886a5a6fa697066b4109a86958f47e16f62f0de37c7`.
- Exact source roots: `decompiled_minecraft/1.9.2/ornithe-feather/` and `decompiled_minecraft/1.9.3/ornithe-feather/`; mapping family is Ornithe Feather Gen2 build.2 on each exact release.
- Provenance manifest SHA-256: 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Both manifests identify Vineflower `1.12.0`. Feather mapping artifacts are pinned as 1.9.2 build.2 SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` and 1.9.3 build.2 SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; client inputs are separately pinned in the manifests.
- All cited files were rehashed against their respective manifest `sources` entries. Relevant actual source-file SHA-256 values (1.9.2 / 1.9.3):
  - `net/minecraft/entity/living/LivingEntity.java`: `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` / `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
  - `net/minecraft/entity/Entity.java`: `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
  - `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` / `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
  - `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`: `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` / `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`.
  - `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`: `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` / `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd`.
- Independently re-extracted UTF-8 method-body SHA-256 values, equal across the pair and equal to the frozen report: `moveRelative(float sideways, float forwards)` `8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd`; `flyingTick()` `a6b70cf51e2a4f3e4bd14ce0189793795ad590a73a4bdacc4ce0da11992f1fe0`; `isClimbing()` `b3cb19f4c2d27c81dcaecd98b04d7e2243b0bd3d2ad62f744d6620ff85a2327a`; `isFallFlying()` `7a9851fa3cd3041eb7d58523b5176a962aca1985630ca487ee25b79db8f84a39`.

## Source references and boundary

- `LocalClientPlayerEntity.serverTickAi()`: 1.9.2 lines 704–709; 1.9.3 lines 707–712.
- `ServerPlayNetworkHandler.handlePlayerAction()`, `START_FALL_FLYING`: both line 846 onward; the request gate and item validation are lines 847–850.
- `ServerPlayerEntity.setFlying()`: 1.9.2 lines 1040–1042; 1.9.3 lines 1044–1046.
- `LivingEntity.mobTick()`: 1.9.2 lines 1697–1703 for travel ordering; 1.9.3 lines 1706–1712.
- `LivingEntity.moveRelative()`: 1.9.2 lines 1293–1460; 1.9.3 lines 1302–1469. The strict threshold/write is 1.9.2 lines 1297–1300 and 1.9.3 lines 1306–1309; ordinary climb cleanup is 1.9.2 lines 1367–1380 and 1.9.3 lines 1376–1389.
- `LivingEntity.isClimbing()`: 1.9.2 lines 900–912; 1.9.3 lines 905–917.
- `LivingEntity.flyingTick()`: 1.9.2 lines 1709–1727; 1.9.3 lines 1718–1736. `isFallFlying()`: 1.9.2 lines 2042–2044; 1.9.3 lines 2052–2054.
- Inherited `Entity.move()` resolves collision and calls `checkFallDamage` at 1.9.2 lines 438–642; `checkFallDamage` writes the landed reset or descent accumulation at lines 792–801. Corresponding 1.9.3 file was independently hashed against its provenance manifest; no broader `Entity.move()` delta claim is needed for the no-difference glide/climb conclusion.

The reviewed claim remains closed only for these source identities and this state/contact slice. The result does not close other player-tick inventory or pair coverage.

