# Independent Minecraft Wiki snapshot review — 2026-10-08

## Scope

Intent: independently assess the six newly frozen Wiki-lane records and their fetch log against their exact snapshot commits, exact ready sources, reachable player paths, state/resource inputs, and recorded Wiki provenance. These decisions apply only to the bounded records below. They do not close the Wiki lane or any source pair. No implementation, MCPK material, normal source-pair discovery reports, or runtime evidence was reviewed.

## Snapshot decisions

| Record | Decision | Exact immutable binding |
|---|---|---|
| Jump apex | **ACCEPT** | `ab102b5d5da292e76460801ab362711acd734996`; `workflows/wiki-audit-2026-10-07/jump-apex-1.8.9-to-1.9.4.md`; blob SHA-256 `3a413b1e0e62cb5da276a6209779e60c3bd97874d7c166f5999142f067bdfd3a` |
| Sprint swimming entry and water drag | **REQUEST CHANGES** | `bb0c70e808818dc6b1155a016e4434d6867cbea6`; `workflows/wiki-audit-2026-10-07/sprint-swimming-1.12.2-to-1.13.2.md`; blob SHA-256 `334d7608e28efd543d2ecb5f417ac2cc758f9e38688f422e438a46f03ffc802d` |
| Unconnected iron-bars shape | **ACCEPT** | `9fb2bb9f0fb399100e9bd455930757db2f327b61`; `workflows/wiki-audit-2026-10-07/iron-bars-unconnected-shape-1.8.9-to-1.9.4.md`; blob SHA-256 `198b766542b8b790c919e1609271112cb2863eba30083273b9f97e009ba25b17` |
| Creative-flight retained paths | **ACCEPT — bounded subpaths only** | `bf7847b53d53300c40c24837f3eead1076022726`; `workflows/wiki-audit-2026-10-07/creative-flight-retained-1.8.9-to-1.9.4.md`; blob SHA-256 `854bc8c2bd7f6c4d4df568fa870c4034ec533dff1901d5631f966af4a6f411cd` |
| Open trapdoor above same-facing ladder | **ACCEPT** | `8f8480df58973eac0099c285b4ef3f91dbe9346d`; `workflows/wiki-audit-2026-10-07/climbable-trapdoor-ladder-1.8.9-to-1.9.4.md`; blob SHA-256 `0bb0a81b4f9b78abac8eb8c82a69d736fad449f6bf2830c4ba30f01ae8aabb08` |
| Bed-walk first-introduction lead | **OUT OF SCOPE** | `b1560113b61d94c4fe31c05fc20a2aa6d045e4ca`; `workflows/wiki-audit-2026-10-07/bed-walk-lead-before-baseline.md`; blob SHA-256 `dc2ae395bff6617c1155d4fd6a4137dc97c8a5637078a436a7248f35849d6daf` |
| Wiki retrieval/access log | **ACCEPT — provenance record only** | `5e353798e5411b00c766905a1ed7c34aaa493c16`; `workflows/wiki-audit-2026-10-07/wiki-page-fetch-log.md`; blob SHA-256 `133fbf4e21214d3d7017ddcf4696d04a0fabd13d9cb8b417e1512d2691ba4131` |

For identity cross-checking, the corresponding Git blob IDs (SHA-1) are, in table order: `6959522c8e79dfccd81732f2e03ddc1d3390106d`, `4b1b83f8ce267e5e9b33a9a6670a86029c0824cb`, `b2af810884ae283b84f3de35a3b79794ae162312`, `ac8a96ce0bb23f9c5592cd439cd93abeaaeb525f`, `4b80f513719ade18507ed9e8515b617be663d94a`, `e80ac9919c2ee4c40b6c5d1712479e6d95c60b7d`, and `75852435f5814d2dfd776eabad033db6cf021c45`. Each SHA-256 above was computed from the exact Git blob bytes, not a floating worktree copy.

## Confirmed findings and required correction

### Jump apex — ACCEPT

The cited 1.8.9 and 1.9.4 `LivingEntity.java` hashes (`082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` and `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`) match the exact ready-tree source-manifest rows. The local-player override reaches `LivingEntity.mobTick()` and is locally controlled, so the non-local 0.98 velocity damping is skipped. In both versions the near-zero cutoff precedes the jump-input branch; travel then moves using current Y velocity before the normal-air `-0.08` and `* 0.98F` update. The cutoff changes from `< 0.005` to `< 0.003`.

Replaying that operation order from the binary float `0.42F` gives the recorded tick-5 peak `1.2491870787446813`; at tick 6, the 1.8.9 incoming `0.0030162615090425808` is cut to zero, while the 1.9.4 value remains and raises the cumulative displacement to `1.2522033402537238`. The table is an exact-source recurrence for its stated ordinary, unassisted, local ground-jump conditions; it is not runtime validation or a result for other jump paths. The official HistoryLine search result available to this review gives the same 15w45a rounded values. The Jumping article itself remains a recorded robots.txt fetch failure, so the article's current contents and an article revision were not established.

### Sprint swimming — REQUEST CHANGES

The water-travel multiplier claim is supported for the bounded branch: with the stated enchantment/effect exclusions, both versions use `g = 0.02F` for input acceleration; 1.12.2 retains the 0.8 base horizontal multiplier, while 1.13.2 selects 0.9 for sprinting and applies it after movement. The relevant `LivingEntity` and `PlayerEntity` hashes match their ready manifest rows. The 1.13.2 submersion writer is called before `PlayerEntity.super.tick()` and tests `FluidTags.WATER`. In the exact 1.13.2 client JAR (`3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`), `data/minecraft/tags/fluids/water.json` has raw-entry SHA-256 `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` and lists `minecraft:water` and `minecraft:flowing_water`. `FluidTags.java` and `TagManager.java` hashes (`b9d4c2d93f4594f27e235e2d5973cd0c81d269597cbdc400a11333cc617247aa` and `fbe18a52f638c2d6d38713034388a951bd9de8fb3acf647dd31acd9d3b3bf252`) match the 1.13.2 source manifest. This verifies the vanilla default tag input; custom/reloaded tag values are outside the finding.

Two corrections are required:

1. The 1.12.2 `LocalClientPlayerEntity.java` hash printed in the snapshot is `01a58e94d8c6ff98a8e3794227cdc76a5fcbbdad795c70c9cf28854aff9823cc`; the exact file and its manifest row instead hash to `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. The 1.12.2 ready record's overall source-manifest hash is valid; this is a cited-file hash transcription error.
2. The narrative says both 1.12.2 sprint-start paths require `onGround`. The source's double-tap branch at lines 718–732 does, but the separate sprint-key branch at lines 734–740 does not check `onGround` and has no water exclusion. With forward input at least 0.8, the normal food/flight gate, no item use or blindness, and the sprint key pressed, a non-sprinting player can start sprinting in water through that 1.12.2 path. Thus 1.13.2's submerged double-tap eligibility is a new path when airborne, but submerged sprint start is not new for the direct-key path. The 1.13.2 direct-key branch instead explicitly accepts out-of-water or fully submerged states, narrowing the 1.12.2 key path for partially submerged water states. Scope the new-entry claim to the double-tap path and document the key-path difference separately.

The recorded Wiki URL/revision `Swimming?oldid=2726686` matches the checkpoint fetch log, which says an older cached revision was opened. Direct re-open of that URL in this review was inaccessible through the retrieval tool; I do not treat that failure as evidence that the page or claim is absent.

### Iron bars — ACCEPT

The four cited registration/shape files (`Block.java` and `PaneBlock.java` in 1.8.9 and 1.9.4) match their exact ready-manifest hashes. Both registries construct iron bars as `new PaneBlock(Material.IRON, true)`. With no connected neighbors, 1.8.9's producer adds the full-length 2/16-wide X and Z strips; 1.9.4 adds only `SHAPES[0]`, the central 2/16 × 2/16 post. The source chain is reachable for a player: `PlayerEntity` inherits `LivingEntity` and `Entity`; `Entity.move()` queries `World.getCollisions(this, ...)`, which dispatches the block collision producer with that entity. Those `Entity.java`/`World.java` caller hashes also match both ready manifests. This accepts only the unconnected iron-bars state. Neighbor predicates and connected states remain open as stated.

The snapshot's `Glass_Pane?oldid=2728132` link matches the fetch log, which records the pane page opened from an older cached revision; the Iron Bars page is recorded as blocked by robots.txt. The Wiki page is a lead, while the exact source pair establishes this shape.

### Creative flight — ACCEPT, bounded subpaths only

The cited eight Java files for the local toggle, player flight branch, water/lava travel path, and cobweb dispatch/override all match their ready source-manifest rows. For a locally controlled player with flight permission, active creative flight, and no vehicle, the sprint multiplier and 0.6 vertical damping match in the compared branch; both versions bypass the ordinary water/lava travel branches for a flying player and bypass cobweb slowdown in `PlayerEntity.onCobwebCollision()`. The Wiki `Flying?oldid=2732007` reference matches the fetch log's record of an opened older cached revision.

This does **not** accept whole-flight equivalence. The 1.9.4 movement branch additionally resets fall distance and clears flag 7 after delegated movement. Their consumers and later effects remain unresolved in this snapshot, as do later version intervals and the other limitations it lists. The source claims accepted here are only the three stated retained subpaths.

### Open trapdoor over ladder — ACCEPT

The 1.8.9 and 1.9.4 `LivingEntity.java` hashes and the cited ladder/trapdoor source hashes match their manifest rows. In 1.9.4, `isClimbing()` checks the block at the player's floored X/Z and bounding-box minimum Y; the new trapdoor case requires `OPEN`, a ladder directly below, and equal horizontal `FACING` values. The 1.8.9 code only recognizes ladder/vine at that cell. The ordinary `moveRelative()` climb consumer is reached through player travel and keeps the existing clamp/fall-distance/sneak behavior. Both releases register trapdoor and ladder blocks; their source properties support the stated state combination. The comparison is a reachable player-state predicate difference in `(1.8.9, 1.9.4]`, not a claim about the first snapshot or later continuity.

The snapshot correctly says the Ladder Wiki page could not be fetched and uses no Wiki wording as source evidence. Its exact release source comparison stands independently.

### Bed-walk lead — OUT OF SCOPE

This record makes only a boundary disposition: the cited first-introduction lead is dated to Java 1.8 snapshot 14w32c, before the 1.8.9 audit baseline. It explicitly makes no claim that bed collision or player stepping remained unchanged after 1.8.9 and states that no exact source pair was inspected for this disposition. The `Walking?oldid=2757268` URL/revision matches the fetch log, which records an older cached revision opened. Current direct re-open was inaccessible to this review tool. Therefore this is out of scope as a first-introduction change within the audited range; later continuity remains unverified. It is not a source-verified movement finding.

## Wiki retrieval log

The immutable log's oldids and outcomes align with the record links: Swimming `2726686`, Flying `2732007`, Glass Pane `2728132`, and Walking `2757268` are recorded as opened from older cached page content; the Jumping article is recorded as blocked while an official HistoryLine search result was available; Iron Bars and Ladder are recorded as robots.txt/error fetch failures. This re-review's direct attempts to open the oldid URLs returned “not accessible via this tool.” Those attempts do not rewrite the dated log and do not establish missing page content. The official HistoryLine search result independently exposed the jump-height entry; the Wiki remains secondary evidence throughout.

## Source artifact identities

All four ready records were re-read and their source-manifest and artifact-manifest file hashes recomputed. Each identifies the expected exact release and `ornithe-feather` namespace:

| Release | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Client JAR SHA-256 |
|---|---|---|---|
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` |
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` |

The cited source hashes were independently checked against their ready trees and corresponding source-manifest rows; the only mismatch is the sprint-swimming 1.12.2 local-client hash reported above. The revised Feather-derived JARs remain of **unproven** equivalence to unavailable original derived JARs wherever the earlier Feather snapshot caveat applies. No runtime validation was performed.

## Review cross-checks

- **Requirement and scope:** reviewed only the six frozen Wiki records and their fetch log; exact ready-source excerpts were checked only to verify these bounded records, and no source pair or Wiki lane is declared complete.
- **Correctness and edge cases:** the bounded source claims above are checked against exact method order, predicates, state, and reachable player consumers; sprint-swimming requires correction.
- **Missing dependencies:** creative-flight flag/fall-distance consumers, iron-bar connected states and neighbor predicates, snapshot cutovers, and bed post-baseline continuity remain open as stated.
- **Conventions and duplicates:** no implementation code or overlapping source-pair reports were reviewed; statuses preserve the records' separate bounded claims.
- **Permissions and visibility:** no user-facing or permission changes are part of this documentation-only review.
- **Security:** not applicable to these source-evidence snapshots.

## Closure

The Wiki lane and all involved source pairs remain **PARTIAL**. No implementation guidance or runtime conclusion is supplied. The sprint-swimming snapshot needs the hash correction and sprint-key path scope correction before acceptance; the other decisions apply only to their exact immutable records and stated limits.
