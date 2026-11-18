# Blind source verdict: 1.15 → 1.15.1 yaw and ordinary dry acceleration

## Verdict

No source-confirmed behavioral or precision delta was found in either bounded operation. The normal local mouse-turn route and the first ordinary dry ground/air yaw-relative acceleration route are byte-identical in the exact Mojmap source files for 1.15 and 1.15.1. This rejects a patch-boundary claim for these operations only; it does not close the pair's other movement coverage.

I treated “ordinary direct local yaw setter” as the ordinary mouse-input yaw write, `MouseHandler.turnPlayer` → `LocalPlayer.turn` (inherited `Entity.turn`). I also checked the protected `Entity.setRot(float,float)` setter to disambiguate the term. Both forms are unchanged; `setRot` is not the regular mouse-turn route.

## Exact source identity and byte verification

Version-root evidence: `decompiled_minecraft/1.15/mojmap.provenance.json` and `mojmap.ready.json` identify version `1.15`, namespace `mojmap`, and run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`; the raw client SHA-256 is `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. The root `mojmap.sources.sha256` hashes to `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`, matching both provenance and ready records.

`decompiled_minecraft/1.15.1/mojmap.provenance.json` and `mojmap.ready.json` identify version `1.15.1`, namespace `mojmap`, and the same preparation run; its raw client SHA-256 is `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`. Its root `mojmap.sources.sha256` hashes to `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`, matching both records. The two client inputs and source-manifest identities are distinct and the exact release IDs agree across the version-root records.

For every cited source below, I checked the on-disk file SHA-256 against that version root's `mojmap.sources.sha256`. The following per-file hashes match in both roots, so each cited source file has identical bytes across this pair:

| Mojmap source file | SHA-256 in each version root |
| --- | --- |
| `net/minecraft/client/MouseHandler.java` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/phys/Vec3.java` | `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909` |
| `net/minecraft/util/Mth.java` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |

## Bounded operation review

All line references are to each version's `mojmap/` source tree; the cited file hashes above bind the bytes to the version-root manifests.

- **Ordinary local yaw write:** `MouseHandler.turnPlayer`, lines 192–223, passes mouse deltas `j` and `k*n` (both `double`) to `minecraft.player.turn`. `LocalPlayer.aiStep`, lines 625–780, reaches `super.aiStep()`. Inherited `Entity.turn(double,double)`, lines 326–338, computes the yaw increment as `double g = d * 0.15`, then assigns `this.yRot = (float)(this.yRot + g)` and updates `yRotO` with the same double-add-then-float-cast pattern. Thus the angle stays binary64 through multiplication and addition, then narrows once to the binary32 yaw field. `setRot(float,float)`, lines 310–313, instead stores `f % 360.0F` (and the matching pitch expression) in float arithmetic; it is not called by ordinary mouse turn. These lines and their gates/order are unchanged.
- **Actual local travel route:** `LocalPlayer.aiStep` calls `super.aiStep`; `LivingEntity.aiStep`, lines 2207–2288, damps `xxa` and `zza` by `0.98F` and calls `travel(new Vec3(xxa,yya,zza))` at line 2287. In `LivingEntity.travel(Vec3)`, lines 1831–1912, the controlled/effective-AI path and dry, non-flying, non-fall-flying path selects the ordinary branch at lines 1885–1912. It reads block friction `v`, sets `w` to `v*0.91F` on ground or `0.91F` in air, calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)`, then applies climbable handling and moves before multiplying horizontal velocity by `w`. `getFrictionInfluencedSpeed`, lines 2006–2012, uses `getSpeed() * (0.21600002F / (v*v*v))` on ground and `flyingSpeed` in air. Ground and air gates, input speed selection, yaw-relative call position, collision/move order and post-move factors are identical in both releases.
- **First yaw-relative vector math:** `Entity.moveRelative`/`getInputVector`, lines 1063–1078, adds the calculated vector to existing velocity. `Vec3` declares `double` x/y/z fields and constructor (lines 10–20), `normalize()` with double intermediates (34–36), `scale(double)` (84–86), and `lengthSqr()` returning double (104–106). The input square length and `1.0E-7` gate are double; vectors with length squared above `1.0` normalize, then `scale(float f)` widens `f` to the `scale(double)` parameter. Yaw `g` is float. The radians factor is explicitly narrowed to float before the float multiply; `Mth.sin(float)` and `Mth.cos(float)` return float (Mth lines 25–30). Final x/z expressions combine double vector coordinates with those float trig values in double arithmetic and construct a double-coordinate `Vec3`. The operation sequence and casts match exactly on both sides.

## Acceptance boundary

**Claim verdict:** not accepted as a 1.15→1.15.1 difference for ordinary local yaw mutation or ordinary dry ground/air yaw-relative acceleration. Exact-source identity, cited method bytes, numeric widths/casts, finite gates (none in these two paths), and order were checked for this bounded claim. Pair-wide coverage and unrelated yaw/movement paths remain open.

## Phase-two independent audit

The author report's SHA-256 before review was `667c649c16b6847b292fe0df7cf32ac880892dfbd4e28d4cc3709a87181a82ef`, exactly the pinned identity. I reviewed the report against the exact Mojmap sources. Its bounded no-delta verdict is supported: the cited Entity, LivingEntity, LocalPlayer and Player files match their version-root manifest entries and are byte-identical across the pair. Direct yaw writes (`setRot`, `turn`, `absMoveTo`, `moveTo`) in Entity therefore have the same bytes, casts, finite gates and ordering described in the report.

I also checked the ordinary local input route and numeric dependencies independently: LocalPlayer `isEffectiveAi()` returns true (490–492), `serverAiStep()` copies input impulses when it is the camera entity (607–617), `aiStep()` calls `super.aiStep()` (778), and LivingEntity `aiStep()` runs effective AI before damping x/z by `0.98F` and calling `travel` (2253–2288). Player's non-ability-flying path delegates to `super.travel`; its base movement attribute is `0.1F` and nonsprinting `flyingSpeed` is `0.02F` (Player 191–194, 520–525, 1471–1482). Vec3's components and scale/length operations are double, while Mth `sin(float)`/`cos(float)` return float (Vec3 10–20, 34–36, 84–86, 104–106; Mth 25–30); both source files have identical manifest-listed bytes across the pair. The stone witness is source-supported: `Blocks.STONE` uses properties without a friction override (Blocks 24), and `Block.Properties.friction` defaults to `0.6F` (Block 854 in 1.15; 858 in 1.15.1). Block.java differs elsewhere between the releases, but these friction lines and the relevant stone declaration were checked on both sides and retain the same value.

**Independent verdict:** the report's yaw-precision and first ordinary dry ground/air acceleration claims are correct for this bounded slice; no 1.15→1.15.1 difference is established. No runtime or build claim is made. The author report was not edited.