# Independent bounded review: 1.8.5–1.8.9 ordinary movement group

## Verdict

**Accept the stated same-effective-input, same-received-vanilla-state ordinary movement conclusion as bounded source evidence.** Do not treat it as full-pair tick/dependency closure, whole-game equal-initial-world evolution, or runtime parity. The main closure report's resource uncertainty for 1.8.5–1.8.8 is superseded by the correction below. The 1.8.9 archive is unavailable, but this is an archive-equality limitation rather than an identified movement-semantic gate under the report's premises.

## Reviewed artifact identities

The requested immutable inputs matched their expected SHA-256 values:

- `closure.md`: `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257`
- `resource-registration-supplement.md`: `5533875a8ebe54a180d9b45d7b5cdfb11895c55e1f53e886c24decf628b96493`
- Follow-up `resource-correction.md`: `e1c11425af54a812ac2abdc426dae78f1e5ca6ae6af3ce8b15b2039de4215f1f`

I rehashed the exact mapped Java sources for each adjacent pair. Each endpoint has 1,612 Java paths, with no additions or removals: 21 changed paths for 1.8.5→1.8.6, 16 for 1.8.6→1.8.7, 28 for 1.8.7→1.8.8, and 26 for 1.8.8→1.8.9. These counts agree with the accepted sweep/bridge audit artifacts. Their per-path dispositions are consistent with the reports: the .5→.6 placement/NBT authorization, UI, and server/network/diagnostic changes (including the specific dispenser producer below); .6→.7 resource-pack lifecycle, sign serialization, server/UI/diagnostic changes; .7→.8 resource-pack/options, skull-owner parsing, rendering/Realms/server/storage changes; and .8→.9 Realms-option/UI, release metadata, rendering/audio, and role-preserving command/math renames. No changed path or audited hunk alters an ordinary movement root or collision provider. The .8→.9 bridge's `CrashReport.java` omission from its original prose list is correctly recorded in the group report as release-string-only.

## Independent resource correction check

The original supplement's assertion that the allowed cache contained no 1.8.5–1.8.8 client archives/version JSON is false for the canonical cache at `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/<version>/`. I verified the four client JAR SHA-256 values against their recorded provenance pins, and the four `version.json` SHA-256 values against their local pin records; all matched. The client JAR hashes are the values already bound by the mapped source provenance. No network access was used.

I compared every packaged `assets/` path and entry hash from those verified JARs. Each archive has 3,085 asset entries; the asset path sets match. There are no asset entry changes between 1.8.5→1.8.6 or 1.8.6→1.8.7. The only content change in 1.8.7→1.8.8 is `assets/minecraft/lang/en_US.lang`, adding the six resource-pack incompatibility warning/confirmation strings shown in the correction. No blockstate, model, texture, shader, font, sound, or other text entry changes in these four archives. The correction's aggregate inventory hashes and its archive totals are consistent with this direct comparison. Therefore the 1.8.5–1.8.8 resource-byte gate is closed for the inspected pinned archives; do not retain the earlier “archives absent” statement as a valid limitation.

## Why the unavailable 1.8.9 archive is not a movement-semantic blocker

The 1.8.9 source shows the consumer boundary. `ModelManager.reload` constructs `ModelBakery`, bakes models and rebuilds `BlockModelShaper`; `ModelBakery.loadModelDefinition` reads `blockstates/<id>.json` and registers render variants. `Minecraft` registers `ModelManager`, `BlockRenderDispatcher`, and `WorldRenderer` as resource-reload listeners. `BlockRenderDispatcher` selects and renders baked models; texture and atlas reloads likewise belong to renderer classes. Resource-pack code exposes named resource streams and metadata and has no code/script execution path.

Movement collision instead calls `Block.getCollisionShape(world,pos,state)` through `World` collision collection. Block-specific Java methods define the box and contact behavior: for example `LadderBlock.getCollisionShape` updates its shape from the vanilla block state's facing, while `LiquidBlock.getCollisionShape` returns `null` and its fluid behavior is implemented in Java. Player movement speed/effects are also Java-backed (`EntityAttributes.MOVEMENT_SPEED`, `StatusEffect` modifiers, and `LivingEntity`/`PlayerEntity` travel/jump paths). Resource JSON selects visual models for an already-existing `BlockState`; it does not register a block, change that state, define its collision box/contact predicate, or supply effect/attribute logic. Text, font, sound, shader, and image entries likewise feed client presentation/audio consumers. They can affect what a human sees or chooses, but that route changes the effective input and is outside the explicit equal-effective-input premise.

I therefore find no resource format in this 1.8.9 client whose packaged contents configure collision, climbability, fluid response, movement effects/attributes, or travel behavior. The absent 1.8.9 JAR leaves exact `.8→.9` packaged-resource path/content equality unverified, but it does not leave a demonstrated route for those assets to change movement response when effective input and received vanilla state are held equal. This conclusion is code-path bounded; it is not a claim that the absent archive was inspected or that whole-game traces with different user choices/world states are equal.

## Dispenser and `IBlockSource.getBlock()` trace

In 1.8.5, `Bootstrap.registerDispenseBehaviors` installs the command-block item handler. Its reachable caller is `DispenserBlock.tick`, which invokes `dispense` only inside `if (!world.isClient)`. The handler additionally guards the command-block state write, `BlockItem.setBlockNbt`, and `world.updateNeighbors` by `!world.isClient`; the item decrement is outside that inner guard but remains reachable only through the server-gated tick. The registered behavior can therefore produce a changed server block state and copied block-entity NBT, later observable by a client as a different received world state.

`IBlockSource.getBlock()` and `BlockSource.getBlock()` exist in 1.8.5. The sole `source.getBlock()` call in the Java tree is `Bootstrap`'s `world.updateNeighbors(source.getPos(), source.getBlock())` inside that command-block handler. Both the behavior and call disappear in 1.8.6, and the two accessor declarations/implementations disappear with the only caller. The 1.8.5 and 1.8.6 `DispenserBlock`, `CommandBlock`, and `Block` source files are byte-identical. Thus this is a real non-player world-state producer delta, not a client movement-calculation delta: if a server's behavior changes which block the client receives, later collision can differ because the state premise no longer holds. With equal received vanilla state, it cannot change the movement response.

## Exact remaining gates

- The local canonical cache has no 1.8.9 directory, `client.jar`, or `version.json`. Exact packaged-resource/whole-archive equality from 1.8.8→1.8.9 remains unverified. The 1.8.9 client input remains pinned by its source provenance, but the archive itself was not available for the resource comparison.
- The .5→.8 sweep is an adjacent Java hash-pruning pass with changed-hunk review, not a separate full tick-graph/provider retrace. The .8→.9 bridge and ordinary slice are bounded endpoint audits. Full movement dependency coverage across every pair and runtime parity remain unestablished.
- If scope expands from equal received state to equal initial worlds plus every later non-player/server world evolution, the removed command-block dispenser behavior remains an open reachability/state-divergence question.
- The .5–.8 release-parent provenance markers remain absent, though the embedded mapping-root provenance and source hashes were verified. This is a publication-marker limitation, not a mismatch in the examined sources or cached client hashes.

The prior supplement should be treated as corrected only for the resource-cache claim by `resource-correction.md`; its Java route and server-only producer analysis is corroborated. Keep the main conclusion at the bounded level stated in `closure.md`.
