# Independent audit: 1.20 → 1.20.1 Mojmap source inventory

## Verdict

**No pair freeze.** The submitted Java path/hash comparison is reproducible, and the listed 11 Java changes are accurate. I found no Java source movement delta in the submitted movement subset. The inventory correctly leaves the pair open: archive-backed resources and two important dependency closures are not yet established, and some movement inputs/responses are named without their full tick or packet-to-state path.

Source identity is adequate for a bounded Java-source comparison, with a provenance caveat. The 1.20 tree has a legacy embedded provenance file and per-source hashes but no release-parent ready marker or separate manifests. The 1.20.1 release has ready/source/artifact/diagnostic sidecars whose stated hashes match; the source manifest verifies every source file. The missing staging/cache artifacts prevent rechecking the original client archive bytes and rerunning publication provenance. Do not describe the source publication as equally ready on both sides.

## Bound input and verification

Author report: `.native-source/20261028/frontier-c/1200-1201-full-inventory/report.md`, SHA-256 `c0aa5afe99b7b5ef37a8061132f734bda6f72816abfbfd287e3574c353ed5fcd` (12,157 bytes).

I read only that assigned inventory, its listed output tables, and the cited canonical 1.20/1.20.1 Mojmap sources and provenance paths. I did not inspect product code/history, wiki material, other reports, or run tests/builds/game/server tools.

Independent checks:

- Recomputed the SHA-256 of every author artifact. The report, Java diff, movement subset, non-Java metadata inventory, two empty issue files, and cited source sidecars match the supplied digests. The author evidence-digests file itself is a separate 2,049-byte artifact and is not self-hashed there.
- Both canonical Mojmap trees contain 4,786 Java files. Recomputed every source hash against the 1.20 embedded `sources` object and the 1.20.1 `mojmap.sources.sha256`: 4,786/4,786 match on each side; no missing or extra Java entries.
- Recomputed both sides for all 4,786 rows of `java-path-hash-diff.csv`: zero missing paths or hash mismatches; exactly 11 changed and 4,775 unchanged. The 11 paths are the ones in the author report. Recomputed the 1,019 submitted movement-scope entries: every listed path is unchanged and all 1,019 are also unchanged in the full table.
- The 1.20.1 ready marker’s source-manifest (`858b…8465`), artifact-manifest (`5f1f…7db2`), and diagnostics (`3eec…f4fd`) digests match the release-parent files. Its provenance names batch version ids and source/archive/mapping inputs, but names a staging tree and success log that are not present at those paths. The artifact manifest also points outside the available canonical source tree; client archive/resource bytes were not available for independent comparison.
- Both release parents were inspected with hidden entries shown. 1.20 contains only its `mojmap` directory; the old mapping tree has `.decompile-provenance.json`. 1.20.1 contains the mapping directory and the five declared sidecars. Neither mapping source tree contains resource/assets/data files.

## Changed Java paths and scope disposition

The full path/hash diff confirms these 11 changed files:

- `com/mojang/realmsclient/RealmsMainScreen.java`, `com/mojang/realmsclient/gui/screens/RealmsNotificationsScreen.java`, `com/mojang/realmsclient/util/RealmsPersistence.java`: Realms UI/persistence.
- `net/minecraft/DetectedVersion.java`, `net/minecraft/SharedConstants.java`, `net/minecraft/client/multiplayer/chat/report/ReportEnvironment.java`: version/report metadata.
- `net/minecraft/client/gui/components/AbstractButton.java`, `net/minecraft/client/gui/components/AbstractWidget.java`: UI render visibility.
- `net/minecraft/client/gui/screens/ConnectScreen.java`, `net/minecraft/network/Connection.java`: connection lifecycle.
- `net/minecraft/world/level/chunk/ChunkStatus.java`: chunk-generation completion callback; in-scope relevance is unclosed.

I agree the first ten do not directly change a movement operation, player movement writer, or movement packet payload/handler. Connection lifecycle may change when packets are observed, but its source diff is not itself a player movement procedure. The `ChunkStatus` diff is exactly the `thenApply` callback changing from unconditional `ProtoChunk` status promotion to `Either.ifLeft` promotion. The report properly does not claim it irrelevant: the caller/callee path and any resulting client chunk-availability effect still need closure. Its proximity to `hasChunkAt` alone is not evidence of a player movement delta.

## Movement graph audit and remaining dependencies

The submitted graph is a useful broad inventory, but it is not a complete independent closure of all terminals or dependency paths:

1. The tick outline reaches `LivingEntity.aiStep()` and travel, but the inherited pre-travel `LivingEntity.baseTick()` effect path is not traced. In the cited 1.20.1 source, `LivingEntity.baseTick()` calls `tickEffects()` (`LivingEntity.java:335-437`); `tickEffects()` can expire/update effects (`767-780`) through `onEffectUpdated()` (`1019+`). Movement-relevant effects and their attribute changes therefore need a paired trace into `Attributes.MOVEMENT_SPEED`, jump power and other consumed values. Merely listing effects among travel inputs does not close when those values change in the tick.
2. The inventory lists movement attributes/equipment and the packet listener’s direct responses, but omits the clientbound attribute-update route: `ClientPacketListener.handleUpdateAttributes()` (`2321+`) applies `ClientboundUpdateAttributesPacket` snapshots. Pair that handler with attribute instances/modifiers and the local player’s movement reads. Similarly list all accepted packet/state writers that can affect the local player before a tick, rather than only velocity, correction, explosion and outbound movement packets.
3. Resource/data closure is explicitly absent. Both compared source trees have no resource files; without archive-backed data, block/fluid/entity tags and any relevant data-driven registry definitions cannot be compared. Expand the requested set from the already noted fluid/block/entity tags to every tag/registry input reached by audited movement predicates (including equipment/effect/enchantment and collision/climb predicates where applicable). The inventory should record exact archive identity and per-side resource paths/hashes before claiming full pair coverage.
4. `ChunkStatus` needs a call-site trace of the modified completion stage through chunk generation/status storage, then through the actual client `ChunkSource`/`hasChunkAt`, `Level.getChunk` and the player’s unloaded-chunk travel/collision branches. Verify whether the changed generation pipeline is reachable on the logical client; if not, record that exact exclusion. The author explicitly leaves this open, so it blocks freeze.
5. The reported ordered tick graph names `Entity.tick/baseTick`, movement collision, callbacks, pose, and fluid scans, but its terminal inventory is summarized by directory groups rather than paired method-level entry points/callers for every state writer and consumer. Keep a bounded list of the exact methods reviewed for each terminal, with no-difference evidence from both releases. A broad unchanged Java hash does not establish that an unlisted caller or data dependency is irrelevant.

The required separate blind audit is also not complete: this report is an independent audit of the author’s evidence and graph, and it found the listed gaps. Do not freeze or accept a pair-wide “complete coverage” verdict from this input. No individual movement finding is submitted or accepted here.

## Suggested next bounded actions

- Restore or publish verified client artifacts/resources for both releases; verify exact archive and mapping identities and compare movement-relevant resource/tag/registry files.
- Trace the effect expiration and attribute-update paths and clientbound attribute packets into the tick’s movement reads.
- Close the `ChunkStatus` generation and availability path, distinguishing server-only work from logical-client behavior with exact call sites.
- Extend paired terminal coverage to external player state writers and record each checked method/dependency, then have a different blind reviewer audit that frozen inventory.
