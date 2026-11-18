# Independent audit verdict: Elytra glide lower gate, 1.18.1 → 1.18.2

**Verdict: ACCEPTED for the stated bounded claim.** The report's main distinction is source-correct: both `LivingEntity.travel` bodies perform the same pre-move `fallDistance = 1.0F` assignment only when the entry `getDeltaMovement().y` is strictly greater than `-0.5`; the 1.18.2 `Entity.move` reset is a subsequent, separate write and does not make that earlier assignment false. This verdict is source acceptance for the bound below, not complete movement-pair coverage or runtime parity.

## Bound and applicability

- Pair: exact Minecraft **1.18.1 → 1.18.2**, canonical local **Mojmap** decompilations.
- Operation: dry, ordinary local-player `LivingEntity.travel` on a tick where Elytra flight is already active, with ladder/vine in the player's feet block state; inspect the lower-gate write, ordinary climb handling and same-call `Entity.move` fall-distance reset. Wind and block-contact trigger behavior are outside this audit.
- Both `travel` methods require `isEffectiveAi() || isControlledByLocalInstance()`, then check water, lava, `isFallFlying()`, and only afterward fall through to the ordinary movement `else`. Thus for the stated dry, already-fall-flying local-player route, glide is selected before ordinary `handleRelativeFrictionAndCalculateMovement` / `handleOnClimbable`; the latter does not also run during that `travel` call. The feet climb predicate itself is unchanged: `onClimbable()` reads `getFeetBlockState()` and checks `CLIMBABLE` (or the usable-ladder trapdoor case); the tag contains LADDER and VINE in both releases.
- Applicability qualification: ordinary `LocalPlayer` jump initiation is guarded by `!this.onClimbable()` in both releases. The audited state is therefore an already-active glide that later reaches a climbable feet state (or an otherwise active flag); it is not a claim that jump input starts Elytra flight while standing on ladder/vine.

## Claim audit

1. **Strict pre-move write: confirmed unchanged.** In `LivingEntity.travel`, 1.18.1 lines 2070–2074 reads `Vec3 $$13 = this.getDeltaMovement()`, tests `$$13.y > -0.5`, and writes `this.fallDistance = 1.0F` before the glide math and `move` call (2098–2099). 1.18.2 lines 2074–2078 has the same order and condition, before glide math and `move` (2102–2103). `Vec3.y` is a double component; equality at `-0.5` does not enter the assignment; the assigned literal is float `1.0F`.
2. **Climb branch: confirmed mutually exclusive in `travel`.** The ordinary helper call occurs only in the final `else` (1.18.1 lines 2113–2117; 1.18.2 lines 2117–2121). Its `handleOnClimbable` processing precedes its own move call (1.18.1 lines 2157–2160; 1.18.2 lines 2161–2164). `onClimbable` uses feet state (1.18.1 lines 1429–1444; 1.18.2 lines 1433–1448), and both `BlockTagsProvider` sources list LADDER and VINE in `CLIMBABLE` (1.18.1 lines 566–577; 1.18.2 lines 564–575).
3. **1.18.2 same-call reset: confirmed separate and later.** After collision adjustment, `Entity.move` computes the adjusted movement vector's squared length; if nonzero fall distance and squared length `>= 1.0`, it ray-clips from current position to current position plus the adjusted vector using `FALL_DAMAGE_RESETTING` / water, and resets fall distance on a non-MISS hit (1.18.2 lines 562–575). The `FALL_DAMAGE_RESETTING` tag contains `CLIMBABLE` plus sweet berry bush and cobweb (BlockTagsProvider line 576); `ClipContext.Block.FALLDAMAGE_RESETTING` maps matching blocks to a full-block shape (ClipContext line 51). This can clear the glide write before position application when its gates and ray hit hold. It does not change the fact that the earlier `travel` write happened. Later `checkFallDamage` runs after movement/on-ground evaluation (Entity lines 590–593; body lines 979–991), and can add a downward non-grounded displacement after such a reset. 1.18.1 `Entity.move` has no corresponding ray-reset branch (lines 560–579); its `checkFallDamage` follows the position move.

## Exact source identity checked

The report input is `report.md`, SHA-256 `48fffb8e8c60d8039bcd0d1a90b8a2f0a3445c768969d76592b7c8a487abe5af`.

- **1.18.1 Mojmap:** adjacent provenance says version/requested version `1.18.1`, namespace `mojmap`, run `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, Java 25.0.3 / Gradle 9.7.1 / Vineflower 1.12.0; client SHA `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`. `mojmap.ready.json` agrees on version, mapping, client SHA and provenance SHA `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`. Source-manifest SHA is `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`.
- **1.18.2 Mojmap:** adjacent provenance says version `1.18.2`, namespace `mojmap`, Java 25.0.3 / Gradle 9.7.1 / Vineflower 1.12.0. The ready marker agrees on exact version and mapping, client SHA `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`, and source-manifest SHA `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. The adjacent `artifacts.sha256` independently lists that client SHA plus exact 1.18.2 client-mappings and remapped-client-JAR entries. The checked adjacent version directories contain the provenance, ready-marker, artifact-manifest and source-manifest sidecars described here.
- For each release, the adjacent `mojmap.sources.sha256` manifest entries match fresh SHA-256 hashes of all six relevant decompiled files: `LivingEntity.java`, `Entity.java`, `Player.java`, `LocalPlayer.java`, `BlockTagsProvider.java`, and `ClipContext.java`. The manifest identities and source hashes agree with the supplied report. No source regeneration was run.

References are relative to `decompiled_minecraft/<version>/mojmap/`; exact line ranges above identify the compared members. The 1.18.2 ray reset is bounded to the inspected move path and conditions; no claim is made that every ladder/vine glide trajectory crosses the ray or finishes with zero fall distance.

## Audit identity

- Input report SHA-256: `48fffb8e8c60d8039bcd0d1a90b8a2f0a3445c768969d76592b7c8a487abe5af`
- Output: `c-glide-climb-1181-1182-lower-gate-audit/verdict.md`
- Output SHA-256: supplied in the completion handoff after this file is finalized.

