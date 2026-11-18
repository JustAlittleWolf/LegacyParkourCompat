# Independent source audit: 1.10.2 → 1.11 glide/climb alias gate

## Verdict

**Accepted for the bounded claim.** The frozen input report is `c-glide-climb-1102-111-alias-gate/report.md`, SHA-256 `25e9bd11076455fa6e2047342b39cd31a7adf365281382bc4d2ba769ce44eed0` (recomputed from its bytes).

For an ordinary dry, locally controlled player with a usable chest Elytra and flag 7 already active, both `LivingEntity#moveRelative` bodies choose the fall-flying branch before their ordinary movement branch. `isClimbing()` is called only in the ordinary branch. It reads the block at floored player X/Z and shape-minY; ladder and vine return true in both versions. Therefore, at ladder/vine feet, the active glide branch bypasses ordinary climb horizontal clamps, descent cap, `fallDistance = 0.0F`, sneaking descent stop, and post-move horizontal-collision Y response. The glide pre-move threshold is exactly `velocityY > -0.5`, assigning `1.0F`; equality does not assign it. The velocity updates and `0.98F` Y multiplication precede the move in the same order and with the same expressions in both bodies.

The local request remains edge-triggered (`jumping && !priorJump`), airborne, descending, not already fall-flying and not ability-flying, then requires a usable chest Elytra. Both server handlers accept only airborne, descending, not already fall-flying, not in water, and usable chest Elytra; neither checks climbing. Only the failed outer packet predicate calls `clearFlying()`, whose two flag writes are `true` then `false`. `flyingTick()` runs before `moveRelative()` in both sources; it validates the flag against airborne/non-riding status and usable Elytra, and only writes the validated flag on the logical server. The report's local-versus-server authority boundary is supported.

A separate downstream difference is confirmed, outside the glide/climb alias claim: the 1.10.2 glide calls `Entity#move(dx,dy,dz)`; 1.11 calls `Entity#move(MoverType.SELF,dx,dy,dz)`. For the grounded sneaking player guard, 1.10.2 probes support at `-1.0`, while 1.11's SELF/PLAYER guard probes at `-stepHeight`; the corresponding constructors assign `0.6F` in both versions. The guard is inactive when `onGround` is false. This audit confirms the exact invocation and support-probe expressions only; it does not claim collision-geometry or resulting trajectory coverage.

## Exact source identity and byte checks

Read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Both ready markers identify exact releases `1.10.2` and `1.11`, mapping `ornithe-feather`; their `mappingJar` values identify Feather gen2 build.2 inputs. The provenance files identify namespace `ornithe-feather`. Ready marker SHA-256 / provenance SHA-256 / source-manifest SHA-256:

- `1.10.2/ornithe-feather`: `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` / `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- `1.11/ornithe-feather`: `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` / `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` / `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.

For each of the six referenced Java files per release, the actual file SHA-256 matched the corresponding line in that release's `ornithe-feather.sources.sha256`. Values below are actual bytes (and manifest matches):

| Source file | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6` | `45b27314866ba66e7f0208dd236ee7e6919aee84a32d22f8ef07db09433f3aeb` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `de500c8bf8e82a3f89d3a7f7208f3eed3d47f616344fb1e72aaacfd9e331a03f` | `f939a9c8b592feff053f2b0988c527c05bb8d8c8f328d882cc7468dc9a9583fa` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |

## Exact source references

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `LocalClientPlayerEntity.java`: 1.10.2 `:661-665,730-735`; 1.11 `:673-677,742-747` (prior jump capture and request gate).
- `ServerPlayNetworkHandler.java`: 1.10.2 `:845-857`; 1.11 `:835-847` (packet acceptance gates and flag action).
- `ServerPlayerEntity.java`: 1.10.2 `:1042-1049`; 1.11 `:1046-1053` (set/clear flag writes).
- `LivingEntity.java`: 1.10.2 `:928-946,1332-1423,1746-1771,2088-2089`; 1.11 `:978-996,1386-1477,1800-1825,2143-2144` (feet predicate, glide/ordinary branches, flyingTick and flag getter). `stepHeight` initialization is 1.10.2 `:165`, 1.11 `:172`.
- `Entity.java`: 1.10.2 `:446-485` (grounded-sneaking guard and fixed `-1.0` support probe); 1.11 `:455-510` (SELF/PLAYER guard and `-stepHeight` probe).

The report correctly limits itself to this route and does not claim full-pair coverage, an exact intermediate patch boundary, implementation, or runtime parity. The 1.11 provenance records `movementDiagnosticsReview: 0` and `sourcePairReview: not performed`; this bounded audit does not close those broader gates.
