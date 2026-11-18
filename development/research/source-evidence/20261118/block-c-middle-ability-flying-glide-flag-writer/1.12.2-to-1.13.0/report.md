# 1.12.2 → 1.13 source trace: ability flight clears live Elytra glide

## Bounded verdict

For an ordinary dry local player with a default chest-slot Elytra, a live synchronized flag 7, no passenger state, and `abilities.canFly`, the 1.12.2 and 1.13 source paths are behaviorally the same for the requested operation. A legal local double-jump toggle can set `abilities.flying` and synchronize abilities. The next ordinary local movement through `PlayerEntity.moveRelative` enters the abilities-flying branch; it calls `super.moveRelative` while the live glide flag is still set, then sets `fallDistance = 0.0F` and directly clears flag 7. Thus one glide movement response can execute on that call before the clear. The clear is reachable and is not server-only, delayed to a separate glide scheduler, or blocked by the ordinary dry/default-Elytra preconditions.

No change in direct-clear existence, ordering, timing, or reachability is supported for this bounded path across this pair. This is a bounded no-difference result, not a full-pair coverage freeze.

## Exact sources and paired slices

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`; line numbers are one-based in the pinned local files.

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`
  - 1.12.2: `syncAbilities` 323–326; local ability selection/toggle 747–762; Elytra start gate 764–769; camera flight input 771 onward; grounded flight reset 812–816.
  - 1.13: `syncAbilities` 332–335; local ability selection/toggle 756–771; Elytra start gate 773–778; grounded flight reset 833–837.
  - Both locally set `abilities.flying` true from spectator state or toggle it on the second eligible jump, then call `syncAbilities()`. In the non-spectator branch the surrounding gates are `abilities.canFly`, `!bl`, jumping, and `!bl4`. 1.13 adds `!this.isSwimming()` to the second-tap toggle arm; the assigned dry witness satisfies it. Both versions' start-gliding input gate requires jumping, descending, airborne, `!isFallFlying()`, and `!abilities.flying`, followed by a chest Elytra identity and `ElytraItem.canFly` check. It therefore does not re-enable glide from this input while the live glide flag is already true or after flight has been toggled on.
- `net/minecraft/entity/living/player/PlayerEntity.java`
  - 1.12.2: `moveRelative` 1386–1404, especially 1390–1401.
  - 1.13: `moveRelative` 1435–1461, especially 1447–1458.
  - In both, `if (abilities.flying && !isRiding())` saves vertical velocity and `speedInAir`, sets ability fly speed, invokes `super.moveRelative(...)`, restores vertical velocity multiplied by `0.6` and the saved speed, then writes `fallDistance = 0.0F` and `setFlag(7, false)`. Otherwise it invokes `super.moveRelative` without the ability-flight clear. 1.13 adds swimming movement before the branch; for the assigned dry witness that does not change branch eligibility. The clear is after the superclass movement call, so it does not prevent the superclass from observing the still-live flag during this invocation.
- `net/minecraft/entity/living/LivingEntity.java`
  - 1.12.2: movement dispatch and glide branch 1425–1477; server glide validator/writer `flyingTick` 1854–1873; glide predicate 2184–2186.
  - 1.13: movement dispatch and glide branch 1459–1517; server glide validator/writer `flyingTick` 1919–1938; glide predicate 2295–2297.
  - Both `moveRelative` methods pass the locally-controlled gate and, for dry movement, reach `if (isFallFlying())` while flag 7 is true. Both use that branch for this superclass call. The on-ground flag-7 clear in that glide branch is guarded by `!world.isClient`, so it is not the local clear here. The `flyingTick` scheduler recomputes the flag from current flag, airborne/non-riding state, and chest Elytra validity, but commits `setFlag(7, bl)` only under `!world.isClient`; its eligibility/order is the same across the pair. `isFallFlying()` directly returns `getFlag(7)` in both.
  - 1.13 has a slow-falling prelude at 1461–1465 that may write `fallDistance = 0.0F` before the movement gates. This is a separate effect-specific fall-distance writer. For the ordinary no-slow-falling witness, it is inactive; regardless, `PlayerEntity.moveRelative` retains the same later ability-flight fall-distance writer before the same flag clear.
- `net/minecraft/entity/Entity.java`: `getFlag`/`setFlag` are around 1826–1832 (1.12.2) and 1838–1844 (1.13); flag storage access itself is not part of a changed eligibility predicate.
- `net/minecraft/item/ElytraItem.java`: `canFly` exists in both releases. The local start-gliding gate and server-side flag scheduler both use it. The ordinary valid Elytra witness passes this gate; neither item method changes the bounded ability-flight clear.
- `net/minecraft/entity/living/player/PlayerAbilities.java`: `flying` and `canFly` fields are present in both releases.

## Scope and exclusions

This trace covers the local ability input/toggle, its ability synchronization call, the local movement dispatch into `PlayerEntity.moveRelative`, the superclass glide observation, and source-level flag 7 writers in the checked LivingEntity/PlayerEntity code. The legal state is source-reachable when flight permission is enabled (`canFly`) and the non-spectator toggle conditions are met; the assignment assumes the synchronized glide flag is already live.

The separate 1.13 slow-falling `fallDistance` write is recorded above but does not establish a change in the asked direct flag clear. The authority and scheduling for the server's synchronized glide state, server packet acceptance, and a complete player-tick graph are outside this slice. `flyingTick` is explicitly a server writer and does not overwrite the local flag on the client. NBT read's `FallFlying` restoration sets flag 7 in both versions, but is not the live-local ordinary movement writer under examination.

## Source identity and integrity

Host and shell: Windows host, native PowerShell; primary checkout `D:\Javastuff\LegacyParkourCompat`. Canonical decompiled source tree was read-only; no decompilation or network access was used.

- 1.12.2 mapping: `ornithe-feather`; ready marker records `feather-gen2-1.12.2+build.2-mergedv2.jar` and `.tiny`; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; provenance file SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`. Provenance records raw 1.12.2 client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` and Feather build.2 mapping artifacts.
- 1.13 mapping: `ornithe-feather`; provenance resolves `net.ornithemc:feather-gen2:1.13+build.2`; source manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; provenance file SHA-256 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`. Provenance records raw 1.13 client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` and Feather build.2 mapping artifacts.

The declared per-file source-manifest entries matched SHA-256 values computed from the local files for each of the six relied-on classes in both versions:

| Class | 1.12.2 SHA-256 | 1.13 SHA-256 |
|---|---|---|
| `LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| `PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| `LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| `Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `ElytraItem.java` | `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c` | `4ff72d8153400a0ce484c568b899a2b8c23c94654bd7c3f5dd38568da3dcf72b` |
| `PlayerAbilities.java` | `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` | `03c506732bb599c2a6d5f6c7d6f13175b9d27a0abfd9058811cf443f51af012d` |
