# Independent re-review — MCPK follow-up r4 (2026-10-08)

## Reviewed object and limits

The reviewed r4 object is bound to:

- Commit: `20894469259abb7761cab1356882cf5e113814da`
- Path: `workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r4.md`
- Git blob: `e8b5b4151a3e032258837933fdb591db128bafe6`
- Raw-file SHA-256: `8c169122cfd57cb44a6100c9daa6ec74125313112b362c015e13a6a845a885bc`

It is present in the supplied owner tip `66d9e2735a88f2f0bf35d5f5499efdb6f4de2232`. The comparison object r3 remains pinned by the r4 report; corrected r2 and the prior review decision were also checked at their immutable identities. Review scope was limited to those MCPK artifacts, the r4-cited exact-version source files/dependencies and manifests, and MCPK page revision 3476. The Minecraft Wiki lane, ordinary source-pair reports, and implementation were not inspected. No tests, builds, Gradle, runtime, push, or new workers were used.

## Disposition

**REQUEST CHANGES** for two bounded r4 edits. The two corrections requested by the earlier review are present and accepted. The new source observations are otherwise accepted at the cited endpoints, with their stated gaps retained.

### Earlier corrections — ACCEPT

- **1.15.2 filename — ACCEPT.** The exact source is `ready/1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java` (lowercase `s` after “Soul”), SHA-256 `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`. Its entry is present in the 1.15.2 source manifest. The corrected r4 path and bounded bed/Soul Sand/top-slab/bottom-slab examples are accurate; the immutable slipperiness r2 scope is preserved.
- **Powder snow stuck response — ACCEPT.** `PowderSnowBlock.entityInside` calls `makeStuckInBlock` with `(0.9F, 1.5, 0.9F)` at lines 53–56. The cited 1.17.1 `Entity.java` lines 2150–2153 set `fallDistance = 0.0F` and store the multiplier. The `Player.java` override at lines 1581–1585 delegates only when the player is not flying. File hashes in r4 match the source manifest. The evidence correction closes the citation gap without adding a fall-damage or health-emulation claim.

### Y=256 jump consumer — REQUEST CHANGES to the source label/call path; ACCEPT the bounded branch

The endpoint behavior is supported, but r4 calls the 1.13.2 body `LivingEntity.aiStep`. In Feather `net/minecraft/entity/living/LivingEntity.java` (SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`), the body at lines 1852–1920 is `LivingEntity.mobTick()`; the water-depth jump branch is lines 1903–1914. Add the exact player and producer edges:

- `LivingEntity.tick()` in the same file calls `super.tick()` and then `this.mobTick()` at lines 1689–1692 and 1752. Feather `net/minecraft/entity/living/player/PlayerEntity.java` (SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`) overrides `mobTick()` and calls `super.mobTick()` at lines 463–481. This makes the cited base jump branch player-reachable.
- Feather `net/minecraft/entity/Entity.java` (SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`) calls `m_03231680()` from `baseTick()` at line 394; that helper calls `checkWaterState()` at lines 947–951. The water overlap scan at lines 2509–2570 stores the measured depth in `f_85121000`; the player-reachable `mobTick()` branch reads that value to select normal jump or water jump.

These call edges confirm the described jump consumer, but they do not establish a Y=256 condition or explain the reported symptom’s initiating state change. The 1.15.2 and 1.16.1 water-height/fluid-height producer and jump branches cited in r4 also match their sources. Keep the cause and exact fix boundary open: 1.16.0 has no ready source, and the `resetPos()` loop is not a caller of this jump branch.

### Expanded 1.14 endpoint claims — ACCEPT with boundaries retained

The new source identities and bounded statements check out:

- Axis collision order at `Entity` lines 724–780 is Y, then the smaller horizontal displacement first, with X first on a tie. The fixed Y→X→Z 1.13.2 Feather endpoint comparison is also accurate.
- `Player` dimensions, `Player.tick()` ordering, pose choice/fallback, `canEnterPose`, dimension refresh, feet anchoring, and the server-side widening move are supported by the cited lines.
- `LocalPlayer.aiStep()` blocks double-tap sprint while sneak is held but its explicit sprint-key branch has no sneak predicate; `sendPosition()` emits state changes, and `ServerGamePacketListenerImpl` applies the commands. This is an endpoint input/packet path, not proof of a trajectory exploit.
- `LivingEntity.onLadder()` accepts ladder, vine, scaffolding, and the specified open trapdoor over a matching ladder. The cited ladder/vine source supports the stated attachment and support-chain limits. The 0.6 step height, grounded/downward plus horizontal-collision gate, candidate comparison, and movement result handling are accurate at 1.14.4.

These source endpoints do not verify blip trajectories, wall-X conservation, hover, or a 1.14.0 change point. r4 correctly keeps those dynamic symptoms open. The ready tree has 1.14.4, not 1.14.0.

### Blip revision 3476 — ACCEPT page provenance; REQUEST CHANGES to route one omitted subclaim

The browser showed `https://www.mcpk.wiki/wiki/Blip?oldid=3476`, revision as of 2022-01-24 12:37. Its 1.14+ section says blip-ups and wall blips were patched, normal blips remained, and describes a separate blip-down setup. r4 accurately treats these as wiki-reported behavior rather than source proof; the 1.14.4 movement/step consumer cannot establish those dynamic sequences or their patch boundary.

The same immutable page has a separate **1.15+** statement that blipping cancels fall damage in some cases and was patched in 1.16. r4 does not route that subclaim. Add it as a page-reported, unverified symptom: do not infer its mechanism or release boundary from this page. Keep damage calculation out of scope; if the question is a direct player `fallDistance` state write on the movement path, that producer needs its own exact-source evidence. The 1.16.0 ready tree is absent, so the page’s broad “1.16” cutoff is not source-pinned here.

The research fetcher’s earlier canonical/raw/API attempts were inaccessible (403 or unavailable URL); the page content above was read in the normal browser at the permanent revision URL. Other MCPK revision identities and their previous dispositions are inherited from r4/prior snapshots, not represented as newly fetched in this re-review.

## Source manifest and artifact identities

All cited source file SHA-256 values checked for this re-review match their corresponding ready-tree source manifests. I rehashed each ready marker, provenance JSON, source manifest, and artifact manifest; the listed client hashes also match the `client.jar` entries in the artifact manifests.

| Ready source root | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Ready marker SHA-256 | Provenance SHA-256 | Client SHA-256 |
|---|---|---|---|---|---|
| `1.13.2/ornithe-feather` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` |
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |
| `1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` |

The 1.13.2 ready marker identifies Feather `feather-gen2-1.13.2+build.2-mergedv2.jar` and its `.tiny` mapping. This re-review preserves r4’s **UNPROVEN** Feather-derived/original-JAR equivalence caveat. The exact 1.14.0 and 1.16.0 source roots are absent; no intermediate patch evidence was inferred from endpoint trees.

## Requested edits

1. In the Y=256 paragraph, name the 1.13.2 method `LivingEntity.mobTick()` and cite the `LivingEntity.tick()` and `PlayerEntity.mobTick()` call edges plus the `Entity.baseTick()` water-depth producer call.
2. Route Blip revision 3476’s 1.15+ fall-damage-cancellation statement as an unresolved page-reported symptom, with the direct fall-distance and damage-scope distinction above. Keep the exact 1.16 patch boundary open.

The source pair remains partial. This review does not establish source-pair completion or authorize movement implementation.
