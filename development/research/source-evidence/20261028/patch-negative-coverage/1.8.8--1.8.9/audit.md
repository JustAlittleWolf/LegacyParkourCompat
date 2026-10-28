# Independent blind source audit: Minecraft 1.8.8 → 1.8.9

## Verdict

No source difference affecting ordinary local-player movement was found in the checked pair. All paired source files that carry the normal client player tick, movement input, jump/travel, position and velocity writes, bounding-box movement/collision, and checked movement block responses have identical SHA-256 content. The 26 whole-tree source changes are version labels, UI/Realms additions, rendering/sound/serialization changes, or local-variable renames; the inspected `MathHelper` hunk only renames local variables in HSV-to-RGB conversion. No candidate movement finding is warranted from this comparison.

This is a bounded independent audit, **not a pair freeze**. Some terminal dependencies and alternate/reentrant movement paths remain untraced below.

## Source identities and integrity

Canonical read-only tree: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\`.

- **1.8.8:** exact client input hash `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8`; mapping artifact `feather-gen2-1.8.8+build.2-mergedv2.jar` (input hash `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f`). The tree has legacy provenance: `.decompile-provenance.json` is embedded under the source root and records the client/mapping inputs and SHA-256 for 1,612 sources, all of which matched the files checked. Its provenance file SHA-256 is `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`. It lacks the newer root-level ready marker and source-manifest sidecars; treat it as legacy provenance, not modern publication attestation.
- **1.8.9:** `ready.json` identifies version metadata `1.8.9`, exact client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`, and mapping artifacts `feather-gen2-1.8.9+build.2-mergedv2.jar` plus `feather-gen2-1.8.9+build.2.tiny`. The sidecar source manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, matching the ready marker. All 1,612 manifest entries matched the files checked.
- Both trees contain 1,612 Java sources. A path-by-path SHA-256 comparison found 26 changed files and no added/removed Java paths. Thus content identity is established within each tree's available provenance; the 1.8.8 provenance-format limitation remains explicit.

## Ordered coverage

### Examined

1. **Exact source identity and source integrity:** checked both mapping/version roots, input and mapping artifact identities, source counts, and every stored source hash against the files. Compared all Java paths by SHA-256.
2. **Client input and local tick:** `client/entity/living/player/Input.java`, `KeyboardInput.java`, `ClientPlayerEntity.java`, `LocalClientPlayerEntity.java` (`tick`, lines 105–116; `sendMovementToServer`, 117 onward), and `client/Minecraft.java` (`tick`, line 1303). Paired contents are identical. The only `Minecraft.java` diff is the display title and HTTP version header.
3. **Jump/travel and player state:** `entity/living/player/PlayerEntity.java` (`tick`, line 183; `jump`, 1268; `moveRelative`, 1279 onward) and `entity/living/LivingEntity.java` (`isClimbing`, 794; `jump`, 1096; `jumpInWater`/`jumpInLava`, 1111–1115; `moveRelative`, 1119; living `tick`, 1259). Paired contents are identical, so gates, operation order, numeric expressions and state writes in these files do not differ between endpoints.
4. **Position, velocity, box and collision:** `entity/Entity.java` (`tick`, 245; `move`, 371; `setPositionFromShape`, 638; liquid/collision checks, 648–840; `updateVelocity`, 844). Paired content is identical. This includes the examined direct player collision response and bounding-box movement implementation inherited from `Entity`.
5. **Movement block/fluid responses:** `block/Block.java`, `FlowingLiquidBlock.java`, `LadderBlock.java`, `SlimeBlock.java`, and `IceBlock.java` were paired and hash-identical. Whole-tree comparison showed no changed block source path. This rules out a changed source implementation among blocks for the endpoints but does not independently close every shape/registration/world dependency.
6. **Changed-source review for plausible movement dependencies:** `MathHelper.java` changes only local names in HSV-to-RGB; `GameOptions.java` adds a Realms-notification option; `EffectCommand.java` changes local names while retaining the same duration/amplifier computations and `StatusEffectInstance` arguments; `MinecraftServer.java`, `IntegratedServer.java`, and `RemoteHandshakeNetworkHandler.java` change version strings. `Screen`/title/options/Realms, renderer, sound, book text, command bookkeeping, crash/version and snooper edits were non-movement changes on the examined hunks. No changed movement hook, player response, effect application implementation, or fluid/block path was found among changed files.

Key identical source hashes (same value on both sides):

| Source | SHA-256 |
|---|---|
| `client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `client/entity/living/player/Input.java` | `aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74` |
| `client/entity/living/player/KeyboardInput.java` | `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` |
| `client/entity/living/player/ClientPlayerEntity.java` | `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b` |
| `entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `block/FlowingLiquidBlock.java` | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` |

### Partial

- Traced the ordinary client tick and core inherited movement path by comparing the relevant classes, but did not reconstruct every caller/callback edge from client input through all world/player update phases.
- Checked representative block, fluid, ladder, slime and ice sources and verified no changed block source anywhere in the source inventory. Did not individually inventory every collision-shape provider, block registration, neighboring-block query or world/chunk collision dependency.
- Checked shared effect/player movement files for identical hashes and inspected the effect command hunk. Did not fully traverse every status-effect, equipment/attribute, potion, enchantment, knockback, piston, packet correction, or externally triggered direct player response path.

### Unexamined

- Complete transitive dependency closure for every ordinary local-player tick branch, including all effect/equipment/attribute providers and all block shape/neighbor/world callbacks.
- Alternate player movement and direct-response branches outside the examined normal local-player path (including all packet-driven corrections and external impulses).
- Independent paired evidence for every terminal slice required by the discovery workflow; whole-tree hash comparison is supporting evidence, not a substitute for that closure.

## Pair status

The inspected movement source is identical across 1.8.8 and 1.8.9. Keep the pair open pending the listed dependency-closure work; this audit does not establish full-pair coverage or freeze.
