# Fluid-current tracker boundary and tag resource witness

## Verdict

The weak-current suppression first appears in the earliest inspected post-1.21 source, exact release `26.1`. It persists unchanged in `26.1.1` and `26.1.2`. Thus, for the inspected releases, the source boundary is `1.21.11` direct current accumulation → `26.1` tracker threshold. This does not establish behavior for uninspected intermediate or other release IDs.

Default fluid-tag source data includes both `minecraft:water` and `minecraft:flowing_water`. The `FlowingFluid.getFlow` source has reachable states with nonzero horizontal flow. This proves default generated tag membership and a source-level nonzero-current witness. It does **not** prove the client JAR's packaged `data/minecraft/tags/fluid/water.json` bytes: the exact pinned JARs are not present in the available artifact cache, and no matching JSON resource was published in the decompiled source trees.

## Exact source identities

All cited source files are under the canonical source publication `decompiled_minecraft/`.

| Release | Mapping | Source manifest SHA-256 | Artifact manifest SHA-256 | Client JAR SHA-256 pin |
|---|---|---|---|---|
| `1.21.11` | `mojmap` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| `26.1` | `unobfuscated` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` |
| `26.1.1` | `unobfuscated` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| `26.1.2` | `unobfuscated` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

Each release's `.ready.json` identifies its source/artifact manifest digests and client hash; its `.sources.sha256` contains the listed relevant source hashes. The `.ready.json` and `artifacts.sha256` files pin each release client SHA-256 listed in the table, and every `.ready.json` reports status `ready`. The `1.21.11` provenance JSON contains no input client SHA field; its recorded cache path is absent. The `26.1` and `26.1.1` provenance records include `originalClientJarSha256` values matching their ready-marker and artifact-manifest pins. The `26.1.2` provenance JSON names its exact source run but contains no input client SHA field, and its recorded cache path is absent. For `1.21.11` and `26.1.2`, client identity is therefore pinned by the ready marker and artifact manifest, not by provenance.

Relevant file hashes (SHA-256; case normalized):

- `1.21.11/mojmap/net/minecraft/world/entity/Entity.java` — `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`
- `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` — `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java` — `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/entity/player/Player.java` — `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/data/tags/FluidTagsProvider.java` — `3e3fab74c346256ba325b8953f4d6a6d8cf0670b182652c111053b51de8bec05`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/tags/FluidTags.java` — `5973c1d50c0ed73bae0149cfc7ad6a68dbdadb815f76ef024bca1a11fba2b653`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/level/material/FlowingFluid.java` — `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/level/material/FluidState.java` — `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/world/level/material/Fluids.java` — `73104fa3ffdef657d3b834b5f4d295c61ca5aed10772727389c5174ead0feccd`
- `26.1`, `26.1.1`, `26.1.2` `unobfuscated/net/minecraft/client/player/LocalPlayer.java` — `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`

The identical file hashes across the three 26.1 releases were checked against each release's separate source manifest, not inferred from one release's manifest alone.

## Behavioral boundary

At `1.21.11`, `Entity.updateFluidHeightAndDoFluidPushing(tag, scale)` sums each matching state's `getFlow`, attenuates by tracked fluid height under `0.4`, and increments a current count while `isPushedByFluid()` is true. Its application gate is `sum.length() > 0.0`. It averages by count for players, normalizes only for non-players, applies the requested scale, performs the minimum impulse adjustment, then adds that impulse to player delta movement. The ordinary water path calls it with `0.014`. `Player.isPushedByFluid()` is false only while the player ability is flying.

In `26.1`, `Entity` owns an `EntityFluidInteraction` initialized for water and lava. Its `updateFluidInteraction()` runs `fluidInteraction.update(this, !this.isPushedByFluid())`; the collector obtains each fluid state's `getFlow`, height-scales it below `0.4`, and accumulates it. `Tracker.applyCurrentTo` adds the additional gate `currentCount != 0 && accumulatedCurrent.lengthSqr() >= 1.0E-5F` before the player-average, `0.014` water scale, minimum impulse, and delta-movement addition. For a player, a nonzero accumulated vector below that squared threshold is therefore dropped where the old source's positive-length gate would have applied current. `26.1.1` and `26.1.2` have byte-identical `EntityFluidInteraction.java`, `Entity.java`, and `Player.java`, so the gate and call path persist unchanged in both patch releases.

`Entity.tick()` calls `baseTick()`, and `Entity.baseTick()` calls `updateFluidInteraction()`. The client `LocalPlayer` inherits the player fluid eligibility and entity update; there is no `LocalPlayer`-specific fluid-current override in the inspected source. This packet addresses only that direct fluid-current effect.

## Default water tag and current witness

In all three inspected 26.1 source releases, `FluidTagsProvider.addTags` declares `this.tag(FluidTags.WATER).add(Fluids.WATER, Fluids.FLOWING_WATER)`. `FluidTags.WATER` is created with default namespace and name `water`, and `Registries.FLUID` has registry path `fluid`; the data provider writes tags through `TagsProvider`'s JSON path provider. Thus the source defines the built-in resource as `data/minecraft/tags/fluid/water.json` and includes both source and flowing water.

`Fluids.FLOWING_WATER` is `new WaterFluid.Flowing()`. `FluidState.getFlow` delegates to its fluid type. `FlowingFluid.getFlow` sums horizontal direction steps multiplied by differences between its own height and neighboring same-fluid heights (or eligible lower neighboring fluids); a height difference produces nonzero components before final normalization. Its flowing states have positive own height (`amount / 9.0F`). This supplies a concrete source-level possibility for a nonzero flow contribution from a fluid explicitly in the default water tag. The proof is structural; no world was launched or runtime state simulated.

Resource/artifact limits: the release artifact manifests pin `26.1/client.jar` to `bc6194…d3840`, `26.1.1/client.jar` to `8c109b…fef3`, and `26.1.2/client.jar` to `b1b315…d0b0`. Searches of the checked canonical source trees and available `build` files found no `data/minecraft/tags/fluid/water.json` resource and no local client JAR at the artifact/provenance cache paths. The `26.1` and `26.1.1` provenance records name matching original client SHA values; the `26.1.2` ready/artifact records pin a client SHA, but its provenance JSON does not itself record that SHA. Consequently this packet accepts the tag's built-in generated membership from the exact-source provider, but leaves direct archive-member bytes and per-JAR tag membership unverified.

## Evidence boundaries

- No full pair coverage closure or independent audit is asserted here.
- No runtime witness, movement parity conclusion, implementation recommendation, or product association is asserted.
- Accepted base finding scope and its original evidence/audit remain separate; this note only closes the source release-cutoff question among inspected exact sources and records the precise resource limitation.
