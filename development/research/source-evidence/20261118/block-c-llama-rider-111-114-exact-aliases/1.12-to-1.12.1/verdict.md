# Independent source audit: 1.12 → 1.12.1 Llama rider Y placement

## Verdict

**ACCEPT — bounded no-difference claim supported.** The ordinary default-size vanilla Llama's direct rider-position callback has the same player Y expression, numeric types/order, virtual accessors, and effective client callback gates in Minecraft 1.12 and 1.12.1. This accepts only the claim about this operation; it is not full pair coverage, a movement implementation approval, or runtime parity evidence.

## Immutable input

Audited report: `c-llama-112-1121-position-intermediate/report.md`, SHA-256 `886c43e2bc0cddde17b792062d83c2f0fbd79f0346148aa5efed038fd36ecd3f`.

Sources are the read-only local canonical `decompiled_minecraft/<version>/ornithe-feather/` trees, with provenance manifests:

- 1.12 manifest SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- 1.12.1 manifest SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- Each manifest pins the Ornithe Feather Gen2 build.2 mapping artifact, metadata and Mojang client artifact as listed in the audited report. Their input SHA-256 values match the manifest entries for both releases.

For each side, independently recomputed hashes of the following files match its provenance `sources` entry. Hashes are 1.12 / 1.12.1 respectively:

- `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`: `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` / same.
- `net/minecraft/entity/Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` / same.
- `net/minecraft/entity/living/LivingEntity.java`: `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` / same.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` / `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640`.
- `net/minecraft/world/World.java`: `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` / `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259`.

## Source audit

- `LlamaEntity.java:60–63` sets the default Llama dimensions to `(0.9F, 1.87F)`; no other `setSize` call exists in this class. `getMountHeight()` at `153–156` returns `this.height * 0.67`. The field is `float`, widened for multiplication by the `double` literal; result is `double`. The method and whole file are identical across the pair.
- `LlamaEntity.updateRiderPositon(Entity)` at `144–151` first requires `this.hasPassenger(rider)`, then calls `rider.setPosition(...)`. Its Y argument is left-associative `this.y + this.getMountHeight() + rider.getRideHeight()`. Both additions are double operations under the declared `double` accessor and `setPosition(double, double, double)` signatures; there is no intermediate float cast. The method and whole file are identical across the pair.
- A `PlayerEntity` virtually supplies `getRideHeight()` at `943–945`, returning double `-0.35`; the inherited `Entity` default at `1606–1608` is `0.0`. The override body is text-identical in both releases. Thus the Y calculation for the player is `(mountY + (floatHeight widened and multiplied by 0.67d)) + (-0.35d)`, in that source order.
- Reachable callback route: `World` at `1427–1433` dispatches an in-chunk riding entity through `rideTick()`. `PlayerEntity.rideTick()` at `375–385` only takes its dismount branch under `!world.isClient && isSneaking() && isRiding()`; on the logical client it calls `super.rideTick()`. `LivingEntity.rideTick()` at `1917–1922` calls `Entity.rideTick()`. `Entity.rideTick()` at `1582–1595` stops riding if the captured mount is removed; otherwise it zeros rider velocity, ticks the rider, then checks `isRiding()` before virtual `entity.updateRiderPositon(this)`. For a Llama, dynamic dispatch reaches its override, whose `hasPassenger(rider)` is the final write gate. The Player callback, LivingEntity callback, Entity callback and World dispatch bodies are identical across the pair; PlayerEntity and World whole-file differences do not alter these audited methods.
- No intervening `PlayerEntity.updateRiderPositon` override exists. The class declaration search also confirms the only `rideTick` declarations in this inheritance path are `PlayerEntity`, `LivingEntity`, and `Entity`, matching the trace above.

No source-level delta or unsupported numeric claim was found within the bounded ordinary adult/default Llama-to-Player Y-position operation. Horizontal offsets, other riders/mounts, other operations, and full pair inventories were not audited.
