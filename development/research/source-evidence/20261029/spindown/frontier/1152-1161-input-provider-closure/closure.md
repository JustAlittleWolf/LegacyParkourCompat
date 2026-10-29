# Local-player raw input provider closure: Mojmap 1.15.2 → 1.16.1

## Scope and exact source identity

This closes the raw keyboard input provider dependency identified by the local-player gate closure: installation of the local player's `Input`, key-state updates and reads, `LocalPlayer.aiStep` sample/crouch ordering, and the input values transferred into the local movement fields consumed by `LivingEntity.aiStep`. It does not reopen the already-reported sprint, Elytra, auto-jump, flight, or pose-fit claims.

Canonical read-only source trees:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` — ready marker reports `versionId=1.15.2`, `mapping=mojmap`, `status=ready`, 3,332 Java files, Vineflower 1.12.0; `mojmap.sources.sha256` SHA-256 is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, matching `sourceManifestSha256` in `mojmap.ready.json`.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.1\mojmap` — ready marker reports `versionId=1.16.1`, `mapping=mojmap`, `status=ready`, 3,572 Java files, Vineflower 1.12.0; `mojmap.sources.sha256` SHA-256 is `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, matching `sourceManifestSha256` in `mojmap.ready.json`.

For each table entry the source-file SHA-256 was recomputed and matched the row in that release's `mojmap.sources.sha256` manifest. The path shown is relative to `mojmap/`.

| Source path | 1.15.2 SHA-256 | 1.16.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` | same |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` |
| `net/minecraft/client/Options.java` | `5bf3f2ef925c282c8ac9c6df57b362013b295b6946b006ee72a008495c485634` | `efcaf563621e2c6ad2e547d5273492e7dbeb13a6e0b9c8639aa0e056a84435bb` |
| `net/minecraft/client/KeyMapping.java` | `f68badd84af608d37b419430f6875a1b4689f00c7adff317b58e01b825659649` | `d07149699a69aec6a6d010ad3a8f4cc1aa6865bf5faa2f6a1f07d3055a08df6f` |
| `net/minecraft/client/ToggleKeyMapping.java` | `62a498fd0aa92f301b97dda87c8f58f1940afcdd3a45b2177b64577485127508` | same |
| `net/minecraft/client/KeyboardHandler.java` | `e708fb96303d6a9f5a867ef2151b87e1a00479f5d6b42ec784105ea9f5a4a85b` | `ae2f40305a88439f7e58998e07f23cb1fdccdb007ee8f21312834c06d604e068` |
| `net/minecraft/client/MouseHandler.java` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |

## Provider trace

1. **Installation is the same.** `ClientPacketListener.handleLogin` assigns `new KeyboardInput(this.minecraft.options)` to the local player in both releases (1.15.2 `ClientPacketListener.java:370`; 1.16.1 `:397`). The respawn handler likewise assigns the same provider to the replacement local player (1.15.2 `:1084`; 1.16.1 `:1135`). The local client tree has no other `Input` subclass; `KeyboardInput` is the sole `extends Input` declaration. Thus the compared normal login/respawn local-player route is not using the base no-op `Input.tick` implementation.

2. **Movement key state is sampled identically.** The `Options` declarations for `keyUp`, `keyDown`, `keyLeft`, `keyRight`, `keyJump`, and `keyShift` preserve their same key identifiers, default codes, categories, and `ToggleKeyMapping` use for sneak (1.15.2 `Options.java:116–121`; 1.16.1 `:107–112`). `KeyboardInput.tick(boolean)` is byte-identical (`KeyboardInput.java:13–25` both): it reads each direction's `isDown()`, reads `keyJump.isDown()` and `keyShift.isDown()`, creates signed unit forward/left impulses (or zero when both/neither are down), then multiplies only the two movement impulses by `0.3` when the supplied slow-movement flag is true. `Input` field layout and base methods are byte-identical (`Input.java:5–25`).

3. **Key-state storage/update relevant to those reads is unchanged.** `KeyMapping.set`, `setAll`, `isDown`, and `setDown` have the same bodies in both sources: keyboard event handling sets/releases the mapped key state; `isDown` returns the stored state; `setAll` resamples key symbols from the window on mouse grab; and `setDown` stores the state. The `KeyboardHandler` key release/press branch still calls `KeyMapping.set(key, false/true)` in both, and `MouseHandler.grabMouse` still invokes `KeyMapping.setAll()` before grabbing the mouse. The full `KeyMapping.java` hash changes only in translated-name construction/return (`Supplier<String>`/string formatting becomes `Component` display names); those edits do not touch the key-down or update methods. `ToggleKeyMapping.java` is byte-identical, so hold versus toggle sneak semantics remain driven by the same `toggleCrouch` supplier and event update code. The nearby GUI event changes do not alter these movement-key state writes.

4. **Tick sampling order has a `LocalPlayer` change, but no provider change.** At the start of `LocalPlayer.aiStep`, both versions copy the already-stored `input.jumping` and `input.shiftKeyDown` into `bl`/`bl2`, and compute the sprint impulse gate before calling `input.tick(...)` (1.15.2 `LocalPlayer.java:632–635`; 1.16.1 `:659–666`). In 1.16.1, the new `crouching` cache is assigned immediately before `input.tick`, from the prior stored shift-key state through `isShiftKeyDown()`; the 1.15.2 implementation computes `isCrouching()` live. Then `input.tick(this.isMovingSlowly())` reads the current key mappings in both. Consequently a newly pressed/released shift is first sampled into `input.shiftKeyDown` by this tick; the 1.16.1 crouching cache and both versions' pre-tick `bl2` use the state already stored before that sample. This distinction is part of the reported local-player ordering/cache delta, not a difference in how raw shift is produced.

5. **Impulses reach the same movement fields.** Under `isControlledCamera()`, `LocalPlayer.serverAiStep()` copies `input.leftImpulse`, `input.forwardImpulse`, and `input.jumping` into `xxa`, `zza`, and `jumping` in both versions (1.15.2 `LocalPlayer.java:607–612`; 1.16.1 `:634–639`). `LivingEntity.aiStep()` invokes `serverAiStep()` under the effective-AI gate, then later passes `new Vec3(xxa, yya, zza)` to `travel` in both versions (1.15.2 `LivingEntity.java:2240–2287`; 1.16.1 `:2390–2457`). This closes the raw forward/jump source into the ordinary local movement step. Local crouch/sneak readers and gate behavior remain the separate `LocalPlayer` changes already documented by the local-player gate closure.

## Closure verdict

**Closed: raw keyboard provider parity for the examined local-player gate slices.** Exact endpoint evidence shows the same keyboard subclass, same login/respawn installation, same current-key sampling and impulse calculation, same relevant key-state storage/update semantics, and same transfer of forward/jump values into movement. No 1.15.2→1.16.1 provider-side delta was found for raw shift, jump, or forward. The 1.16.1 pre-sampling crouch-cache write changes when the existing local-player logic observes the already-stored shift value; retain that `LocalPlayer.aiStep` finding independently. This endpoint comparison does not establish an intermediate patch boundary or runtime parity.
