# Eye-fluid surface inclusion

Source-only comparison: 1.21.11 (Mojmap) to 26.1 (published unobfuscated names), with persistence checked in 26.1.2 and 26.2. This finding is the independent eye-fluid boundary review for the permitted source assignment.

## Exact source evidence

1.21.11, Mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `tick` calls `updateFluidOnEyes` at lines 502–506; the sampler and strict surface gate are lines 1524–1536; `isEyeInFluid` reads the resulting tag set at lines 1600–1602; `getEyeY` is lines 3691–3693. `EntityType.java` lines 1191–1201 gives the default player size and eye height (`0.6F`, `1.8F`, `1.62F`). `FlowingFluid.java` lines 438–445 returns `getAmount() / 9.0F` for a non-contiguous fluid surface.

26.1, unobfuscated: `26.1/unobfuscated/net/minecraft/world/entity/Entity.java`, `tick` records the previous water-eye state then calls `updateFluidInteraction` at lines 521–525; its delegation is lines 1558–1561, its eye-query delegation is lines 1651–1653, `getEyeY` is lines 3720–3722, and the default interaction box is deflated by `0.001` at lines 4015–4024. `EntityFluidInteraction.java` resets trackers and scans the interaction box at lines 32–60; the eye coordinate check is inclusive at lines 67–70; the query returns the tracker flag at lines 148–151. Its `hasFluidAndLoaded` gate requires all horizontally covered chunks to be `FULL` and records whether any covered section has fluid at lines 91–119. `Entity.java` line 249 initializes trackers for `FluidTags.WATER` and `FluidTags.LAVA`; `EntityFluidInteraction.java` lines 121–129 matches a fluid to a tracked tag. `EntityType.java` lines 1189–1199 gives the same default player dimensions and eye height. `FlowingFluid.java` lines 470–477 retains the same float-height expression.

## Difference and movement meaning

The old sampler selects only `BlockPos.containing(x, eyeY, z)` and adds the sampled fluid tags only when `blockY + getHeight(...) > eyeY`. The 26.1 sampler visits fluid blocks overlapping the deflated player box and marks the tracked fluid when the player’s center block X/Z match and `eyeY >= fluidBottom && eyeY <= fluidTop`. At an exactly representable fluid-surface equality, the old result is false and the new result is true. Queries made after the update therefore report the eye inside that fluid in 26.1; consumers such as ordinary player swimming and water-eye decisions can take their submerged branch at the boundary.

Concrete default-player witness, with no vehicle or special dimensions: center `(x,z)=(0.5,0.5)`, standing eye height `1.62F`, feet Y `0.1577777862548828`, and a vanilla flowing-water state with amount 7 at block `(0,1,0)` and air above. `getOwnHeight` computes the Java float `7.0F / 9.0F = 0.7777777910232544F`; the source top is the double sum `1.7777777910232544`. Adding the default player eye-height float to the stated feet Y produces that same double exactly. The old containing block is `(0,1,0)` and its strict `>` test rejects equality. In the new scan, the eye is within the interaction box (player top is feet Y plus `1.8F`, before the `0.001` deflation), the water cell is among the iterated cells, and the inclusive `<= fluidTop` test accepts it. For the ordinary loaded-world precondition, the player’s covered and one-block-horizontal-neighbor chunks are `FULL`, and the covered section containing this water reports fluid. The state uses vanilla water and a default player; it does not depend on a modern-only block, entity, or interaction.

## Source identity and integrity

The read-only cache is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Provenance manifests identify exact IDs and namespaces: `1.21.11/mojmap.provenance.json` (`versionId=1.21.11`, namespace `mojmap`, created 2026-10-07T15:19:15.6614734Z; SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`) and `26.1/unobfuscated.provenance.json` (`versionId=26.1`, namespace `unobfuscated`, created 2026-10-09T19:03:31.077Z; SHA-256 `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b`). Both trees contain ready markers for the exact named releases.

SHA-256 of cited source files:

- 1.21.11 `Entity.java`: `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`
- 1.21.11 `EntityType.java`: `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e`
- 1.21.11 `FlowingFluid.java`: `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a`
- 26.1 `Entity.java`: `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- 26.1 `EntityFluidInteraction.java`: `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`
- 26.1 `EntityType.java`: `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229`
- 26.1 `FlowingFluid.java`: `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28`

The cited 26.1 `Entity.java`, `EntityFluidInteraction.java`, `EntityType.java`, and `FlowingFluid.java` hashes are identical in 26.1.2; the eye sampler remains at the cited lines. In 26.2, `EntityFluidInteraction.java` and `FlowingFluid.java` remain hash-identical and the inclusive eye check remains at lines 67–70; `Entity.java` and `EntityType.java` differ, while their tick delegation and default player eye height still provide the same behavior. The first changed stable release is established here only as 26.1 relative to 1.21.11. The exact intervening patch boundary is unresolved because 26.1.1 was outside the assigned inputs. Source identity, comparison, ordinary-player reachability, coordinate precision, bounding-box inclusion, fluid-state source and the loaded-section/chunk gate are closed for this witness. Runtime parity and the exact patch cutover remain open.
