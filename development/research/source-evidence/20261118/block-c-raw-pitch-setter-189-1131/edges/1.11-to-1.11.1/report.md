# Feather local pitch update and Elytra read: 1.11 → 1.11.1

## Scope and disposition

This is a bounded source comparison of `Entity.updateLocalPlayerCamera` and the dry, active Elytra path that consumes the resulting stored pitch. The two compared source trees show **no difference in these operations**. This report is evidence for the checked slice, not a full-pair coverage claim.

Minecraft's exact release ID and source directory for the older side are `1.11` (the release commonly described as 1.11.0); there is no `1.11.0` source directory or release ID in the canonical source tree. The newer side is exact ID `1.11.1`.

## Exact source identities and pins

Both source trees are ready `ornithe-feather` mappings produced with the `feather` mapping option and pinned artifacts. The exact provenance files identify the requested and resolved IDs as the versions shown below.

| Version ID | Client SHA-256 | Feather merged jar SHA-256 | Feather Tiny SHA-256 | Source-manifest SHA-256 | Provenance-file SHA-256 |
|---|---|---|---|---|---|
| `1.11` | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` | `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` |
| `1.11.1` | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` | `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` |

Each ready marker's source-manifest and provenance pins match the corresponding files. The source manifest entries match the on-disk SHA-256 values for the examined files. The shared `MathHelper.java` SHA-256 is `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` on both sides.

Examined-file SHA-256 values:

- `1.11` `Entity.java`: `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`
- `1.11.1` `Entity.java`: `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96`
- `1.11` `LivingEntity.java`: `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`
- `1.11.1` `LivingEntity.java`: `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`

## Ordinary local pitch update

- `1.11`, Feather: `decompiled_minecraft/1.11/ornithe-feather/net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(float yaw, float pitch)`, lines 300–310.
- `1.11.1`, Feather: `decompiled_minecraft/1.11.1/ornithe-feather/net/minecraft/entity/Entity.java`, same method, lines 304–314.

Both bodies have the same operation order and expressions:

1. Save old `this.pitch` as float local `f`, then old yaw as float local `g`.
2. Add `yaw * 0.15` to yaw and narrow the result to float. The unsuffixed `0.15` promotes the float input and field to double for the expression.
3. Compute `this.pitch - pitch * 0.15` in double precision, then narrow to float at the explicit cast.
4. Clamp that float to `[-90.0F, 90.0F]` using the shared `MathHelper.clamp(float, float, float)` implementation (`x < min ? min : x > max ? max : x`).
5. Only after clamping, update `lastPitch` by `this.pitch - f`; then update `lastYaw` by `this.yaw - g`. The passenger look callback comes after these writes.

Finite-large witness (source-level IEEE-width consequence): starting with stored pitch `30.0F`, `lastPitch = 22.0F`, and a finite camera pitch input `Float.MAX_VALUE`, the double product `Float.MAX_VALUE * 0.15` is about `5.1042355E37`, still within float range. The cast remains finite; subtraction makes the new pitch large and negative, so the float clamp stores `-90.0F`. The subsequent previous-pitch update adds `-90.0F - 30.0F` to `22.0F`, yielding `-98.0F`. Thus the previous-pitch delta is based on the clamped current pitch, not the raw large result. Both releases do this identically.

## Dry active nonpassenger Elytra read

- `1.11`, Feather: `LivingEntity.java`, `flyingTick()` lines 1808–1826; `moveRelative(float,float)` lines 1386–1425; `getLookVector()` / `getRotationVector(float)` lines 1914–1926; `isFallFlying()` lines 2143–2145.
- `1.11.1`, Feather: corresponding methods at lines 1810–1828, 1388–1427, 1916–1928, and 2145–2147. The relevant branches and expressions match.

On a player tick, `flyingTick()` runs immediately before `moveRelative`. With fall-flying flag set, airborne, not riding, and a chest Elytra accepted by `ElytraItem.canFly`, the state remains active. For the ordinary dry path, both water and lava checks pass without needing the creative-flight bypass. `moveRelative` then takes its `isFallFlying()` branch when movement is locally controlled (or logical-side movement is authoritative). It calls `getLookVector()` and separately computes `i` from the stored `this.pitch`.

The actual look-vector path is `getLookVector() → getRotationVector(1.0F)`. The exact `tickDelta == 1.0F` branch passes `this.pitch` directly with `headYaw` to the rotation-vector calculation; it does not interpolate from `lastPitch`. Therefore the Elytra calculation consumes the current stored pitch written by `updateLocalPlayerCamera`, after its clamp, and does so in both releases. This establishes a reachable movement consumer for the setter's stored result, but no 1.11-to-1.11.1 change in the checked pitch operation or this consumer.

## Result

No operation delta is supported for the assigned slice. Do not infer an implementation cutover from this comparison. Full movement-pair coverage and intermediate patch-boundary status remain outside this report.
