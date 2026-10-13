# F-S3-GLIDE-THROUGH-CLIMBABLE: Fall-flying Players no longer take the climbable fallback on tagged vines

- Older version A: 1.21.10
- Newer version B: 1.21.11
- Mechanic / coverage slice IDs: `S3-climb` (related branch body `S3-glide`)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior on vanilla blocks present in A
- First changed release: unknown within (1.21.10, 1.21.11]
- Runtime validation: not performed

## Paired evidence

The paired original-verified Mojmap publication identities are `PUB-1.21.10-MOJMAP-2026-10-07` and `PUB-1.21.11-MOJMAP-2026-10-07` in the run artifact manifest. The identities tie each exact source tree and its cited class hashes to the original client jar, Mojmap jar, source/artifact manifests, readiness marker and provenance record. No revised derived artifact is used.

- A evidence artifact record: `PUB-1.21.10-MOJMAP-2026-10-07` (original-verified). Source: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`; `LivingEntity.onClimbable()Z`, original lines 1662-1678; SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`. For a nonspectator, it checks `BlockTags.CLIMBABLE`, then usable open trapdoors. It has no `CAN_GLIDE_THROUGH` guard.
- B evidence artifact record: `PUB-1.21.11-MOJMAP-2026-10-07` (original-verified). Source: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`; `LivingEntity.onClimbable()Z`, original lines 1669-1687; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. Before the same climbable/trapdoor checks it returns false when `isFallFlying()` and the current block state is in `BlockTags.CAN_GLIDE_THROUGH`.
- Player override: A `Player.onClimbable()Z` lines 1955-1957 and B lines 2043-2045, file hashes `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` / `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; both return false for `abilities.flying`, otherwise delegate to `super.onClimbable()`.
- A/B vanilla `climbable.json` entry SHA-256 is `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`; both include ladder, vine, scaffolding, twisting/weeping vines and plants, and cave vines and plants. B's new `can_glide_through.json` entry SHA-256 is `e1b304a494b93be2a3280ea6cc7c73ebc0fd76e21654af801d5e4359e94232a8`; it contains vine, twisting vines/plants, weeping vines/plants, and `#cave_vines`, which expands to cave vines and cave vines plant (entry SHA-256 `b936bbb57d972003dda38f1d3584e3749915f26220843b941fc2571ef901c75a`). A jar has no `can_glide_through.json`. B `VanillaBlockTagsProvider.java` lines 320-324 SHA-256 `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474` and `BlockTags.java` line 125 SHA-256 `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbebc38e3` register the tag.
- Consumer path: both `Player.travel(Vec3)V` wrappers delegate to `LivingEntity.travel`; `LivingEntity.travel()` selects `travelFallFlying` while the shared fall-flying flag is set. The paired `travelFallFlying` bodies are A lines 2383-2397 and B lines 2441-2455. When `onClimbable()` is true, both bodies call `travelInAir` and `stopFallFlying`; otherwise they apply the same gliding formula. File hashes are the paired `LivingEntity.java` hashes above; Player hashes are recorded above.

## Source-level difference

For a Player with `abilities.flying == false`, `isSpectator() == false`, `isFallFlying() == true`, and a current block state at `blockPosition()` that is one of the vanilla-default tag intersection states (vine, twisting vine or plant, weeping vine or plant, cave vine or plant), outside the earlier fluid-travel branch, A reports the Player climbable and B reports it not climbable. The `Player.onClimbable()` wrapper delegates to `LivingEntity.onClimbable()` under this ability guard. A then calls `travelInAir()` and `stopFallFlying()`; B follows the fall-flying movement branch. This is a branch and state-write difference, not a measured trajectory.

## Reachability and limits

The intersection blocks are present in A's vanilla `climbable` tag and are existing blocks in the A endpoint artifact. This is a direct player movement-path difference; it does not depend on custom tags for the stated vanilla-default case. Server/resource-pack tag overrides are not assessed. No trajectory was measured and no damage, collision-shape, or block-state compatibility conclusion is made. The glide formula itself is otherwise unchanged for an equal branch decision. This finding is bounded to the direct `travelFallFlying()` consumer. `onClimbable()` has a separate inherited `isPushable()` consumer; its Player/entity-collision movement consequence remains open under `S4-move-core` and is not claimed by this snapshot.

## Handoff

Independent source delta: a fall-flying Player whose `getInBlockState()` is in the A/B vanilla climbable intersection with B `CAN_GLIDE_THROUGH` tag takes different travel branches in A and B under the stated spectator, flight-ability and fluid-branch guards. Finding-specific independent blind source review is pending. No accepted immutable snapshot or implementation handoff exists; implementation and runtime validation remain deferred.
