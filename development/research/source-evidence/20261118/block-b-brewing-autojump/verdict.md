# Independent Brewing Stand AutoJump product review

**COMPLETED — ACCEPT** for the bounded V1_12 Brewing Stand admission in the combined product collector.

## Reviewed identities

| Object | Git SHA-1 | SHA-256 of object payload | SHA-256 with Git object header |
| --- | --- | --- | --- |
| Product commit | `f6c68a7145a0caeac53307cfec3996d21c9b27bb` | `2ee40d2b7662b6a012d2c95d832d974cffee08ab20958591ff2173548ec68186` | `3bc0d891362ff585831e1f524626e6c8ec20492f07156283c5308d86b90dc4a7` |
| Product root tree | `8d47ae54e05e3111298f2f0fb3b15a34b7f8f7e2` | `266dc6dc4e71386ea0fa0bd45cb4b6be3d715ad61d4d0192c9c77cfb9c2c9964` | `c758b4cc6c8d98402c1dc81eaddf5ceed672eb7578ae22aa9f4aa0e09e47eae3` |

The sole parent is `d6e179cb813fc6a61193979bbddee6ded9501488`. The original ready ref resolves to `3fe08ce9c3cbb43971bcc3429522d2bf7ff9cf7e`, tree `e196f54bf862c903a22a6cda4b60dab8c5aee69d`. A read-only Git alternate exposed both object stores and `merge-base --is-ancestor` returned 1 for the original ready commit against the reviewed commit. Published four-face border integration `51b54fb163eac19479580fd2e166f066377f8545` is an ancestor. The seven changed paths are the V1_12 AutoJump collector, the existing Brewing Stand evidence binding, four archived evidence receipts and their local Git attributes. Git's whitespace check passed with CRLF recognized as the existing Java file's line ending.

## Collector review

- The parent code for X/Z/Y scan bounds and visit order, corner and top-edge exclusion, epsilon-expanded admission envelope, loaded-chunk check, one-face border dispatch, floor and clearance checks, ordinary fence/slab provider checks, and block-before-entity fallback is byte-equivalent in the reviewed collector. The ordered component provider remains private to its existing Change and is resolved through the keyed `BlockCollisionShapeHook`. This avoids the isolated ready commit's direct exposure of the provider constant and preserves the combined border code.
- Only a single visited stand in the interior, with loaded air and dry bottom smooth-stone slabs ending at or below the query floor, can enter the stand branch. The added gates require a same-level logical-client local player, grounded standing dimensions, ordinary movement and fluid/effect state, finite player-sized probe within the old vertical range, and a dry stand. Existing slab support under both the player center and stand, clear stand headroom, and the nearby-entity exclusion still apply. An unsupported stand query uses the existing fallback. This includes full-block-floor and mixed-provider cases.
- The 26.2 `LocalPlayer.updateAutoJump` constructs the actual query from the post-move position and a horizontal endpoint raised by `getBbHeight()`, inflating X/Z by `getBbWidth()`. These are the dimensions and bounds tested by the new stand-only checks. The keyed provider supplies exact stem and plate AABBs in order, checked against the active player-context occupied union. The collector translates each component at the stand's scan position, filters by strict query intersection, and returns individual shapes for the vanilla first-intersection loop. Active block shape dispatch, border prepending, query-bounds dispatch, historical resolution, and native fallback all remain at their existing call sites.
- The V1_12 profile covers exact 1.12, 1.12.1 and 1.12.2. Logical-server and native execution are excluded by the existing runtime dispatcher. Earlier profiles still resolve the earlier collector and later profiles still resolve their own collector. The archived 1.11.2 source evidence creates no product expansion.

## Evidence review

Each Markdown link in the amended binding resolves in the reviewed tree and each declared raw receipt digest matches the referenced Git blob. The four newly archived payloads are byte-for-byte equal to the original ready commit's blobs. The step report is `89ffadc032d3bf41871d8dd8803cabc9280a29ddab56dbe21b3f5a06c862cf75`. Its unchanged audit receipt is `5be1000f8bb37e32db747d6a7905b307c2d4a08433678b8497f2f34be06d4fe5`. The binding corrects the extra `b` in the audit's in-text report digest.

The old `Entity.setPosition` uses a float half-width. Double literal `-0.3` leaves maxX slightly positive and defeats the exact incoming-side gate in `Box.intersectX`. At `X = -(double)(0.6F / 2.0F)`, maxX is exactly zero. From feet Y=0.5, the 0.6F step cannot clear the plate top at 1.125, so the tied retry restores the flat collision result. The fallback ray at Y=1.01 selects the stem at 1.875 first and the 1.375 rise exceeds the 1.2F limit. The newer union exposes the plate at that ray height and its 0.625 rise passes the timer interval. In the audited 1.11.2 and 1.12.2 full-floor move, the positive step route lands at 1.125 and the stem produces rise 0.75. The feet-Y=1 query-entry example gives 0.875. Those controls are separate.

The archived step report's 1.12.2 `LocalClientPlayerEntity.java` table cell carries a second transcription error, one missing `b` in the inline source digest. The canonical file and its pinned source manifest both give `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`, also recorded correctly in the existing 1.12 alias verdict. I verified the corrected source identity directly. This affects a transcription in the preserved historical receipt. The step report's raw artifact digest, source result and product verdict all stand.

## Build evidence and limits

The retained Gradle output in [build.log](build.log) has SHA-256 `0190f5e0b6662cb00d1b8ed463d71212b37e82ba1c07f4ebe5ff7b545d91c90c`. It records `BUILD SUCCESSFUL in 17s`, 19 executed tasks, client and server compilation, and zero Test task entries. The log names the reviewed worktree. The existing V1_12 collector class from that run has SHA-256 `b81cafbe2b418e6396b00ea9585cc98070bae75207859a441634814ebe09f099` and its bytecode contains the new stand guards. The checked-in init script configures all Gradle `Test` tasks with `enabled = false` through `gradle.beforeProject`. The build output alone does not record the command line or establish how each exclusion was supplied.

This accepts the scoped product reconciliation and confirms compilation evidence. Broader provider families, earlier-profile implementation, full-pair coverage and tick-level runtime parity remain open. The review ran static Git, source, hash, and bytecode checks. No test, rebuild, runtime launch, or repository/ref mutation occurred.
