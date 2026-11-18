# Independent audit of early input scaling revision 2

**Terminal: COMPLETED** (bounded source audit only). No product, wiki, Git, backlog, code, tests, runtime, generation, or fluid-state producer inspection was performed. Prior `verdict.md` remains unchanged.

## Immutable input

`early-input-scaling-source/comparison-r2.md` SHA-256: `213b14ff38e666aa6ba7f52fe94139b7e38f0fb4c0c5119f6a5fa1923a9bc7c0` (verified before writing this verdict).

## Individual claim verdicts

### Accepted: 1.14.2 sneak-pose selection

The 1.14.2 Yarn caller computes `bl4 = isInSneakingPose() || shouldLeaveSwimmingPose()` and passes it to `input.tick(bl4, isSpectator())` (`ClientPlayerEntity.java:617–618`). Its player override selects the sneak-pose disjunct with this exact gate (`ClientPlayerEntity.java:582–585`): `!abilities.flying && !isSwimming() && wouldPoseNotCollide(sneakPose)`, followed by `isHoldingSneakKey() || !wouldPoseNotCollide(standingPose)`; otherwise it returns false. `isHoldingSneakKey()` reads the input sneak state (`:577–579`). The source binds `field_18081` as sneaking through `Entity.isInSneakingPose()` (`Entity.java:1751–1753`); `PlayerEntity` maps `field_18076` to `STANDING_SIZE`. In 1.14.3 Yarn, the same enum order is named `SNEAKING` and `STANDING`.

The new `!isSwimming()` condition suppresses only the pose-derived disjunct while the swimming flag is true. `shouldLeaveSwimmingPose()` remains the other OR arm and is `isInSwimmingPose() && !isInsideWater()` in 1.14.2 (`Entity.java:1771–1777`). Therefore the 1.14.2 flag does not globally turn off slow input while swimming; the separate swimming-pose/water condition can still select it. The 1.14.1→1.14.2 source change and the corrected comparison’s distinction between these two disjuncts are accepted.

### Accepted as a source fact; open as a behavioral cutover: 1.14.4 water predicate

The 1.14.4 Mojmap caller keeps the two conditions as a separate OR: `isVisuallySneaking() || isVisuallyCrawling()` (`LocalPlayer.java:628–629`). `isVisuallyCrawling()` is `isVisuallySwimming() && !isInWater()`; `isVisuallySwimming()` tests `Pose.SWIMMING` (`Entity.java:1836–1842`). `isInWater()` returns `wasInWater` (`Entity.java:929–931`).

In 1.14.3 Yarn, the corresponding arm is `shouldLeaveSwimmingPose()`, whose body is `isInSwimmingPose() && !isInsideWater()` (`Entity.java:1811–1816`); `isInSwimmingPose()` tests the swimming pose, and `isInsideWater()` returns `insideWater` (`:905–907`). Thus the corrected comparison accurately identifies distinct direct water-state accessors and an independently gated swimming-pose arm. The exact source difference is accepted.

Whether `insideWater` and `wasInWater` are equivalent at this call site remains **open**. Their state producers are outside this assignment, so the source comparison cannot claim that the 1.14.3→1.14.4 change is merely a mapping rename or a behavioral change. The gate must not be closed based on names or helper structure alone.

### Accepted: nested source/toolchain provenance

The 1.14.2 Yarn tree’s nested `yarn/.decompile-provenance.json` identifies Yarn `1.14.2+build.7`, records the client and intermediary inputs, and records Java 25.0.3, Mapping-IO 0.9.1, Tiny Remapper 0.14.1, and Vineflower 1.12.0 with tool hashes. The 1.14.3 nested manifest identifies Yarn `1.14.3+build.13` and the same toolchain. The checked `.2` `ClientPlayerEntity.java`, `Entity.java`, `EntityPose.java`, and `KeyboardInput.java` hashes match the nested source-hash map; the checked `.3` `ClientPlayerEntity.java` and `Entity.java` hashes match their map.

The 1.14.4 Mojmap provenance identifies version `1.14.4`, namespace `mojmap`, and tool versions; the checked `KeyboardInput.java`, `LocalPlayer.java`, and `Entity.java` hashes match `mojmap.sources.sha256`. These local identities are sufficient for this bounded comparison. They do not establish movement parity.

## Hashes checked

| Source or provenance file | SHA-256 |
|---|---|
| `1.14.2/yarn/.decompile-provenance.json` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| `1.14.2/yarn/net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| `1.14.2/yarn/net/minecraft/entity/EntityPose.java` | `3b669e6d9f951310a1f92d84f419ef2dec1769aae9dbaedfc198176f38042217` |
| `1.14.2/yarn/net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `1.14.2/yarn/net/minecraft/client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| `1.14.3/yarn/.decompile-provenance.json` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |
| `1.14.3/yarn/net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| `1.14.3/yarn/net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `1.14.4/mojmap.provenance.json` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |
| `1.14.4/mojmap.sources.sha256` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| `1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` |
| `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| `1.14.4/mojmap/net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |

## Limits

This verdict covers only the corrected comparison’s input scaling, 1.14.2 sneak-pose selection, the 1.14.3/1.14.4 swimming-pose disjunct, and local source/provenance identity. The water-state producer equivalence remains open. No full-pair coverage, runtime parity, sprint-threshold analysis, or implementation conclusion is claimed.