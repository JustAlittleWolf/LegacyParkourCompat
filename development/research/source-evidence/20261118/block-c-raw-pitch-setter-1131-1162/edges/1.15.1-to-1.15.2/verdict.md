# Independent C audit: 1.15.1 to 1.15.2 local turn pitch and dry Elytra use

## Frozen verdict

**No source change** in ordinary local mouse-turn pitch scaling, pitch field widths, current/previous pitch clamping, or the active dry Elytra pitch inputs between exact 1.15.1 and 1.15.2 Mojmap sources. The relevant `Entity.java` and `MouseHandler.java` files are byte-identical across the pair; the relevant `LivingEntity.travel` branch is line-for-line identical, although the full `LivingEntity.java` files differ.

## Evidence identity and integrity

Canonical trees read-only: `decompiled_minecraft/1.15.1/mojmap/` and `decompiled_minecraft/1.15.2/mojmap/`.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 (matches ready marker) | Source facts |
|---|---|---|---|---|
| 1.15.1 Mojmap | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | ready; 3,329 files / 17,524,592 bytes; source manifest, artifacts manifest, diagnostics, provenance hashes match ready declarations; run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`; official Mojang mappings; Vineflower 1.12.0, Java 25.0.3+9-LTS. Client SHA-256 `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`; mappings SHA-256 `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34`. |
| 1.15.2 Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` (computed; ready marker has no `provenanceSha256` field) | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | ready; 3,332 files / 17,543,871 bytes; source manifest, artifacts manifest, diagnostics hashes match ready declarations; batch `1.15.2, 1.16.5, 1.17.1, 1.18.2`; Mojmap; Vineflower 1.12.0, Java 25.0.3+9-LTS. Client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; mappings SHA-256 `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777`. |

Actual SHA-256 values for all three source files below match each release's source manifest. `Entity.java` is identical in both releases at `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `MouseHandler.java` is identical at `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b`. `LivingEntity.java` hashes are `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` (1.15.1) and `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` (1.15.2). Relevant travel bodies were compared directly below. The ready markers' historical `outputPath` values point to preparation staging trees; the canonical source files and manifests in the shared tree match the declared hashes.

## Compared source and behavior

- `net/minecraft/client/MouseHandler.java`, `MouseHandler.turnPlayer`, lines 192-223: both releases use the same sensitivity curve (`f = sensitivity * 0.6F + 0.2F`, `g = f * f * f * 8.0`), optionally smooth the accumulated deltas, clear accumulated deltas, apply Y inversion as an integer sign, and call `player.turn(j, k * n)`. There is no `Player.turn` override; this resolves to `Entity.turn`.
- `net/minecraft/world/entity/Entity.java`, `Entity.turn`, lines 326-337: both releases compute pitch `f = e * 0.15` and yaw `g = d * 0.15` as `double`; write current pitch as `(float)(this.xRot + f)` and current yaw as `(float)(this.yRot + g)`; clamp current pitch to `[-90.0F, 90.0F]`; independently write previous pitch/yaw using the same deltas; clamp previous pitch to the same bounds; then notify the vehicle. Pitch/yaw fields are `float` (declaration lines 126-129). The double addition is converted to float on each write. Yaw is not clamped. Current and previous pitch are clamped independently; previous pitch is not copied from current pitch by this method.
- For ordinary finite mouse updates whose float conversion remains finite, each pitch assignment first stores the un-clamped sum (which can temporarily be outside `[-90, 90]`) and then immediately clamps it. The first write does not alter the source-level operation order or become a distinct persisted movement state after `turn` returns. This same ordered write/clamp happens for `xRotO` after current pitch and both yaw writes.
- `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel`, lines 1831-1871: under the local/effective-AI movement gate, a dry player without flight ability enters the non-water/non-lava path; when `isFallFlying()` is true, the Elytra branch reads `getLookAngle()` and separately computes `l` from current `xRot`. `Entity.getLookAngle` at `Entity.java:1661-1662` calls `calculateViewVector(this.xRot, this.yRot)`, using current angles directly. It does not use `xRotO`, `getViewVector` interpolation, or the pre-clamp local mouse input. The angle-derived look vector feeds the pitch-dependent lift/dive and horizontal steering terms before velocity damping and movement. These reads and gates are identical in both releases.

## Movement meaning and boundary

A local pitch update affects active dry Elytra travel through the current pitch/yaw after the turn method's clamp; the previous pitch field is not consumed by this travel branch. There is no 1.15.1-to-1.15.2 behavior delta for the assigned operation. This endpoint comparison does not establish when the behavior was introduced or prove other 1.15 patch boundaries.

## Phase-two independent report comparison

The authorized source report at `c-raw-pitch-setter-1151-1152-source/report.md` was rehashed before reading and matched its assigned SHA-256 `144e2a31b2c2840f63489eef1deee82c1a1ea7cca24341ed2bdb7b04f200656e`.

The report agrees with this frozen verdict: no 1.15.1→1.15.2 difference in the complete `Entity.turn`, `MouseHandler.turnPlayer`, or active dry `LivingEntity.travel` slices. Its rechecked ready/provenance/source identity values agree with this audit. It independently adds a finite caller-arithmetic witness for the unsmoothed local mouse path (conditional on a finite accumulator value) and traces the active travel call path through `LocalPlayer.aiStep` and `Player.travel` into `super.travel`. Those details refine reachability context; they do not alter the pitch setter or Elytra-consumer verdict. The report explicitly limits that witness to caller arithmetic and does not establish a device event or an out-of-range live pitch; this is consistent with the setter clamp and this audit's scope.

**Comparison disposition:** agreement; no finding-level discrepancy or reopened source claim. This endpoint comparison does not establish an intermediate patch boundary or cover other pitch writers.