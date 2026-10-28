# Independent source audit: 1.18.2 to 1.19 ordinary movement inventory

## Verdict and scope

Independent full-manifest traversal and path-ledger verification pass. The inventory is a useful bounded comparison, but it does not close the pair: direct received-state handling, generic support-query consumers, alternative input providers, and provider/world dependencies remain outside or open. This audit makes no pair-freeze or runtime-parity claim.

I independently hashed every Java source listed by both canonical manifests and reconciled the author's full path/status/old-hash/new-hash TSV against those hashes. Both source trees were complete and internally consistent. The independent machine record is `independent-hash-audit.json`.

## Confirmed claims

- The Swift Sneak factor is in the 1.19 local input path: `LocalPlayer.aiStep` adds `EnchantmentHelper.getSneakingSpeedBonus`, and `KeyboardInput.tick` applies that factor only when slow input is active. The helper returns enchantment level times `0.15F`; the feature is newly registered as a legs enchantment in 1.19. At level zero the clamped factor is exactly the old `0.3F`. Thus the positive-level branch is contextual to a 1.19 feature and does not demonstrate a difference on the shared old-feature state. Equipment/resource availability and non-keyboard input implementations remain outside this audit.
- Contact modifier call sites use the legacy support query in 1.19: `tryAddSoulSpeed`, `tryAddFrost`, and `onChangedBlock` call `getBlockStateOnLegacy`; the helper calls `getOnPosLegacy`, which delegates to the common calculation with `0.2F`. The 1.18.2 helper used the same `floor(y - 0.2F)` and air-to-fence/wall/gate-below fallback. Generic 1.19 `getOnPos()` uses `1.0E-5F`, but that is not the query used at these modifier call sites. This confirms those call-site rewrites preserve their support offset/fallback, subject to the report's explicit unresolved state-provider semantics.
- The report correctly leaves collision/provider semantics, alternative `Input` implementations, broad generic `getOnPos()` consumers, and full pair coverage open. Its traversal does not establish direct response closure.
- Supplemental exact body comparison found `LivingEntity.travel(Vec3)` byte-identical across these sources (method SHA-256 `b357fd9f66141f9e2bf50e30ec9a91f043282d6a31fccc33fcba96730dd4a745` on both), as is `jumpFromGround` (`d0524bea05cd60ba77bdd68d71559670d2fbb23cc7a948e6ff790fe0c8131dfa`). This closes those two method-body comparisons only; called helpers and complete travel dependency closure remain open.

## Review additions and qualifications

- `LocalPlayer.sendPosition()` differs in the assignment caching the auto-jump setting: direct boolean field read in 1.18.2 versus `autoJump().get()` in 1.19. The movement packet selection/coordinates in the method body otherwise match. This is an option-API migration candidate; equivalence of the configured value should be checked against the option declaration/serialization before calling the whole sender path unchanged. It does not by itself establish changed movement behavior.
- Direct correction handlers are not all untouched. `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` is byte-identical across the pair (method SHA-256 `52c3503f8f41c29881911840afbac34ee525e3f952b9a11767d501e42ac50e3e`), as is `handleSetEntityMotion` (`d1a6133f8f41c29881911840afbac34ee525e3f952b9a11767d501e42ac50e3e`). `handleTeleportEntity` changes `setPacketCoordinates` to `syncPacketPositionCodec`; the handler's controlled-local-instance guard means this is not the direct local-player correction route, but the changed remote/controlled entity path is not audited here. `handleRespawn` also changes dimension registry lookup and records last-death location; respawn-state closure remains excluded. Keep direct response ordering/state as a sibling dependency rather than infer it from the ordinary-input inventory.
- The 1.19 `Entity.move` changed hunk adds movement-emission bookkeeping for climbable/powder-snow contact and a STEP game event condition. The inventory notes it and does not find a player position/velocity write in that hunk; game-event/sensor consumers are not closed by this ordinary-player audit.

## Independently verified identities

| Input | SHA-256 / counts |
|---|---|
| 1.18.2 manifest `decompiled_minecraft/1.18.2/mojmap.sources.sha256` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; 4,236 entries, 4,236 live Java files, 0 missing/extra/mismatch |
| 1.19 manifest `decompiled_minecraft/1.19/mojmap.sources.sha256` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; 4,430 entries, 4,430 live Java files, 0 missing/extra/mismatch |
| Author path ledger `java-path-diff.tsv` | `35d872f8492eba576e4cbe16c84ca9df0a469973a7def086b7d9118af04126c2`; 4,541 rows; every path/status/hash tuple independently matches |
| Author inventory | `e866cd82ed82a5650004588cac9d71be0101e7e3302863c048bd98a3bea3f4f0` |
| Author source identity JSON | `443e5666ab072915436241a4647ec1ee83f52f96e76a5c3a5cdab19c5346ab44` |

Independent diff counts: 305 added, 111 removed, 1,530 modified, and 2,595 unchanged. Relevant reviewed source file hashes are retained in the independent hash record's parent verification and the canonical per-source manifests; no canonical sources were written.

## Limits

The method checks above do not replace the full local-player travel call graph, direct packet/state writer audit, contact-state provider and collision closure, or independent full-coverage review. No source pair freeze is recommended from this bounded audit.
