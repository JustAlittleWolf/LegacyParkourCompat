# LocalPlayer candidate boundaries — 1.16.0 through 1.16.5 Mojmap

## Scope and inputs

Source-only boundary check for the three accepted candidates in `../local-player-tick-uncovered-members/report.md` (SHA-256 `a0ad62636b1995748e8633a926b54625cae0310531519472362dd8510c919db1`) and its renewed review `../local-player-tick-candidates-review/review-r2-addendum.md`. The assigned sources are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\mojmap`. No decompilation, product inspection, tests, build, or runtime was used.

## Release identity and source integrity

The root sidecars are present for 1.16, 1.16.1, 1.16.2, and 1.16.5. Each `mojmap.ready.json` identifies its exact `versionId`, `mojmap` mapping, and source count. Its recorded source-manifest, artifact-manifest, provenance, and diagnostics hashes match the corresponding root files. All three audited source-file hashes match their entries in `mojmap.sources.sha256` for each release. Sidecar SHA-256 values, ordered as `artifacts.sha256 / mojmap.provenance.json / mojmap.ready.json / mojmap.sources.sha256 / movement-diagnostics.txt`:

| Release | Ready ID / mapping / source count | Sidecar SHA-256 values |
|---|---|---|
| 1.16 | `1.16 / mojmap / 3572` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` / `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` / `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` / `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` / `950f97d4e7cc067f12f6504d95244c36df991c95aabba6c94b81cae605430e2f` |
| 1.16.1 | `1.16.1 / mojmap / 3572` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` / `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` / `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` / `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` / `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827` |
| 1.16.2 | `1.16.2 / mojmap / 3516` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` / `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` / `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` / `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` / `a14aaa94b80b2783eb8903f2eaff520c9f623067df4d376480ca87cd26768902` |
| 1.16.5 | `1.16.5 / mojmap / 3523` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` / `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` / `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` / `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` / `676ea98fca6e2b73e0db8214057397af8112b0b9d37b72ad7d9b1025bd77ec50` |

For 1.16.3 and 1.16.4, the five parent sidecars are not expected; each exact mapped source root carries its authoritative `mojmap/.decompile-provenance.json`. The embedded publication identities resolve to 1.16.3 and 1.16.4, with the same `lpc-decompile-v1` pipeline and Mojmap namespace. The provenance-file hashes are `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` (1.16.3) and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` (1.16.4). Their exact metadata, client and published mapping input hashes are:

- 1.16.3 metadata SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; client SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; mapping SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4 metadata SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`; client SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; mapping SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

For both 1.16.3 and 1.16.4, the current `LocalPlayer.java`, `LivingEntity.java`, and `Player.java` SHA-256 values each match the corresponding entry in that release's own embedded `sources` map, confirming the inspected class bodies against their own exact mapped publication. They are also byte-identical to their entries in the 1.16.2 and 1.16.5 root manifests. Thus all six release identities and the three operation-bearing source files are validated; no full-tree validation blocker remains for 1.16.3 or 1.16.4.

## Operation boundary results

1. **Shift input clears `sprintTriggerTime` in `LocalPlayer.aiStep`: first checked release 1.16; present through 1.16.5.** In 1.16 and 1.16.1, `LocalPlayer.java:688–690` executes `if (bl2) this.sprintTriggerTime = 0;`, after the item-use timer clear and before the automatic sprint gate. `bl2` is the tick's sampled shift input. In 1.16.2–1.16.5 the same code is at `:663–665`. The entire `LocalPlayer.java` hash changes between these source groups, but the inspected block is unchanged. Given the accepted 1.15.2 comparison in the input report (which lacks this shift-specific clear), the introduction is bounded to after 1.15.2 and no later than 1.16; no finer boundary is claimed.

2. **`LocalPlayer.aiStep` gates water descent on `isAffectedByFluids()`: first checked release 1.16; present through 1.16.5.** `LocalPlayer.java:758–759` in 1.16/1.16.1, and `:733–734` in 1.16.2–1.16.5, use `isInWater() && input.shiftKeyDown && isAffectedByFluids()` before calling `goDownInWater()`. This exact gate is already in 1.16.0. Its introduction is bounded to after 1.15.2 and no later than 1.16, with no finer cutover established.

3. **`LivingEntity.aiStep` gates jump selection on `isAffectedByFluids()`: first checked release 1.16; present through 1.16.5.** In 1.16/1.16.1, `LivingEntity.java:2425` begins `if (this.jumping && this.isAffectedByFluids())`; in 1.16.2–1.16.5 it is at `:2427`. `serverAiStep()` is invoked before that selection (`:2419` in 1.16/1.16.1; `:2421` in 1.16.2–1.16.5). The candidate report's LocalPlayer input-copy and inherited Player fluid-predicate dependencies remain applicable. This gate is already in 1.16.0; the introduction is bounded to after 1.15.2 and no later than 1.16, with no finer cutover established.

For all three operations, source files group by SHA-256: in 1.16 and 1.16.1, `LocalPlayer.java` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`, `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`, and `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`. In 1.16.2 through 1.16.5, they are respectively `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`, `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`, and `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.

## Conclusion

All three accepted operation forms are present in the earliest requested release, 1.16, and persist unchanged in each audited version through 1.16.5. This is a boundary of the named audit interval and the prior 1.15.2 endpoint, not proof of the precise 1.16 pre-release introduction point. The 1.16.3 and 1.16.4 operation-bearing files validate against each release's embedded mapped-source identity. This check does not establish pair freeze or runtime parity.
