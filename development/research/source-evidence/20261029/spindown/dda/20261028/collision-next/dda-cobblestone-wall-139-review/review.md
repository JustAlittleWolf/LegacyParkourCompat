# Independent review: DDA ID 139 cobblestone wall provider closure

## Verdict

**ACCEPT within the bounded metadata, key-alias, state and local collision-provider scope.** The report correctly stops general conversion at connected or otherwise unproven wall neighborhoods. It establishes neither full DDA visitation equivalence nor camera-path reachability or runtime parity.

## Evidence identity

The reviewed closure is `.native-source/20261028/collision-next/dda-cobblestone-wall-139-closure/report.md` (SHA-256 `3DC40166D0E3A017474FB5CDBD2A88BCE22675D89F67B1F64A4426E46939D459`); its `sources.sha256` manifest is SHA-256 `8EA8E51201B5F510C3B754369F645F6E9603DB3C0995ADE3EA06E2787263DEFA`. I recomputed all 21 manifest entries against canonical 1.8.9/26.2 files; all matched. The 15 Java files also matched the relevant canonical source-manifest entries. The ready markers identify 1.8.9/`ornithe-feather` and 26.2/`unobfuscated`, and each source manifest's actual SHA-256 matches its ready marker.

I also checked the current-key migration evidence that was not included in the closure's source manifest. 26.2 `BlockStateData.java` (SHA-256 `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4`) maps the normal wall state family to `minecraft:cobblestone_wall` and the old `variant=mossy_cobblestone` family to `minecraft:mossy_cobblestone_wall` (registrations 2224/2225). `ItemStackTheFlatteningFix.java` (SHA-256 `479371caace1c176eaad7de02f23258ad40be9b6c49a61bd6489a0da7a64bff5`) independently maps `cobblestone_wall.0` and `.1` to those distinct item keys. Both files' hashes match the 26.2 canonical source manifest.

## Claim checks

- 1.8.9 `Block.java` registers legacy ID 139 as one `cobblestone_wall` `WallBlock`. `WallBlock.Variant` has NORMAL ID 0 and MOSSY ID 1; `byId` changes every out-of-range value to 0. Thus metadata 1 selects mossy; 0 and 2–15 select normal. The variant is not consulted by `updateShape`, `getCollisionShape`, or `shouldConnectTo`.
- Old collision recomputes four neighbor gates, writes one mutable AABB and then sets `maxY=1.5`. With no horizontal connections, the bounds are X/Z `[0.25,0.75]` and Y `[0,1.5]`. With all four connections the old box expands to the full X/Z footprint. Its neighbor rule rejects barriers, accepts the same `WallBlock` instance and any fence gate, then admits solid-blocking cubes except pumpkin.
- 26.2 registers separate normal and mossy wall keys, each with `forceSolidOn`, and one WallBlock provider. The provider caches collision-shape functions for `makeShapes(24,24)`: an 8/16 central post plus 6/16 side arms selected from `UP` and side states. LOW/TALL sides have the same collision height under those constructor inputs; waterlogging is a shape-map key but does not change collision geometry. The collision provider returns the immutable state shape and does not mutate the old shared bounds.
- The local no-arm equality is valid when the current state has `UP=true` and all four sides `NONE`, e.g. four air neighbors: the new post and old single box both span X/Z `[0.25,0.75]`, Y `[0,1.5]`. This is only a local shape equality. A north connection already differs in occupied component volumes; all four connections make the old AABB full-footprint while the modern post-plus-arms union leaves corner volumes empty.
- The reported neighbor-rule distinction is real: modern `connectsTo` includes wall tags, iron bars, face sturdiness, and direction-compatible fence gates; legacy uses the old material/cube/pumpkin rule and any fence gate. Alias mapping cannot bypass that dependency.

## Boundary

The closure is suitable for the described bounded use: metadata-to-variant decoding, normal/mossy current keys, local shape/provider behavior and the no-connected-neighbor equality case. Preserve native behavior for connected walls unless the adapter separately proves the old connection rule and exact old AABB result for that neighborhood. The report does not imply that modern/current wall states are generally historical equivalents.
