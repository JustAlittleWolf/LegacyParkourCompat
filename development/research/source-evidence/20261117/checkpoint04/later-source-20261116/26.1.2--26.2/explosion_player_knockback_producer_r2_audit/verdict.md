# Independent source audit: explosion player-knockback producer r2

## Verdict

**ACCEPT** the bounded producer comparison in `build/later-source-20261116/26.1.2--26.2/explosion_player_knockback_producer_revision_r2/coverage-r2.md`, SHA-256 `25E3815D15F4574D28B65502571AE5864C2A0BED55A3B80D1592ECDA10332854`.

The r2 correction fixes the exact calculator-gate description that blocked r1. The inspected server producer, calculations, storage gates, and packet field remain bounded NO_DIFF across 26.1.2 and 26.2. This is not full-pair coverage, client-trajectory evidence, or a claim that historical movement executes on a logical server.

## Correction and source evidence

The exact sources are the `unobfuscated` publications for 26.1.2 and 26.2. The actual provenance sidecars are at `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` (SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`). Ready sidecars are at the release roots and bind source manifests `unobfuscated.sources.sha256`; current hashes match the r2 report's declarations. I rechecked relevant source bytes and manifest rows for `SimpleExplosionDamageCalculator.java` and `ServerExplosion.java`; their hashes match the report and manifests. The prior independent r1 audit verified all six cited source files on both sides and the unchanged producer/packet claims; r2 changes only the calculator-gate description and bounded-verdict wording.

`SimpleExplosionDamageCalculator.getKnockbackMultiplier` in both sources tests `entity instanceof Player player && player.getAbilities().flying`; it does not test `player.isCreative()`. The r2 statement that this calculator returns zero for any flying `Player`, regardless of creative status, is accurate. The local identifier `creativeFlying` does not introduce a creative-mode test.

The separate `ServerExplosion.hurtEntities` recording branch in both sources tests `entity instanceof Player player && !player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)` before storing the vector in `hitPlayers`. Thus it excludes spectators and creative-flying players, but does not exclude non-creative flying players. This is a distinct condition from the calculator's any-flying-player zero multiplier; the corrected report keeps them separate.

## Bounded evidence reused

The prior r1 audit found that the paired `ServerLevel.explode`, `ServerExplosion.explode`, `ServerExplosion.hurtEntities`, base and simple calculator methods, and `ClientboundExplodePacket` field/codec support the reported no-difference calculation/order/packet placement. That audit confirmed all 12 cited Java-file hashes against the source manifests. Those unchanged claims and identities are reused; this audit rechecked the changed gate wording and relevant source lines/hashes, not the full source trees.

No source difference is found in the formula, operation order, any-flying-Player calculator gate, separate spectator/creative-flying hitPlayers-recording gate, recipient-distance gate, or optional-vector placement. Caller-supplied calculator values, player attributes, exposure, event callers, packet transport/application, and other pair movement areas remain open.

No backlog, product, wiki, Git history, artifacts, build, tests, source generation, or runtime behavior were inspected.
