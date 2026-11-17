# Product readiness: LocalPlayer suffocation escape witness

Disposition: **COVERED_NO_CODE** for the accepted exact 1.16.2 farmland witness. No pair-wide, .3–.5 alias, or runtime-parity claim is made.

## Accepted inputs

- Operation report `build/source-mid-patches-20261117/1.16.1--1.16.2/ordinary_player/local-player-suffocation-escape.md`, SHA-256 `868DF34DD179E59546793F38DCE241395C8B8B3A3CA676A51EB012C2E56866B5`; its independent ACCEPT is in `build/source-mid-patches-20261117/1.16.1--1.16.2/ordinary_player/audit/verdict.md`, SHA-256 `5203F7819178EB5EE399CD8DE31DFD5482B82B5657A4277923B9790BD3FED57E`.
- Accepted vanilla-state witness `build/source-mid-patches-20261117/1.16.1--1.16.2/suffocation_state_witness/suffocation-escape-witness.md`, SHA-256 `7DE358A9A00D94485900903310BED28B40B0EE3FACB08BBD47A0167AB436D09A`; witness audit `.../suffocation_state_witness/audit/verdict.md`, SHA-256 `91CE8B95ECC4E422BB37648EE89D75F2996F462CD3F4F5F70150E4880783F902`.

## Literal alias and provider resolution

`ParkourVersion.V1_16` is ordinal 19; `V1_16_2` is ordinal 20 and covers 1.16.2–1.16.5. For selected V1_16_2, the resolver does not select the older `SuffocationProbe @V1_16` (nor `@V1_15_2` or `@V1_8_9`) because each annotation is lower than the selected ordinal. `PushAwayProbeRangeHook` providers are only `@V1_12` and `@V1_13_1`; the only `PushAwayVelocityHook` provider is `@V1_13_1`. None is eligible at ordinal 20. `LocalPlayerMixin` wrappers preserve the native call when their lookup is empty, so the accepted 1.16.2 witness runs through the current native suffocation path.

This matters because `SuffocationProbe @V1_16` deliberately turns farmland, soul sand and dirt path into non-suffocating states to preserve the 1.16.1 response; it must not be inferred to govern V1_16_2.

## Bounded native correspondence

In current 26.2, `LocalPlayer.moveTowardsClosestSpace` retains the accepted 1.16.2 order (west, east, north, south), strict closest-edge comparison, and ±0.1 horizontal velocity write. `suffocatesAt` constructs the same near-full x/z prism over the entity AABB's minY/maxY, deflated by `1.0E-7`, and calls the level's suffocating-block collision query. Current `Blocks.FARMLAND` still sets `isSuffocating(Blocks::always)`. The current query iterates suffocating blocks and returns on an intersecting collision shape. These operations retain the accepted witness: for the command-positioned player overlapping farmland at y=4.8, the four probes resolve to a final +0.1 X velocity write, matching the accepted 1.16.2 result. This also preserves native behavior for all other inputs.

Exact current source identity: ready marker `decompiled_minecraft/26.2/unobfuscated.ready.json` SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; source manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`. Relevant 26.2 files: `LocalPlayer.java` `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`; `Blocks.java` `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435`; `CollisionGetter.java` `8E7D1A54D27E0F187667439539C59B8892F966F1B9C8107EB162C6D43F830F6C`; `BlockCollisions.java` `EB8A8F6F07B1D6384F17987818F056D41ADFD6B2AD120D26774BFA550F4FC51E`.

## Limits

The correspondence is for the accepted 1.16.2 source witness. The V1_16_2 alias also names 1.16.3–1.16.5; this packet does not independently establish persistence across those patch releases. It does not establish self-propelled entry into farmland, general suffocation parity, map frequency, trajectory, or runtime behavior. No code change is indicated for the bounded witness.
